package com.fl.tools.common.utils.uml;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.BusinessEntity;

@Component
public class BusinessEntityERDDefBuilder extends AbstractBusinessEntityDefBuilder {

	@Override
	public Collection<Def> build(BusinessEntity be) {
		Collection<Def> theDefs = new HashSet<>();

		return theDefs;
	}

}
