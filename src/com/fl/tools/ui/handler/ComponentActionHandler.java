package com.fl.tools.ui.handler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.common.utils.uml.builder.BasicPlantUMLBuilder;
import com.fl.tools.common.utils.uml.builder.ERDPlantUMLBuilder;
import com.fl.tools.common.utils.uml.builder.InheritancePlantUMLBuilder;
import com.fl.tools.infr.domain.ComponentProxy;
import com.fl.tools.infr.domain.DomainObjectProxy;
import com.fl.tools.ui.beans.AttributeUIView;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.DomainObjectsUIView;

@Component
@ManagedBean
public class ComponentActionHandler {
	@Autowired
	private BasicPlantUMLBuilder basicUmlBuilder;
	@Autowired
	private InheritancePlantUMLBuilder inheritanceUmlBuilder;
	@Autowired
	private ERDPlantUMLBuilder erdPlantUMLBuilder;

	@Autowired
	private ComponentUIView componentUIView;
	@Autowired
	private DomainObjectsUIView domainObjectUIView;

	private ComponentProxy selectedComponent;
	private DomainObjectProxy selectedDomainObject;

	public void setSelectedComponent(ComponentProxy selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	public DomainObjectProxy getSelectedDomainObject() {
		if (selectedDomainObject == null) {
			selectedDomainObject = domainObjectUIView.getDomainObjects().getDomainObjects().values().iterator().next();
		}
		return selectedDomainObject;
	}

	public ComponentProxy getSelectedComponent() {
		if (selectedComponent == null) {
			selectedComponent = componentUIView.getComponents().getComponents().values().iterator().next();
		}
		return selectedComponent;
	}

	public List<AttributeUIView> getComponentAttributes() {
		List<AttributeUIView> attrs = new ArrayList<>();
		if (selectedComponent != null) {
			ComponentProxy current = componentUIView.getComponents().getComponent(selectedComponent.getParent());
			while (current != null) {
				final String name = current.getName();
				current.getAttributes().forEach((k, v) -> {
					attrs.add(new AttributeUIView(v, true, name));
				});
				current = componentUIView.getComponents().getComponent(current.getParent());
			}

			selectedComponent.getAttributes().forEach((k, v) -> {
				attrs.add(new AttributeUIView(v));
			});
		}
		return attrs;
	}

	public List<String> getHierarchy() {
		List<String> hierarchy = new ArrayList<>();
		if (selectedComponent != null) {
			hierarchy.add(" - " + selectedComponent.getAbsoluteName());
			ComponentProxy current = componentUIView.getComponents().getComponent(selectedComponent.getParent());
			String space = "      ";
			while (current != null) {
				hierarchy.add(space + " - " + current.getAbsoluteName());
				space = space + space;
				current = componentUIView.getComponents().getComponent(current.getParent());
			}
		}
		return hierarchy;
	}

	public String getErdPlantUMLText() {
		if (selectedComponent != null) {
			String uml = erdPlantUMLBuilder.build(selectedComponent);
			System.out.println(uml);
			return uml;
		}
		return "";
	}

	public String getBasicPlantUMLText() {
		if (selectedComponent != null) {
			return basicUmlBuilder.build(selectedComponent);
		}
		return "";
	}

	public String getInheritancePlantUMLText() {
		if (selectedComponent != null) {
			return inheritanceUmlBuilder.build(selectedComponent);
		}
		return "";
	}

	public void handleDomainObjectSelection(AjaxBehaviorEvent evt) {
		String uuid = (String) evt.getComponent().getAttributes().get("uuid");
		selectedDomainObject = domainObjectUIView.getDomainObjects().getDomainObjects().get(uuid);
	}

	public void handleComponentSelection(AjaxBehaviorEvent evt) {
		String uuid = (String) evt.getComponent().getAttributes().get("uuid");
		selectedComponent = componentUIView.getComponents().getComponent(uuid);
	}

}
