package com.fl.tools.common.utils.uml.builder;

import java.util.Collection;

import org.springframework.util.Assert;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.infr.domain.ComponentProxy;

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
