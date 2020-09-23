package com.fl.tools.ui.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.v2.ComponentProxy;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

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
		components.getComponents().forEach((k, v) -> {
			elements.add(v);
		});
		return elements;
	}

	public int getSize() {
		return components.size();
	}
}
