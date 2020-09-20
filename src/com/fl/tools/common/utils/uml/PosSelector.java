package com.fl.tools.common.utils.uml;

public class PosSelector {
	static PosSelector newInstance() {
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
