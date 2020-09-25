package com.fl.tools.common.utils.uml.common;

public class PosSelector {
	public static PosSelector newInstance() {
		return new PosSelector();
	}

	private boolean up = true;

	public ViewPosition getPos() {
		if (up) {
			up = false;
			return ViewPosition.UP;
		}
		up = true;
		return ViewPosition.DOWN;
	}
}
