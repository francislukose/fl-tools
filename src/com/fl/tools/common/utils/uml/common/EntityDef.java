package com.fl.tools.common.utils.uml.common;

import java.util.ArrayList;
import java.util.List;

public class EntityDef implements Def {

	private String name;
	private List<Def> attrs = new ArrayList<>();

	public EntityDef(String name) {
		this.name = name;
	}

	public EntityDef addAttr(Def attr) {
		attrs.add(attr);
		return this;
	}

	@Override
	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nentity \"");
		buffer.append(name);
		buffer.append("\" as ");
		buffer.append(name);
		buffer.append("{\n");

		attrs.forEach((e) -> {
			buffer.append(e.toPlantUMLText());
			buffer.append("\n");
		});

		buffer.append("\n}");

		return buffer.toString();
	}

	public int hashCode() {
		return name.hashCode();
	};

	@Override
	public boolean equals(Object obj) {
		return obj != null && ((EntityDef) obj).name.equalsIgnoreCase(name);
	}
}
