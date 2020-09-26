package com.fl.tools.common.utils.uml.common;

public class InheritanceRelDef extends RelDef {

	public InheritanceRelDef(String from, String to, ViewPosition pos) {
		super(from, to, pos);
	}

	@Override
	public String toPlantUMLText() {
		return "\n" + from + " <|" + pos.label + " " + to + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && toPlantUMLText().equalsIgnoreCase(((InheritanceRelDef) obj).toPlantUMLText());
	}

	@Override
	public int hashCode() {
		return toPlantUMLText().hashCode();
	}
}