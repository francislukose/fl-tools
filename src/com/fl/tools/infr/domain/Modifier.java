package com.fl.tools.infr.domain;

public class Modifier {
	public static final Modifier PUBLIC = new Modifier("public");
	public static final Modifier STATIC = new Modifier("static");
	public static final Modifier ABSTRACT = new Modifier("abstract");
	public static final Modifier FINAL = new Modifier("final");
	public static final Modifier PROTECTED = new Modifier("protected");
	public static final Modifier PRIVATE = new Modifier("private");
	public static final Modifier DEFAULT = new Modifier("default");

	private String name;

	public Modifier() {
		this("default");
	}

	public Modifier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Modifier [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Modifier other = (Modifier) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
