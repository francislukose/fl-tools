package com.fl.tools.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.ui.beans.BusinessEntityMapView;

public class SelectedBusinessEntityDto {
	private BusinessEntity entity;
	private List<BusinessEntity> parents = new ArrayList<>();

	public SelectedBusinessEntityDto(BusinessEntity entity) {
		super();
		this.entity = entity;
	}

	public SelectedBusinessEntityDto addParent(BusinessEntity e) {
		if (e != null) {
			this.parents.add(e);
		}
		return this;
	}

	public List<BusinessEntity> getParents() {
		return parents;
	}

	public void setParents(List<BusinessEntity> parents) {
		this.parents = parents;
	}

	public BusinessEntity getEntity() {
		return entity;
	}

	public void setEntity(BusinessEntity entity) {
		this.entity = entity;
	}

	public List<BusinessEntityAttributeDto> getAllAttributes() {
		List<BusinessEntityAttributeDto> attributes = new ArrayList<>();

		parents.forEach((e) -> e.getAttributes()
				.forEach((k, v) -> attributes.add(new BusinessEntityAttributeDto(v, true, e.getSimpleName()))));
		entity.getAttributes().forEach((k, v) -> attributes.add(new BusinessEntityAttributeDto(v)));

		return attributes;
	}
}
