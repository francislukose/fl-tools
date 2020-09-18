package com.fl.tools.fineos.infr.dao.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.fineos.infr.dao.BusinessEntityDao;
import com.fl.tools.fineos.infr.domain.BusinessEntity;

@Component
public class BusinessEntityJsonDaoImpl implements BusinessEntityDao {
	private BusinessEntityJsonDataSource dataSource = new BusinessEntityJsonDataSource();

	static class BusinessEntityJsonDataSource {
		private Map<String, BusinessEntity> businessEntities;
		private boolean initialized = false;

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					businessEntities = objectMapper.readValue(
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

		public Map<String, BusinessEntity> getBusinessEntities() {
			if (!initialized) {
				init();
			}
			return Collections.unmodifiableMap(businessEntities);
		}
	}

	@Override
	public Map<String, BusinessEntity> getBusinessEntities() {
		return dataSource.getBusinessEntities();
	}
}
