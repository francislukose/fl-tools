package com.fl.tools.ui.handler;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.service.BusinessEntityService;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
@ManagedBean
public class IndexPageHandler {
	@Autowired
	private BusinessEntityService businessEntityService;
	@Autowired
	private BusinessEntityMapView businessEntityMapView;

	public String handleHomePageRequest() {
		return "/index";
	}
	public String handleBusinessEntityMapViewRequest() {
		businessEntityMapView.setBusinessEntities(businessEntityService.getBusinessEntitiesMapView());

		return "pages/bo/entities";
	}
}
