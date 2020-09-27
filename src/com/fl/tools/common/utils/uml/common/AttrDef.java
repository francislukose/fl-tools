package com.fl.tools.common.utils.uml.common;

public class AttrDef implements Def {
	private AttributeModifier[] modifier;
	private String name;
	private String type;

	public AttrDef(AttributeModifier[] modifier, String name, String type) {
		this.modifier = modifier;
		this.name = name;
		this.type = type;
	}

	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();
		for (AttributeModifier am : modifier) {
			buffer.append(am.getValue());
			buffer.append(" ");
		}
		buffer.append(name);
		buffer.append(" : ");
		buffer.append(type);

		return buffer.toString();
	}

}