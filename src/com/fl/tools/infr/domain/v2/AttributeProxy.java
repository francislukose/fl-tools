package com.fl.tools.infr.domain.v2;

import java.util.Collections;
import java.util.Set;

public class AttributeProxy {
	private Attribute theAttribute;

	public AttributeProxy(Attribute theAttribute) {
		this.theAttribute = theAttribute;
	}

	public String getName() {
		return theAttribute.getName();
	}

	public String getTypeUUID() {
		return theAttribute.getUUID();
	}

	public String getType() {
		return theAttribute.getType();
	}

	public String getDefaultValue() {
		return theAttribute.getDefaultValue();
	}

	public Set<Steriotype> getSteriotypes() {
		return Collections.unmodifiableSet(theAttribute.getSteriotypes());
	}

	public Set<Modifier> getModifiers() {
		return Collections.unmodifiableSet(theAttribute.getModifiers());
	}

	public Set<Annotation> getAnnotations() {
		return Collections.unmodifiableSet(theAttribute.getAnnotations());
	}

	public String getDocumentation() {
		for (Annotation a : theAttribute.getAnnotations()) {
			if (a.getName().equalsIgnoreCase(Annotation.ANN_DOCUMENTATION)) {
				return a.getValue();
			}
		}
		return "";
	}

	public boolean isCollection() {
		return theAttribute.getSteriotypes().contains(Steriotype.COLLECTION);
	}

	public boolean isDepricated() {
		return theAttribute.getSteriotypes().contains(Steriotype.DEPRECATED);
	}

	public boolean isForignKey() {
		return theAttribute.getSteriotypes().contains(Steriotype.FOREIGN_KEY);
	}

	public boolean isOneToOne() {
		return theAttribute.getSteriotypes().contains(Steriotype.ONE_TO_ONE);
	}

	public boolean isOneToMany() {
		return theAttribute.getSteriotypes().contains(Steriotype.ONE_TO_MANY);
	}

	public boolean isManyToOne() {
		return theAttribute.getSteriotypes().contains(Steriotype.MANY_TO_ONE);
	}

	public boolean isManyToMany() {
		return theAttribute.getSteriotypes().contains(Steriotype.MANY_TO_MANY);
	}

	public boolean isEnumType() {
		return theAttribute.getSteriotypes().contains(Steriotype.ENUM);
	}

	public boolean isEntity() {
		return theAttribute.getSteriotypes().contains(Steriotype.ENTITY);
	}

	public boolean isPublic() {
		return theAttribute.getModifiers().contains(Modifier.PUBLIC);
	}

	public boolean isFinal() {
		return theAttribute.getModifiers().contains(Modifier.FINAL);
	}

	public boolean isPrivate() {
		return theAttribute.getModifiers().contains(Modifier.PRIVATE);
	}

	public boolean isProtected() {
		return theAttribute.getModifiers().contains(Modifier.PROTECTED);
	}

	public boolean isStatic() {
		return theAttribute.getModifiers().contains(Modifier.STATIC);
	}
}
