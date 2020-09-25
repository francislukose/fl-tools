package com.fl.tools.common.utils.uml.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fl.tools.common.utils.Builder;
import com.fl.tools.common.utils.uml.common.Def;
import com.fl.tools.infr.domain.BusinessEntity;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.infr.domain.v2.ComponentProxy;
import com.fl.tools.ui.beans.BusinessEntityMapView;
import com.fl.tools.ui.beans.ComponentUIView;

public abstract class AbstractComponentDefBuilder implements Builder<Collection<Def>, ComponentProxy> {
	@Autowired(required = true)
	protected ComponentUIView componentUiView;

}
