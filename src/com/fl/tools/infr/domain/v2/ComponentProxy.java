package com.fl.tools.infr.domain.v2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentProxy {
	private Component theComponent;
	private Map<String, AttributeProxy> attributeMap;

	public ComponentProxy(Component theComponent) {
		this.theComponent = theComponent;
		this.attributeMap = new HashMap<>();
		theComponent.getAttributes().forEach((e) -> {
			this.attributeMap.put(e.getUUID(), new AttributeProxy(e));
		});
	}
	
	public Map<String, AttributeProxy> getAttributes() {
		return attributeMap;
	}

	public String getAbsoluteName() {
		return getPackageName() + "." + getName();
	}

	public boolean isDepricated() {
		return theComponent.getSteriotypes().contains(Steriotype.DEPRECATED);
	}

	public String getUUID() {
		return theComponent.getUUID();
	}

	public String getName() {
		return theComponent.getName();
	}

	public String getPackageName() {
		return theComponent.getPackageName();
	}

	public String getParent() {
		return theComponent.getParent();
	}

	public Set<Steriotype> getSteriotypes() {
		return Collections.unmodifiableSet(theComponent.getSteriotypes());
	}

	public Set<Modifier> getModifiers() {
		return Collections.unmodifiableSet(theComponent.getModifiers());
	}

	public Set<Annotation> getAnnotations() {
		return Collections.unmodifiableSet(theComponent.getAnnotations());
	}

	public Set<String> getChildren() {
		return Collections.unmodifiableSet(theComponent.getChildren());
	}

	public String getTableName() {
		for (Annotation a : theComponent.getAnnotations()) {
			if (a.getName().equalsIgnoreCase(Annotation.ANN_DATABASE_TABLE)) {
				return a.getValue();
			}
		}
		return "";
	}

	public String getDocumentation() {
		for (Annotation a : theComponent.getAnnotations()) {
			if (a.getName().equalsIgnoreCase(Annotation.ANN_DOCUMENTATION)) {
				return a.getValue();
			}
		}
		return "";
	}

	public String getShortName() {
		for (Annotation a : theComponent.getAnnotations()) {
			if (a.getName().equalsIgnoreCase(Annotation.ANN_FOREIGN_KEY_NAME)) {
				return a.getValue();
			}
		}
		return "";
	}

	public boolean isClass() {
		return theComponent.getSteriotypes().contains(Steriotype.CLASS);
	}

	public boolean isInterface() {
		return theComponent.getSteriotypes().contains(Steriotype.INTERFACE);
	}

	public boolean isEnum() {
		return theComponent.getSteriotypes().contains(Steriotype.ENUM);
	}

	public boolean isEntity() {
		return theComponent.getSteriotypes().contains(Steriotype.ENTITY);
	}

	public boolean isPublic() {
		return theComponent.getModifiers().contains(Modifier.PUBLIC);
	}

	public boolean isAbstract() {
		return theComponent.getModifiers().contains(Modifier.ABSTRACT);
	}

	public boolean isFinal() {
		return theComponent.getModifiers().contains(Modifier.FINAL);
	}

	public boolean isPrivate() {
		return theComponent.getModifiers().contains(Modifier.PRIVATE);
	}

	public boolean isProtected() {
		return theComponent.getModifiers().contains(Modifier.PROTECTED);
	}

	public boolean isStatic() {
		return theComponent.getModifiers().contains(Modifier.STATIC);
	}
}
