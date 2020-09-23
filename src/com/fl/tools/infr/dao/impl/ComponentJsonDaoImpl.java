package com.fl.tools.infr.dao.impl;

import java.util.Collections;
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
import com.fl.tools.infr.domain.v2.ComponentsMapView;

@Component
public class ComponentJsonDaoImpl implements ComponentDao {
	private ComponentsJsonDataSource dataSource = new ComponentsJsonDataSource();

	class ComponentsJsonDataSource {
		private ComponentsMapView components;
		private boolean initialized = false;

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

					Set<com.fl.tools.infr.domain.v2.Component> componentsAsList = objectMapper.readValue(
							BusinessEntityJsonDaoImpl.class.getClassLoader()
									.getResourceAsStream("config/components.json"),
							new TypeReference<Set<com.fl.tools.infr.domain.v2.Component>>() {
							});
					this.components = new ComponentsMapView(componentsAsList);
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

}
