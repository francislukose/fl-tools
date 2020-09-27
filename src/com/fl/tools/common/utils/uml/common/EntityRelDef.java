package com.fl.tools.common.utils.uml.common;

public class EntityRelDef implements Def, Sortable {
	private String from;
	private String to;
	private EntityRelationship relationship;

	public EntityRelDef(String from, String to, EntityRelationship relationship) {
		super();
		this.from = from;
		this.to = to;
		this.relationship = relationship;
	}

	@Override
	public String toPlantUMLText() {
		return "\n" + from + " " + relationship.getValue() + " " + to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityRelDef other = (EntityRelDef) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (relationship != other.relationship)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public int getSortOrder() {
		return 2;
	}

}
