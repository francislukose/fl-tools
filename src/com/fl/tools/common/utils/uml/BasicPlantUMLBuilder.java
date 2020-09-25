package com.fl.tools.common.utils.uml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;

@Component("s_BasicPlantUMLBuilder")
public class BasicPlantUMLBuilder extends AbstractPlantUMLBuilder {
	@Autowired
	private BusinessEntityBasicDefBuilder defBuilder;

	@Override
	public AbstractBusinessEntityDefBuilder getDefBuilder() {
		return defBuilder;
	}

}
