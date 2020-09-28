package com.fl.tools.infr.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DomainObjectsMapView {
	private Map<String, DomainObjectProxy> domainObjects = new HashMap<>();

	public DomainObjectsMapView() {

	}

	public DomainObjectsMapView(Collection<DomainObject> domainObjects) {
		if (domainObjects != null) {
			domainObjects.forEach((e) -> {
				this.domainObjects.put(e.getUUID(), new DomainObjectProxy(e));
			});
		}
	}

	public Map<String, DomainObjectProxy> getDomainObjects() {
		return Collections.unmodifiableMap(domainObjects);
	}
}
