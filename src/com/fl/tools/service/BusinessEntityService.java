package com.fl.tools.service;

import java.util.Map;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.TypeList;

public interface BusinessEntityService {
	public Map<String, BusinessEntity> getBusinessEntitiesMapView();
	public Map<String, TypeList> getTypeListMapView();
}
