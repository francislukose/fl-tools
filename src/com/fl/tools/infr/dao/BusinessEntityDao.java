package com.fl.tools.infr.dao;

import java.util.Map;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.TypeList;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

public interface BusinessEntityDao {
	public Map<String, BusinessEntity> getBusinessEntities();

	public Map<String, TypeList> getTypeLists();
}
