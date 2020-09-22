package com.fl.tools.common.utils.uml;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;

abstract public class AbstractPlantUMLBuilder implements Builder<String, BusinessEntity> {

	public abstract AbstractBusinessEntityDefBuilder getDefBuilder();

	public String build(BusinessEntity be) {
		Assert.notNull(be);

		StringBuffer buffer = new StringBuffer();
		Collection<Def> defs = getDefBuilder().build(be);

		defs.forEach((e) -> {
			buffer.append(e.toPlantUMLText());
		});

		return buffer.toString();
	}

}
