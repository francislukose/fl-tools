package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.dao.BusinessEntityDao;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.infr.domain.TypeList;
import com.fl.tools.infr.domain.v2.Annotation;
import com.fl.tools.infr.domain.v2.Attribute;
import com.fl.tools.infr.domain.v2.Modifier;
import com.fl.tools.infr.domain.v2.Steriotype;

@Component
public class BusinessEntityJsonDaoImpl implements BusinessEntityDao {
	private BusinessEntityJsonDataSource dataSource = new BusinessEntityJsonDataSource();
	private TypeListJsonDataSource typeListDataSource = new TypeListJsonDataSource();

	class TypeListJsonDataSource {
		private Map<String, TypeList> typeLists;
		private boolean initialized = false;

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					typeLists = objectMapper.readValue(BusinessEntityJsonDaoImpl.class.getClassLoader()
							.getResourceAsStream("config/bo-enums.json"), new TypeReference<Map<String, TypeList>>() {
							});
					initialized = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public Map<String, TypeList> getTypeLists() {
			if (!initialized) {
				init();
			}
			return Collections.unmodifiableMap(typeLists);
		}
	}

	class BusinessEntityJsonDataSource {
		private Map<String, BusinessEntity> businessEntities;
		private boolean initialized = false;

		public Set<com.fl.tools.infr.domain.v2.Component> buildV2Structure() {
			Set<com.fl.tools.infr.domain.v2.Component> components = new HashSet<>();
			Map<String, String> dbTypes = new HashMap<String, String>() {
				{
					put("String", "VARCHAR");
					put("int", "NUMBER");
					put("long", "NUMBER");
					put("boolean", "NUMBER");
					put("BigDecimal", "NUMBER");
					put("DateTime", "DATE");
				}
			};

			Map<String, String> colSize = new HashMap<String, String>() {
				{
					put("String", "250");
					put("int", "5");
					put("long", "10");
					put("boolean", "1");
					put("BigDecimal", "28, 6");
				}
			};

			businessEntities.forEach((k, v) -> {
				com.fl.tools.infr.domain.v2.Component c = new com.fl.tools.infr.domain.v2.Component();

				c.setName(v.getSimpleName());
				c.setPackageName(v.getFullName().replaceAll("." + v.getSimpleName(), ""));
				c.setParent(v.getHierarchy().getNextHierarchy().getPackageName() + ":"
						+ v.getHierarchy().getNextHierarchy().getSimpleName());
				c.getModifiers().add(Modifier.PUBLIC);
				c.getSteriotypes().add(Steriotype.CLASS);
				c.getSteriotypes().add(Steriotype.ENTITY);
				v.getChildren().forEach((ch) -> {
					BusinessEntity be = businessEntities.get(ch);
					String uuid = be.getFullName().replaceAll("." + be.getSimpleName(), "") + ":" + be.getSimpleName();
					c.getChildren().add(uuid);

				});

				if (v.isDeprecated()) {
					c.getSteriotypes().add(Steriotype.DEPRECATED);
				}
				c.getAnnotations().add(new Annotation(Annotation.ANN_DOCUMENTATION, v.getDescription()));
				c.getAnnotations().add(new Annotation(Annotation.ANN_DATABASE_TABLE, v.getTableName()));

				v.getAttributes().forEach((ka, av) -> {
					Attribute attr = new Attribute();
					attr.setName(av.getAttributeName());
					attr.setType(av.getTypeSimpleName());
					if (av.isBusinessObjectType()) {
						BusinessEntity be = businessEntities.get(av.getTypeSimpleName());
						String uuid = be.getFullName().replaceAll("." + be.getSimpleName(), "") + ":"
								+ be.getSimpleName();
						attr.setUUID(uuid);
					} else {
						if (av.getTypeFullName().indexOf('.') > 0) {
							String uuid = av.getTypeFullName().replaceAll("." + av.getTypeSimpleName(), "") + ":"
									+ av.getTypeSimpleName();
							attr.setUUID(uuid);
						} else {
							attr.setUUID(av.getTypeSimpleName());
						}
					}

					attr.getModifiers().add(Modifier.PRIVATE);
					if (av.isMany()) {
						attr.getSteriotypes().add(Steriotype.COLLECTION);
					}
					if (av.isDeprecated()) {
						attr.getSteriotypes().add(Steriotype.DEPRECATED);
					}
					if (av.isEnumType()) {
						attr.getSteriotypes().add(Steriotype.ENUM);
					}
					if (av.isBusinessObjectType()) {
						attr.getSteriotypes().add(Steriotype.ENTITY);
					}
					if (av.isManyToMany()) {
						attr.getSteriotypes().add(Steriotype.MANY_TO_MANY);
						attr.getSteriotypes().add(Steriotype.FOREIGN_KEY);
					}
					if (av.isManyToOne()) {
						attr.getSteriotypes().add(Steriotype.MANY_TO_ONE);
						attr.getSteriotypes().add(Steriotype.FOREIGN_KEY);
					}
					if (av.isOneToMany()) {
						attr.getSteriotypes().add(Steriotype.ONE_TO_MANY);
					}
					if (av.isOneToOne()) {
						attr.getSteriotypes().add(Steriotype.ONE_TO_ONE);
					}
					attr.getAnnotations().add(new Annotation(Annotation.ANN_DOCUMENTATION, av.getDescription()));
					Annotation dbColumn = new Annotation(Annotation.ANN_DATABASE_COL, attr.getName().toUpperCase());
					attr.getAnnotations().add(dbColumn);
					if (dbTypes.containsKey(av.getTypeSimpleName())) {
						dbColumn.addAssociatedAnnotation(
								new Annotation(Annotation.ANN_DATABASE_COL_TYPE, dbTypes.get(av.getTypeSimpleName())));
					}
					if (colSize.containsKey(av.getTypeSimpleName())) {
						dbColumn.addAssociatedAnnotation(
								new Annotation(Annotation.ANN_DATABASE_COL_SIZE, colSize.get(av.getTypeSimpleName())));
					}

					c.getAttributes().add(attr);
				});

				components.add(c);

			});

			File boFile = new File("./tmp-bo-v2.json");
			try (FileOutputStream fos = new FileOutputStream(boFile)) {
				ObjectMapper om = new ObjectMapper();
				String jsonText = om.writerWithDefaultPrettyPrinter().writeValueAsString(components);
				fos.write(jsonText.getBytes());

				System.out.println("FILE WROTE TO " + boFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return components;
		}

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					businessEntities = objectMapper
							.readValue(
									BusinessEntityJsonDaoImpl.class.getClassLoader()
											.getResourceAsStream("config/business-entities.json"),
									new TypeReference<Map<String, BusinessEntity>>() {
									});
					initialized = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public List<String> getDirectChildren(BusinessEntity be) {
			List<String> children = new ArrayList();

			if (be != null) {
				businessEntities.forEach((k, v) -> {
					if (v.getHierarchy().getNextHierarchy() != null && v.getHierarchy().getNextHierarchy()
							.getSimpleName().equalsIgnoreCase(be.getSimpleName())) {
						children.add(v.getSimpleName());
						be.getChildren().add(v.getSimpleName());
					}
				});
			}

			return children;
		}

		public Map<String, BusinessEntity> getBusinessEntities() {
			if (!initialized) {
				init();
			}
			return Collections.unmodifiableMap(businessEntities);
		}

		private void save() {
			File boFile = new File("./tmp-bo.json");
			try (FileOutputStream fos = new FileOutputStream(boFile)) {
				ObjectMapper om = new ObjectMapper();
				String jsonText = om.writerWithDefaultPrettyPrinter().writeValueAsString(businessEntities);
				fos.write(jsonText.getBytes());

				System.out.println("FILE WROTE TO " + boFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Map<String, BusinessEntity> getBusinessEntities() {
		return dataSource.getBusinessEntities();
	}

	@Override
	public Map<String, TypeList> getTypeLists() {
		return typeListDataSource.getTypeLists();
	}
}
