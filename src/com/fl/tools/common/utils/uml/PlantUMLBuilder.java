package com.fl.tools.common.utils.uml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;

@Component
public class PlantUMLBuilder implements Builder<String, BusinessEntity> {

	@Autowired
	private BusinessEntityDefBuilder defBuilder;

	public String build(BusinessEntity be) {
		Assert.notNull(be);

		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		List<Def> defs = defBuilder.build(be);

		defs.forEach((e) -> {
			if (e instanceof ClassDef) {
				buffer.append(e.toPlantUMLText());
			} else {
				buffer2.append(e.toPlantUMLText());
			}
		});

		return buffer.append(buffer2).toString();
	}

}
