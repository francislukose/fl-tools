package com.fl.tools.common.utils.uml;

import java.util.ArrayList;
import java.util.List;

public class ClassDef implements Def {
	private String name;
	private List<AttrDef> attrs = new ArrayList<>();

	public ClassDef(String name) {
		this.name = name;
	}

	public ClassDef addAttr(AttrDef attr) {
		attrs.add(attr);
		return this;
	}

	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\n");
		buffer.append("class ");
		buffer.append(name);
		buffer.append(" {");
		buffer.append("\n");

		attrs.forEach((e) -> {
			buffer.append(e.toPlantUMLText());
			buffer.append("\n");
		});

		buffer.append("\n");
		buffer.append("}");

		return buffer.toString();
	}
}
