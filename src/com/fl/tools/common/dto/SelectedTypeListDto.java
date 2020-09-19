package com.fl.tools.common.dto;

import com.fl.tools.infr.domain.TypeList;

public class SelectedTypeListDto {
	private TypeList selectedTypeList;

	public SelectedTypeListDto(TypeList selectedTypeList) {
		super();
		this.selectedTypeList = selectedTypeList;
	}

	public TypeList getSelectedTypeList() {
		return selectedTypeList;
	}

	public void setSelectedTypeList(TypeList selectedTypeList) {
		this.selectedTypeList = selectedTypeList;
	}

}
