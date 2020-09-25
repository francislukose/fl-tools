package com.fl.tools.common.utils.uml.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InheritancePlantUMLBuilder extends AbstractPlantUMLBuilder {
	@Autowired
	private ComponentInheritanceDefBuilder defBuilder;

	@Override
	public AbstractComponentDefBuilder getDefBuilder() {
		return defBuilder;
	}

}
