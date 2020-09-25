package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.dao.impl.BusinessEntityJsonDaoImpl.BusinessEntityJsonDataSource;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.TypeList;
import com.fl.tools.infr.domain.v2.Annotation;
import com.fl.tools.infr.domain.v2.Attribute;
import com.fl.tools.infr.domain.v2.AttributeProxy;
import com.fl.tools.infr.domain.v2.ComponentProxy;
import com.fl.tools.infr.domain.v2.ComponentsMapView;
import com.fl.tools.infr.domain.v2.Modifier;
import com.fl.tools.infr.domain.v2.Steriotype;

@Component
public class ComponentJsonDaoImpl implements ComponentDao {
	private ComponentsJsonDataSource dataSource = new ComponentsJsonDataSource();

	class ComponentsJsonDataSource {
		private ComponentsMapView components;
		private boolean initialized = false;

		private void save(Set<com.fl.tools.infr.domain.v2.Component> c) {
			File boFile = new File("./tmp-components-1.json");
			try (FileOutputStream fos = new FileOutputStream(boFile)) {
				ObjectMapper om = new ObjectMapper();
				String jsonText = om.writerWithDefaultPrettyPrinter().writeValueAsString(c);
				fos.write(jsonText.getBytes());

				System.out.println("FILE WROTE TO " + boFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void setColumns(Set<com.fl.tools.infr.domain.v2.Component> componentsAsList) {
			Map<String, String> colType = new HashMap<String, String>() {
				{
					put("String", "VARCHAR");
					put("boolean", "NUMBER");
					put("long", "NUMBER");
					put("int", "NUMBER");
					put("BigDecimal", "NUMBER");
					put("double", "NUMBER");
					put("DateTime", "DATE");
					put("enum", "NUMBER");
				}
			};

			Map<String, String> colSize = new HashMap<String, String>() {
				{
					put("enum", "10");
					put("String", "50");
					put("boolean", "1");
					put("long", "10");
					put("int", "5");
					put("BigDecimal", "10,6");
					put("double", "10,6");
				}
			};

			Map<String, Patch> p = Patch.patches();

			componentsAsList.forEach((c) -> {
				ComponentProxy cp = this.components.getComponent(c.getUUID());
				Patch theP = p.get(c.getName());
				c.getAttributes().forEach((a) -> {
					a.getAnnotations()
							.removeIf((filter) -> filter.getName().equalsIgnoreCase(Annotation.ANN_DATABASE_COL));

					AttributeProxy ap = cp.getAttributes().get(a.getUUID());
					if (!ap.isEntity()) {
						String colTypeName = ap.isEnumType() ? "enum" : ap.getType();

						Annotation ann = new Annotation(Annotation.ANN_DATABASE_COL, ColName.getColName(a.getName()))
								.addAssociatedAnnotation(
										new Annotation(Annotation.ANN_DATABASE_COL_TYPE, colType.get(colTypeName)));
						if (colTypeName.equalsIgnoreCase("enum")) {
							ann.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_STERIOTYPE, "Enum"));
						}
						if (!colTypeName.equalsIgnoreCase("DateTime")) {
							String size = colSize.get(colTypeName);
							if (theP != null) {
								for (Attr pattr : theP.getAttrs()) {
									if (pattr.getN().equalsIgnoreCase(a.getName())) {
										if (pattr.getSize() != null && pattr.getSize().length() > 0)
											size = pattr.getSize();
									}
								}
							}
							ann.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_SIZE, size));
						}

						System.out.println("ADDING COLUMN ==> " + a.getName() + "[" + ann.toString() + "]");
						a.getAnnotations().add(ann);
					}
				});
			});
		}

		public void populateBE(Set<com.fl.tools.infr.domain.v2.Component> componentsAsList) {
			componentsAsList.forEach((c) -> {
				if (c.getName().equalsIgnoreCase("BusinessEntity")) {
					c.getModifiers().add(Modifier.ABSTRACT);

					Attribute cAttr = new Attribute();
					cAttr.setName("C");
					cAttr.setType("int");
					cAttr.setTypeUUID("int");
					cAttr.setUUID("int:C");
					cAttr.getModifiers().add(Modifier.PRIVATE);
					cAttr.getAnnotations().add(new Annotation(Annotation.ANN_DATABASE_COL, "C")
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_TYPE, "NUMBER"))
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_SIZE, "5"))
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_STERIOTYPE, "PK")));
					cAttr.getSteriotypes().add(Steriotype.PRIMARY_KEY);
					c.getAttributes().add(cAttr);

					Attribute iAttr = new Attribute();
					iAttr.setName("I");
					iAttr.setType("long");
					iAttr.setTypeUUID("long");
					iAttr.setUUID("long:C");
					iAttr.getModifiers().add(Modifier.PRIVATE);
					iAttr.getAnnotations().add(new Annotation(Annotation.ANN_DATABASE_COL, "I")
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_TYPE, "NUMBER"))
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_SIZE, "10"))
							.addAssociatedAnnotation(new Annotation(Annotation.ANN_DATABASE_COL_STERIOTYPE, "PK")));
					cAttr.getSteriotypes().add(Steriotype.PRIMARY_KEY);
					c.getAttributes().add(iAttr);
				}
			});
		}

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					// objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

					Set<com.fl.tools.infr.domain.v2.Component> componentsAsList = objectMapper.readValue(
							BusinessEntityJsonDaoImpl.class.getClassLoader()
									.getResourceAsStream("config/components.json"),
							new TypeReference<Set<com.fl.tools.infr.domain.v2.Component>>() {
							});

					
					this.components = new ComponentsMapView(componentsAsList);

					// setColumns(componentsAsList);
					// populateBE(componentsAsList);

					// save(componentsAsList);

					initialized = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public ComponentsMapView getComponentsMapView() {
			if (!initialized) {
				init();
			}
			return this.components;
		}

	}

	@Override
	public ComponentsMapView getComponents() {
		return dataSource.getComponentsMapView();
	}

	static class ColName {
		static String getColName(String s) {
			String name = "";
			if (s.length() > 13) {
				name = s.substring(0, 13);
			} else {
				name = s;
			}
			return name.toUpperCase();
		}
	}

}
