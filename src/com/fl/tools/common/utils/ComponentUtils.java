package com.fl.tools.common.utils;

import java.util.ArrayList;
import java.util.Collection;

import com.fl.tools.infr.domain.v2.AttributeProxy;
import com.fl.tools.infr.domain.v2.ComponentProxy;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

abstract public class ComponentUtils {
	public static Collection<AttributeProxy> getAttributes(ComponentsMapView componentView, ComponentProxy component,
			boolean includeBase) {
		Collection<AttributeProxy> attrs = new ArrayList();
		System.out.println(component.getName());
		component.getAttributes().forEach((k, v) -> {
			System.out.println("\t: Adding -> " + k);
			attrs.add(v);
		});

		if (includeBase) {
			ComponentProxy parent = componentView.getComponent(component.getParent());
			while (parent != null) {
				System.out.println(parent.getName());
				parent.getAttributes().forEach((k, v) -> {
					attrs.add(v);
					System.out.println("\t: Adding -> " + k);
				});
				parent = componentView.getComponent(parent.getParent());
			}
		}
		return attrs;
	}

}
