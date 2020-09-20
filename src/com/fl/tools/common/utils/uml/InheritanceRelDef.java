package com.fl.tools.common.utils.uml;

public class InheritanceRelDef extends RelDef{

	public InheritanceRelDef(String from, String to, ViewPosition pos) {
		super(from, to, pos);
	}

	@Override
	public String toPlantUMLText() {
		return "\n" + from + " <|" + pos.label + " " + to + "\n";
	}
}
