package com.fl.tools.infr.dao;

import com.fl.tools.infr.domain.DomainObjectsMapView;

public interface DomainObjectDao {
	public DomainObjectsMapView getDomainObjects(String profileVersionUUID);
}
