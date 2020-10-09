package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.domain.ComponentsMapView;

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

					Set<com.fl.tools.infr.domain.Component> componentsAsList = objectMapper.readValue(
							DomainObjectsJsonDaoImpl.class.getClassLoader()
									.getResourceAsStream("config/components.json"),
							new TypeReference<Set<com.fl.tools.infr.domain.Component>>() {
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
	public ComponentsMapView getComponents(String profileVersionUUID) {
		return dataSource.getComponentsMapView();
	}

}
