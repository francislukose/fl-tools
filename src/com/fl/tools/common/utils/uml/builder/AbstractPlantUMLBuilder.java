package com.fl.tools.common.utils.uml.builder;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.v2.ComponentProxy;

abstract public class AbstractPlantUMLBuilder implements Builder<String, ComponentProxy> {

	public abstract AbstractComponentDefBuilder getDefBuilder();

	public String build(ComponentProxy be) {
		Assert.notNull(be);

		StringBuffer buffer = new StringBuffer();
		Collection<Def> defs = getDefBuilder().build(be);

		defs.forEach((e) -> {
			buffer.append(e.toPlantUMLText());
		});

		return buffer.toString();
	}

}
