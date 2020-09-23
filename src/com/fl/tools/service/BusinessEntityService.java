package com.fl.tools.service;

import java.util.Map;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.TypeList;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

public interface BusinessEntityService {
	public Map<String, BusinessEntity> getBusinessEntitiesMapView();

	public Map<String, TypeList> getTypeListMapView();
}
