package com.fl.tools.infr.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessEntity {
	public static BusinessEntity BASE_BUSINESS_ENTITY = new BusinessEntity();
	static {
		BASE_BUSINESS_ENTITY.setAttributes(new HashMap<>());
		BASE_BUSINESS_ENTITY.setSimpleName("BusinessEntity");
	}

	private String name;
	private String packageName;
	private String tableName;
	private String simpleName;
	private String fullName;
	private String description;
	private boolean deprecated;
	private Map<String, BusinessEntityAttribute> attributes;
	private BusinessEntityHierarchy hierarchy;
	private List<String> siblings;
	private List<String> children;

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public List<String> getSiblings() {
		return siblings;
	}

	public void setSiblings(List<String> siblings) {
		this.siblings = siblings;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public Map<String, BusinessEntityAttribute> getAttributes() {
		return attributes;
	}

	public List<BusinessEntityAttribute> getAttributesAsList() {
		List<BusinessEntityAttribute> aList = new ArrayList<>();
		if (attributes != null) {
			attributes.values().forEach((e) -> aList.add(e));
		}
		return aList;
	}

	public void setAttributes(Map<String, BusinessEntityAttribute> attributes) {
		this.attributes = attributes;
	}

	public BusinessEntityHierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(BusinessEntityHierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

}
