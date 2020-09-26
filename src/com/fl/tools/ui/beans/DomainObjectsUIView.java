package com.fl.tools.ui.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.tools.infr.domain.DomainObjectProxy;
import com.fl.tools.infr.domain.DomainObjectsMapView;

@Component
@ManagedBean
@SessionScoped
public class DomainObjectsUIView {
	private DomainObjectsMapView domainObjects;

	public DomainObjectsMapView getDomainObjects() {
		return domainObjects;
	}

	public void setDomainObjects(DomainObjectsMapView domainObjects) {
		this.domainObjects = domainObjects;
	}

	public List<DomainObjectProxy> getDomainObjectsAsList() {
		List<DomainObjectProxy> elements = new ArrayList<>();
		domainObjects.getDomainObjects().forEach((k, v) -> {
			elements.add(v);
		});
		return elements;
	}

	public int getSize() {
		return domainObjects.getDomainObjects().size();
	}
}
