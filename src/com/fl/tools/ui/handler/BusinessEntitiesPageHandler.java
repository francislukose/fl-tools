package com.fl.tools.ui.handler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.dto.SelectedBusinessEntityDto;
import com.fl.tools.common.dto.SelectedTypeListDto;
import com.fl.tools.common.utils.PlantUMLBuilder;
import com.fl.tools.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.ui.beans.BusinessEntityMapView;

@Component
@ManagedBean
public class BusinessEntitiesPageHandler {
	@Autowired
	private BusinessEntityMapView businessEntityMapView;
	@Autowired
	private PlantUMLBuilder umlBuilder;

	private SelectedBusinessEntityDto entitySelection;
	private SelectedTypeListDto typeListSelection;

	protected void populateParents() {
		BusinessEntityHierarchy current = entitySelection.getEntity().getHierarchy().getNextHierarchy();
		while (current != null) {
			entitySelection.addParent(businessEntityMapView.getBusinessEntity(current.getSimpleName()));
			current = current.getNextHierarchy();
		}
	}

	public void handleBusinessEntitySelection(AjaxBehaviorEvent evt) {
		String boSelection = (String) ((HtmlCommandLink) evt.getSource()).getValue();
		entitySelection = new SelectedBusinessEntityDto(businessEntityMapView.getBusinessEntity(boSelection));
		populateParents();
	}

	public void handleTypeListSelection(AjaxBehaviorEvent evt) {
		String boSelection = (String) ((HtmlCommandLink) evt.getSource()).getValue();
		typeListSelection = new SelectedTypeListDto(businessEntityMapView.getTypeList(boSelection));
	}

	public SelectedBusinessEntityDto getEntitySelection() {
		if (entitySelection == null && businessEntityMapView != null) {
			entitySelection = new SelectedBusinessEntityDto(businessEntityMapView.getBusinessEntitiesAsList().get(0));
			populateParents();
		}
		return entitySelection;
	}

	public SelectedTypeListDto getTypeListSelection() {
		if (typeListSelection == null && typeListSelection != null) {
			typeListSelection = new SelectedTypeListDto(businessEntityMapView.getTypeListsAsList().get(0));
		}
		return typeListSelection;
	}

	public List<String> getHierarchy() {
		List<String> hierarchy = new ArrayList<>();
		if (entitySelection != null) {
			hierarchy.add(" - " + entitySelection.getEntity().getHierarchy().getFullName());
			BusinessEntityHierarchy current = entitySelection.getEntity().getHierarchy().getNextHierarchy();
			String space = "      ";
			while (current != null) {
				hierarchy.add(space + " - " + current.getFullName());
				space = space + space;
				current = current.getNextHierarchy();
			}
		}
		return hierarchy;
	}

	public String getPlantUMLText() {
		String uml = umlBuilder.build(entitySelection.getEntity());
		System.out.println(uml);

		return uml;
	}
}
