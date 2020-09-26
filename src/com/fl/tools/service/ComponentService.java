package com.fl.tools.service;

import com.fl.tools.infr.domain.ComponentsMapView;
import com.fl.tools.infr.domain.DomainObjectsMapView;

public interface ComponentService {
	public ComponentsMapView getComponents();

	public DomainObjectsMapView getDomainObjects();
}
