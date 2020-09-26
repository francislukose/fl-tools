package com.fl.tools.infr.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AttributeProxy {
	private Attribute theAttribute;

	public AttributeProxy(Attribute theAttribute) {
		this.theAttribute = theAttribute;
	}

	public Attribute getActualObject() {
		return theAttribute;
	}

	public String getName() {
		return theAttribute.getName();
	}

	public String getTypeUUID() {
		return theAttribute.getTypeUUID();
	}

	public String getType() {
		return theAttribute.getType();
	}

	public String getUUID() {
		return theAttribute.getUUID();
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

	public Set<Annotation> getDatabaseColumns() {
		Set<Annotation> cols = new HashSet<>();
		theAttribute.getAnnotations().forEach((e) -> {
			if (Annotation.ANN_DATABASE_COL.equalsIgnoreCase(e.getName())) {
				cols.add(e);
			}
		});
		return cols;
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

	public boolean isDeprecated() {
		return theAttribute.getSteriotypes().contains(Steriotype.DEPRECATED);
	}

	public boolean isForeignKey() {
		return theAttribute.getSteriotypes().contains(Steriotype.FOREIGN_KEY);
	}

	public boolean isPrimaryKey() {
		return theAttribute.getSteriotypes().contains(Steriotype.PRIMARY_KEY);
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

	@Override
	public String toString() {
		return theAttribute.toString();
	}
}
