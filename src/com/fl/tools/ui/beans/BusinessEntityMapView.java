package com.fl.tools.ui.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.infr.domain.TypeList;

@Component
@ManagedBean
@SessionScoped
public class BusinessEntityMapView {
	private Map<String, BusinessEntity> businessEntities;
	private Map<String, TypeList> typeLists;

	public List<String> getSiblingsOf(BusinessEntity be) {
		List<String> siblings = new ArrayList();

		BusinessEntity parent = businessEntities.get(be.getDirectParent());
		if (parent != null) {
			parent.getChildren().forEach((e) -> {
				if (!be.getSimpleName().equalsIgnoreCase(e)) {
					siblings.add(e);
				}
			});
		}

		return siblings;
	}

	public Map<String, TypeList> getTypeLists() {
		return typeLists;
	}

	public void setTypeLists(Map<String, TypeList> typeLists) {
		this.typeLists = typeLists;
	}

	public Map<String, BusinessEntity> getBusinessEntities() {
		return businessEntities;
	}

	public void setBusinessEntities(Map<String, BusinessEntity> businessEntities) {
		this.businessEntities = businessEntities;
	}

	public BusinessEntity getBusinessEntity(String entityName) {
		return businessEntities.get(entityName);
	}

	public TypeList getTypeList(String name) {
		return typeLists.get(name);
	}

	public int getTotalEntityCount() {
		return businessEntities.size();
	}

	public int getTotalTypeListCount() {
		return typeLists.size();
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

	public List<TypeList> getTypeListsAsList() {
		List<TypeList> boList = new ArrayList<TypeList>();
		if (typeLists != null) {
			typeLists.values().forEach((el) -> {
				boList.add(el);
			});
		}

		boList.sort((o1, o2) -> {
			return o1.getSimpleName().compareTo(o2.getSimpleName());
		});

		return boList;
	}

}
