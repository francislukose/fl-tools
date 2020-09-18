package com.fl.tools.fineos.infr.domain;

public class BusinessEntityAttribute {
	private String attributeName;
	private String typeFullName;
	private String typeSimpleName;
	private String description;
	private boolean many;
	private boolean deprecated;
	private String relationship;
	private boolean businessObjectType;
	private boolean extension;
	private boolean enumType;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getTypeFullName() {
		return typeFullName;
	}

	public void setTypeFullName(String typeFullName) {
		this.typeFullName = typeFullName;
	}

	public String getTypeSimpleName() {
		return typeSimpleName;
	}

	public void setTypeSimpleName(String typeSimpleName) {
		this.typeSimpleName = typeSimpleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMany() {
		return many;
	}

	public void setMany(boolean many) {
		this.many = many;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public boolean isBusinessObjectType() {
		return businessObjectType;
	}

	public void setBusinessObjectType(boolean businessObjectType) {
		this.businessObjectType = businessObjectType;
	}

	public boolean isExtension() {
		return extension;
	}

	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	public boolean isOneToOne() {
		return relationship.equalsIgnoreCase("OneToOne");
	}
	public boolean isOneToMany() {
		return relationship.equalsIgnoreCase("OneToMany");
	}
	public boolean isManyToMany() {
		return relationship.equalsIgnoreCase("ManyToMany");
	}
	public boolean isManyToOne() {
		return relationship.equalsIgnoreCase("ManyToOne");
	}
	public boolean isEnumType() {
		return getTypeFullName().contains(".enums.");
	}

}
