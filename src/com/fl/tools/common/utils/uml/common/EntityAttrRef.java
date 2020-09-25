package com.fl.tools.common.utils.uml.common;

import org.eclipse.jdt.internal.compiler.ast.SuperReference;

public class EntityAttrRef extends AttrDef {
	private String size;
	private String steriotype;

	public EntityAttrRef(String modifier, String name, String type) {
		super(modifier, name, type);
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
		buffer.append(super.toPlantUMLText());
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
