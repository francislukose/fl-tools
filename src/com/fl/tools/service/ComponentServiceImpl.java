package com.fl.tools.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.tools.infr.dao.BusinessEntityDao;
import com.fl.tools.infr.dao.ComponentDao;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

@Service
public class ComponentServiceImpl implements ComponentService {
	@Autowired
	private ComponentDao componentDao;

	@Override
	public ComponentsMapView getComponents() {
		return componentDao.getComponents();
	}
}
