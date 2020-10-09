package com.fl.tools.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.dao.DomainObjectDao;
import com.fl.tools.infr.domain.ComponentsMapView;
import com.fl.tools.infr.domain.DomainObjectsMapView;

@Service
public class ComponentServiceImpl implements ComponentService {
	@Autowired
	private ComponentDao componentDao;
	@Autowired
	private DomainObjectDao domainDao;

	@Override
	public ComponentsMapView getComponents(String profileVersionUUID) {
		return componentDao.getComponents(profileVersionUUID);
	}

	@Override
	public DomainObjectsMapView getDomainObjects(String profileVersionUUID) {
		return domainDao.getDomainObjects(profileVersionUUID);
	}

}
