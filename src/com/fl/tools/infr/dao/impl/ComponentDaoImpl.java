package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.config.InfrConfiguration;
import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.domain.ComponentsMapView;

@Component
public class ComponentDaoImpl implements ComponentDao {
	@Autowired
	private InfrConfiguration configuration;
	private ComponentsJsonDataSource dataSource = new ComponentsJsonDataSource();

	class ComponentsJsonDataSource {
		private Map<String, ComponentsMapView> componentsCache = new HashMap<>();

		class ProfileLocation {
			String versionUUID;
			String location = configuration.getRootDir() + File.separator + "data" + File.separator;

			ProfileLocation(String versionUUID) {
				this.versionUUID = versionUUID;
				String[] locations = versionUUID.replaceAll("PID:", "").toLowerCase().split(":");
				for (String s : locations) {
					location += s + File.separator;
				}
			}
		}

		synchronized public void init(ProfileLocation location) {
			if (!componentsCache.containsKey(location.versionUUID)) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();

					Set<com.fl.tools.infr.domain.Component> componentsAsList = objectMapper.readValue(
							new FileInputStream(location.location + "ftools-components.json"),
							new TypeReference<Set<com.fl.tools.infr.domain.Component>>() {
							});

					ComponentsMapView components = new ComponentsMapView(componentsAsList);
					componentsCache.put(location.versionUUID, components);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public ComponentsMapView getComponentsMapView(String profileVersionUUID) {
			if (componentsCache.containsKey(profileVersionUUID)) {
				return componentsCache.get(profileVersionUUID);
			}
			init(new ProfileLocation(profileVersionUUID));

			return componentsCache.get(profileVersionUUID);
		}

	}

	@Override
	public ComponentsMapView getComponents(String profileVersionUUID) {
		return dataSource.getComponentsMapView(profileVersionUUID);
	}

}
