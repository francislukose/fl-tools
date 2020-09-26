package com.fl.tools.infr.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.dao.DomainObjectDao;
import com.fl.tools.infr.domain.DomainObject;
import com.fl.tools.infr.domain.DomainObjectsMapView;
import com.fl.tools.infr.domain.DomainValue;

@Component
public class DomainObjectsJsonDaoImpl implements DomainObjectDao {
	private DomainObjectsJsonDataSource dataSource = new DomainObjectsJsonDataSource();

	class DomainObjectsJsonDataSource {
		private Set<DomainObject> domainObjects;
		private boolean initialized = false;

		private void save(Set<com.fl.tools.infr.domain.DomainObject> c) {
			File boFile = new File("./tmp-domain-objects.json");
			try (FileOutputStream fos = new FileOutputStream(boFile)) {
				ObjectMapper om = new ObjectMapper();
				String jsonText = om.writerWithDefaultPrettyPrinter().writeValueAsString(c);
				fos.write(jsonText.getBytes());

				System.out.println("FILE WROTE TO " + boFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		synchronized public void init() {
			if (!initialized) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					domainObjects = objectMapper.readValue(DomainObjectsJsonDaoImpl.class.getClassLoader()
							.getResourceAsStream("config/domain-objects.json"), new TypeReference<Set<DomainObject>>() {
							});

					initialized = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		public Set<DomainObject> getDomainObjects() {
			if (!initialized) {
				init();
			}
			return domainObjects;
		}
	}

	@Override
	public DomainObjectsMapView getDomainObjects() {
		return new DomainObjectsMapView(dataSource.getDomainObjects());
	}
}
