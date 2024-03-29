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
import com.fl.tools.ui.ActionHandler;
import com.fl.tools.ui.beans.AttributeUIView;
import com.fl.tools.ui.beans.ComponentUIView;
import com.fl.tools.ui.beans.Datatable;
import com.fl.tools.ui.beans.DomainObjectsUIView;
import com.fl.tools.ui.beans.MethodUIView;

@Component
@ManagedBean
public class ComponentActionHandler extends ActionHandler {
	@Autowired
	private BasicPlantUMLBuilder basicUmlBuilder;
	@Autowired
	private InheritancePlantUMLBuilder inheritanceUmlBuilder;
	@Autowired
	private ERDPlantUMLBuilder erdPlantUMLBuilder;

	private ComponentProxy selectedComponent;
	private DomainObjectProxy selectedDomainObject;

	public void setSelectedComponent(ComponentProxy selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	public DomainObjectProxy getSelectedDomainObject() {
		DomainObjectsUIView domainObjectUIView = getManagedBean("domainObjectsUIView");

		if (selectedDomainObject == null && domainObjectUIView != null && !domainObjectUIView.isEmpty()) {
			selectedDomainObject = domainObjectUIView.getDomainObjects().getDomainObjects().values().iterator().next();
		}
		return selectedDomainObject;
	}

	public ComponentProxy getSelectedComponent() {
		ComponentUIView componentUIView = getManagedBean("componentUIView");

		if (selectedComponent == null && componentUIView.getSize() > 0) {
			selectedComponent = componentUIView.getComponents().getComponents().values().iterator().next();
		}
		return selectedComponent;
	}

	public Datatable<MethodUIView> getComponentMethods() {
		ComponentUIView componentUIView = getManagedBean("componentUIView");

		List<MethodUIView> attrs = new ArrayList<>();
		if (getSelectedComponent() != null) {
			ComponentProxy current = componentUIView.getComponents().getComponent(getSelectedComponent().getParent());
			while (current != null) {
				final String name = current.getName();
				current.getMethods().forEach((v) -> {
					attrs.add(new MethodUIView(v, true, name));
				});
				current = componentUIView.getComponents().getComponent(current.getParent());
			}

			getSelectedComponent().getMethods().forEach((v) -> {
				attrs.add(new MethodUIView(v));
			});
		}
		return new Datatable<>(attrs);
	}

	public Datatable<AttributeUIView> getComponentAttributes() {
		ComponentUIView componentUIView = getManagedBean("componentUIView");

		List<AttributeUIView> attrs = new ArrayList<>();
		if (getSelectedComponent() != null) {
			ComponentProxy current = componentUIView.getComponents().getComponent(getSelectedComponent().getParent());
			while (current != null) {
				final String name = current.getName();
				current.getAttributes().forEach((k, v) -> {
					attrs.add(new AttributeUIView(v, true, name));
				});
				current = componentUIView.getComponents().getComponent(current.getParent());
			}

			getSelectedComponent().getAttributes().forEach((k, v) -> {
				attrs.add(new AttributeUIView(v));
			});
		}
		return new Datatable<>(attrs);
	}

	public List<String> getHierarchy() {
		ComponentUIView componentUIView = getManagedBean("componentUIView");

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
		DomainObjectsUIView domainObjectUIView = getManagedBean("domainObjectsUIView");

		String uuid = (String) evt.getComponent().getAttributes().get("uuid");
		selectedDomainObject = domainObjectUIView.getDomainObjects().getDomainObjects().get(uuid);
	}

	public void handleComponentSelection(AjaxBehaviorEvent evt) {
		ComponentUIView componentUIView = getManagedBean("componentUIView");

		String uuid = (String) evt.getComponent().getAttributes().get("uuid");
		selectedComponent = componentUIView.getComponents().getComponent(uuid);
	}

}
