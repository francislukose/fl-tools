package com.fl.tools.common.utils.uml.common;

import java.util.ArrayList;
import java.util.List;

public class ClassDef implements Def {
	private ClassModifier[] modifiers;
	private String name;
	private String steriotype;
	private List<AttrDef> attrs = new ArrayList<>();

	public ClassDef(ClassModifier[] modifiers, String name) {
		this.modifiers = modifiers;
		this.name = name;
	}

	public ClassDef addAttr(AttrDef attr) {
		attrs.add(attr);
		return this;
	}

	public String getSteriotype() {
		return steriotype;
	}

	public void setSteriotype(String steriotype) {
		this.steriotype = steriotype;
	}

	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\n");
		for (ClassModifier cm : modifiers) {
			buffer.append(cm.getValue());
			buffer.append(" ");
		}

		buffer.append(name);

		if (steriotype != null) {
			buffer.append(" <<");
			buffer.append(steriotype);
			buffer.append(">>");
		}
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

	@Override
	public boolean equals(Object obj) {
		return obj != null && name.equalsIgnoreCase(((ClassDef) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
