package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

public abstract class AbstractBusinessEntityDefBuilder implements Builder<Collection<Def>, BusinessEntity> {
	@Autowired(required = true)
	protected BusinessEntityMapView entityMapView;

}
