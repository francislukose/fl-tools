package com.fl.tools.common.utils.uml.common;

public abstract class RelDef implements Def{
	protected String from;
	protected String to;
	protected ViewPosition pos;

	public RelDef() {

	}

	public RelDef(String from, String to, ViewPosition pos) {
		super();
		this.from = from;
		this.to = to;
		this.pos = pos;
	}
}
