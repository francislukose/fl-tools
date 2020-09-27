package com.fl.tools.common.utils.uml.common;

public class SimpleDef implements Def,Sortable {
	private String defText;

	public SimpleDef(String s) {
		this.defText = s;
	}

	@Override
	public String toPlantUMLText() {

		return "\n" + defText;
	}

	@Override
	public int hashCode() {
		return defText.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && ((SimpleDef) obj).defText.equalsIgnoreCase(defText);
	}

	@Override
	public int getSortOrder() {
		return 3;
	}

}
