package com.fl.tools.common.utils.uml.common;

public class AttrDef implements Def {
	private String modifier;
	private String name;
	private String type;

	public AttrDef(String modifier, String name, String type) {
		this.modifier = modifier;
		this.name = name;
		this.type = type;
	}

	public String toPlantUMLText() {
		return modifier + name + " : " + type;
	}

}