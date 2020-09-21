package com.fl.tools.common.utils.uml;

public class ClassSkinDef implements Def {

	private ClassSkinOption skinOption;
	private String value;
	private String selection;

	public ClassSkinDef(ClassSkinOption skinOption, String value, String selection) {
		super();
		this.skinOption = skinOption;
		this.value = value;
		this.selection = selection;
	}

	@Override
	public String toPlantUMLText() {
		return "\n skinparam " + skinOption.name() + "<<" + selection + ">>" + " " + value;
	}

	@Override
	public String toString() {
		return toPlantUMLText();
	}

	@Override
	public int hashCode() {
		return toPlantUMLText().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && ((ClassSkinDef) obj).toPlantUMLText().equals(toPlantUMLText());
	}

}
