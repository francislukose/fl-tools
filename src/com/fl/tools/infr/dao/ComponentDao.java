package com.fl.tools.infr.dao;

import com.fl.tools.infr.domain.ComponentsMapView;

public interface ComponentDao {

	public ComponentsMapView getComponents(String profileVersionUUID);
}
