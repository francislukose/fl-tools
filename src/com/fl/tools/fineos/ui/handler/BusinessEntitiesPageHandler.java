package com.fl.tools.fineos.ui.handler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.fineos.common.dto.SelectedBusinessEntityDto;
import com.fl.tools.fineos.infr.domain.BusinessEntityHierarchy;
import com.fl.tools.fineos.ui.beans.BusinessEntityMapView;

@Component
@ManagedBean
public class BusinessEntitiesPageHandler {
	@Autowired
	private BusinessEntityMapView businessEntityMapView;
	private SelectedBusinessEntityDto entitySelection;

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

	public SelectedBusinessEntityDto getEntitySelection() {
		if (entitySelection == null && businessEntityMapView != null) {
			entitySelection = new SelectedBusinessEntityDto(businessEntityMapView.getBusinessEntitiesAsList().get(0));
			populateParents();
		}
		return entitySelection;
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
}
