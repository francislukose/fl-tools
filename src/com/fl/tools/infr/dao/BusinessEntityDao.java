package com.fl.tools.infr.dao;

import java.util.Map;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.TypeList;

public interface BusinessEntityDao {
	public Map<String, BusinessEntity> getBusinessEntities();
	public Map<String, TypeList> getTypeLists();
}
