package com.fl.tools.infr.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentsMapView {
	private Map<String, ComponentProxy> components = new HashMap<>();

	public ComponentsMapView(Set<Component> components) {
		components.forEach((e) -> {
			this.components.put(e.getUUID(), new ComponentProxy(e));
		});
	}

	public ComponentProxy getComponent(String uuid) {
		return this.components.get(uuid);
	}

	public ComponentProxy getEntityComponentByTableName(String tableName) {
		ComponentProxy componentProxy = null;
		for (ComponentProxy cp : components.values()) {
			if (cp.isEntity() && tableName.equalsIgnoreCase(cp.getTableName())) {
				componentProxy = cp;
				break;
			}
		}
		return componentProxy;
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
