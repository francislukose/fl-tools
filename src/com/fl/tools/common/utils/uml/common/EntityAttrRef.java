package com.fl.tools.common.utils.uml.common;

public class EntityAttrRef implements Def {
	private String modifier;
	private String name;
	private String type;

	private String size;
	private String steriotype;

	public EntityAttrRef(String modifier, String name, String type) {
		this.modifier = modifier;
		this.name = name;
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public EntityAttrRef setSize(String size) {
		this.size = size;
		return this;
	}

	public String getSteriotype() {
		return steriotype;
	}

	public EntityAttrRef setSteriotype(String steriotype) {
		this.steriotype = steriotype;
		return this;
	}

	@Override
	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(modifier + " " + name + " : " + type);
		if (size != null && size.length() > 0) {
			buffer.append(" (");
			buffer.append(size);
			buffer.append(")");
		}
		if (steriotype != null && steriotype.length() > 0) {
			buffer.append(" <<");
			buffer.append(steriotype);
			buffer.append(">>");
		}
		return buffer.toString();
	}

}
