package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.config.InfrConfiguration;
import com.fl.tools.infr.dao.DomainObjectDao;
import com.fl.tools.infr.domain.DomainObject;
import com.fl.tools.infr.domain.DomainObjectsMapView;
import com.fl.tools.infr.domain.DomainValue;

@Component
public class DomainObjectsDaoImpl implements DomainObjectDao {
	@Autowired
	private InfrConfiguration configuration;
	private DomainObjectsJsonDataSource dataSource = new DomainObjectsJsonDataSource();

	class DomainObjectsJsonDataSource {
		private Map<String, Set<DomainObject>> domainObjectsCache = new HashMap<>();
		private boolean initialized = false;

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
			if (!domainObjectsCache.containsKey(location.versionUUID)) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					Set<DomainObject> domainObjects = objectMapper.readValue(
							new FileInputStream(location.location + "ftools-domain-objects.json"),
							new TypeReference<Set<DomainObject>>() {
							});
					domainObjectsCache.put(location.versionUUID, domainObjects);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public Set<DomainObject> getDomainObjects(String profileVersionUUID) {
			if (domainObjectsCache.containsKey(profileVersionUUID)) {
				return domainObjectsCache.get(profileVersionUUID);
			}
			init(new ProfileLocation(profileVersionUUID));

			return domainObjectsCache.get(profileVersionUUID);
		}
	}

	@Override
	public DomainObjectsMapView getDomainObjects(String profileVersionUUID) {
		return new DomainObjectsMapView(dataSource.getDomainObjects(profileVersionUUID));
	}
}
