package com.fl.tools.common.utils.uml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;

@Component("s_InheritancePlantUMLBuilder")
public class InheritancePlantUMLBuilder extends AbstractPlantUMLBuilder {
	@Autowired
	private BusinessEntityInheritanceDefBuilder defBuilder;

	@Override
	public AbstractBusinessEntityDefBuilder getDefBuilder() {
		return defBuilder;
	}

}
