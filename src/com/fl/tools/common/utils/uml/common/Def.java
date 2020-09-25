package com.fl.tools.common.utils.uml.common;

public interface Def {
	public static final Def NULL_DEF = new Def() {

		@Override
		public String toPlantUMLText() {
			return "";
		}
	};

	public String toPlantUMLText();
}
