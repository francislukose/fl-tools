package com.fl.tools.ui.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.BusinessEntity;

@Component
@ManagedBean
@SessionScoped
public class BusinessEntityMapView {
	private Map<String, BusinessEntity> businessEntities;

	public Map<String, BusinessEntity> getBusinessEntities() {
		return businessEntities;
	}

	public void setBusinessEntities(Map<String, BusinessEntity> businessEntities) {
		this.businessEntities = businessEntities;
	}

	public BusinessEntity getBusinessEntity(String entityName) {
		return businessEntities.get(entityName);
	}

	public int getTotalEntityCount() {
		return businessEntities.size();
	}

	public List<BusinessEntity> getBusinessEntitiesAsList() {
		List<BusinessEntity> boList = new ArrayList<BusinessEntity>();
		if (businessEntities != null) {
			businessEntities.values().forEach((el) -> {
				boList.add(el);
			});
		}

		boList.sort((o1, o2) -> {
			return o1.getSimpleName().compareTo(o2.getSimpleName());
		});

		return boList;
	}

}
