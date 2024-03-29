package com.fl.tools.ui.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.AttributeProxy;
import com.fl.tools.infr.domain.ComponentProxy;
import com.fl.tools.infr.domain.ComponentsMapView;
import com.fl.tools.infr.domain.DomainObjectsMapView;

@Component
@ManagedBean
@SessionScoped
public class ComponentUIView {
	private ComponentsMapView components;

	public ComponentsMapView getComponents() {
		return components;
	}

	public void setComponents(ComponentsMapView components) {
		this.components = components;
	}

	public List<ComponentProxy> getComponentsAsList() {
		List<ComponentProxy> elements = new ArrayList<>();
		if (components != null) {
			components.getComponents().forEach((k, v) -> {
				elements.add(v);
			});
		}
		return elements;
	}

	public int getSize() {
		if (components == null) {
			return 0;
		}
		return components.size();
	}
}
