package com.fl.tools.infr.domain.v2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentsMapView {
	private Map<String, ComponentProxy> components = new HashMap<>();

	public ComponentsMapView(Set<Component> components) {
		components.forEach((e) -> {
			System.out.println("Adding " + e.getName() + " to map.");
			this.components.put(e.getUUID(), new ComponentProxy(e));
		});
	}

	public ComponentProxy getComponent(String uuid) {
		return this.components.get(uuid);
	}

	public Collection<ComponentProxy> getAllComponents() {
		return this.components.values();
	}

	public Map<String, ComponentProxy> getComponents() {
		return components;
	}

	public int size() {
		return this.components.size();
	}

	public boolean hasComponent(String uuid) {
		return this.components.containsKey(uuid);
	}
}
