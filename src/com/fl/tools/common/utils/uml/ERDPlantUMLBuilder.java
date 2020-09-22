package com.fl.tools.common.utils.uml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ERDPlantUMLBuilder extends AbstractPlantUMLBuilder {
	@Autowired
	private BusinessEntityERDDefBuilder defBuilder;

	@Override
	public AbstractBusinessEntityDefBuilder getDefBuilder() {
		return defBuilder;
	}

}
