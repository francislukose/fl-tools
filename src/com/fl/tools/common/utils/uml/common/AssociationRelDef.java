package com.fl.tools.common.utils.uml.common;

public class AssociationRelDef extends RelDef {
	private String attrName;
	private Cardinality fromCardinality;
	private Cardinality toCardinality;
	private ObjectOwner owner;

	public AssociationRelDef(String from, String to, String attrName) {
		this.from = from;
		this.to = to;
		this.attrName = attrName;
	}

	public AssociationRelDef setFromCardinality(Cardinality c) {
		this.fromCardinality = c;
		return this;
	}

	public AssociationRelDef setToCardinality(Cardinality c) {
		this.toCardinality = c;
		return this;
	}

	public AssociationRelDef setOwner(ObjectOwner o) {
		this.owner = o;
		return this;
	}

	public AssociationRelDef setPos(ViewPosition p) {
		this.pos = p;
		return this;
	}

	@Override
	public String toPlantUMLText() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\n");
		buffer.append(this.from);
		buffer.append(" ");
		buffer.append("\"");
		buffer.append(this.fromCardinality.label);
		buffer.append("\"");
		buffer.append(" ");
		if (this.owner == ObjectOwner.FROM) {
			buffer.append("o");
		}
		buffer.append(this.pos.label);
		if (this.owner == ObjectOwner.TO) {
			buffer.append("o");
		}
		buffer.append(" ");
		buffer.append("\"");
		buffer.append(this.toCardinality.label);
		buffer.append(" ");
		buffer.append(this.attrName);
		buffer.append("\"");
		buffer.append(" ");
		buffer.append(this.to);

		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && toPlantUMLText().equalsIgnoreCase(((AssociationRelDef) obj).toPlantUMLText());
	}

	@Override
	public int hashCode() {
		return toPlantUMLText().hashCode();
	}
}
