package com.fl.tools.common.utils.uml.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicPlantUMLBuilder extends AbstractPlantUMLBuilder {
	@Autowired
	private ComponentBasicDefBuilder defBuilder;

	@Override
	public AbstractComponentDefBuilder getDefBuilder() {
		return defBuilder;
	}

}
