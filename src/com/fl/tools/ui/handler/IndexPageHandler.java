package com.fl.tools.ui.handler;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.csv.BusinessEntityCsvBuilder;
import com.fl.tools.service.BusinessEntityService;
import com.fl.tools.service.ComponentService;
import com.fl.tools.ui.beans.BusinessEntityMapView;
import com.fl.tools.ui.beans.ComponentUIView;

@Component
@ManagedBean
public class IndexPageHandler {
	@Autowired
	private BusinessEntityService businessEntityService;
	@Autowired
	private BusinessEntityMapView businessEntityMapView;
	@Autowired
	private BusinessEntityCsvBuilder csvBuilder;

	public String handleHomePageRequest() {
		return "/index";
	}

	public String handleBusinessEntityMapViewRequest() {
		if (businessEntityMapView == null || businessEntityMapView.getBusinessEntities() == null) {
			businessEntityMapView.setBusinessEntities(businessEntityService.getBusinessEntitiesMapView());
		}
		return "/pages/bo/entities";
	}

	public String handleTypeListMapViewRequest() {
		if (businessEntityMapView == null || businessEntityMapView.getTypeLists() == null) {
			businessEntityMapView.setTypeLists(businessEntityService.getTypeListMapView());
		}
		return "/pages/bo/typelists";
	}

	public void handleBusinessEntityDownloadRequest() {
		if (businessEntityMapView == null || businessEntityMapView.getBusinessEntities() == null) {
			businessEntityMapView.setBusinessEntities(businessEntityService.getBusinessEntitiesMapView());
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		response.reset();
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; filename=\"business-entities.csv\"");

		try (final BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
			output.write(("Business Entity Name,Table Name, Attribute Name, Attribute Type, Relationship" + "\n")
					.getBytes());
			businessEntityMapView.getBusinessEntities().forEach((k, e) -> {
				csvBuilder.build(e).forEach((v) -> {
					try {
						output.write((v + "\n").getBytes());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				});
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		facesContext.responseComplete();
	}
}
