package com.fl.tools.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.fl.tools.common.dto.AttributeComponentDto;
import com.fl.tools.infr.domain.AttributeProxy;
import com.fl.tools.infr.domain.ComponentProxy;
import com.fl.tools.infr.domain.ComponentsMapView;

abstract public class ComponentUtils {
	public static Collection<AttributeComponentDto> getAssociatedComponents(ComponentsMapView componentView,
			ComponentProxy component) {
		Collection<AttributeComponentDto> components = new HashSet<>();

		getAttributes(componentView, component, true).forEach((e) -> {
			if (e.isEntity()) {
				components.add(new AttributeComponentDto(e, componentView.getComponent(e.getTypeUUID())));
			}
		});

		return components;
	}

	public static Collection<AttributeProxy> getAttributes(ComponentsMapView componentView, ComponentProxy component,
			boolean includeBase) {
		Collection<AttributeProxy> attrs = new ArrayList();
		component.getAttributes().forEach((k, v) -> {
			attrs.add(v);
		});

		if (includeBase) {
			ComponentProxy parent = componentView.getComponent(component.getParent());
			while (parent != null) {
				parent.getAttributes().forEach((k, v) -> {
					attrs.add(v);
				});
				parent = componentView.getComponent(parent.getParent());
			}
		}
		return attrs;
	}

}
