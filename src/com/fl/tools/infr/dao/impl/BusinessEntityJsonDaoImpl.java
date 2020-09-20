package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.dao.BusinessEntityDao;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.infr.domain.TypeList;

@Component
public class BusinessEntityJsonDaoImpl implements BusinessEntityDao {
	private BusinessEntityJsonDataSource dataSource = new BusinessEntityJsonDataSource();
	private TypeListJsonDataSource typeListDataSource = new TypeListJsonDataSource();
	private boolean duplicateAttributeCheck = false;
	private boolean populateDirectParentFromHierarchy = false;
	private boolean populateChildrenFromHierarchy = false;
	private boolean saveTempBusinessEntityFileAfterLoad = false;

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
					if (duplicateAttributeCheck) {
						businessEntities.forEach((k, v) -> {
							BusinessEntityHierarchy current = v.getHierarchy().getNextHierarchy();
							while (current != null) {
								BusinessEntity be = businessEntities.get(current.getSimpleName());
								if (be != null) {
									be.getAttributes().forEach((ak, av) -> {
										if (v.getAttributes().containsKey(ak)) {
											v.getAttributes().remove(ak);
											System.out.println("[DUPLICATE ATTR] " + v.getSimpleName() + "::" + ak);
										}
									});
								}
								current = current.getNextHierarchy();
							}
						});
					}
					if (populateDirectParentFromHierarchy) {
						businessEntities.forEach((k, v) -> {
							v.setDirectParent(v.getHierarchy().getNextHierarchy().getSimpleName());
						});
					}
					if (populateChildrenFromHierarchy) {
						businessEntities.forEach((k, v) -> {
							getDirectChildren(v);
						});
					}
					initialized = true;
					if (saveTempBusinessEntityFileAfterLoad) {
						save();
					}
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
