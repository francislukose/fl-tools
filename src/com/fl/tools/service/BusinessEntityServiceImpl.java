package com.fl.tools.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.tools.infr.dao.BusinessEntityDao;
import com.fl.tools.infr.domain.BusinessEntity;

@Service
public class BusinessEntityServiceImpl implements BusinessEntityService {
	@Autowired
	private BusinessEntityDao businessEntityDao;

	@Override
	public Map<String, BusinessEntity> getBusinessEntitiesMapView() {
		return businessEntityDao.getBusinessEntities();
	}

}