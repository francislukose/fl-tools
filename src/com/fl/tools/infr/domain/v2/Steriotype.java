package com.fl.tools.infr.domain.v2;

public class Steriotype {
	public static final Steriotype UNIDIRECTIONAL = new Steriotype("UniDirectional");
	public static final Steriotype ONE_TO_MANY = new Steriotype("OneToMany");
	public static final Steriotype ONE_TO_ONE = new Steriotype("OneToOne");
	public static final Steriotype MANY_TO_MANY = new Steriotype("ManyToMany");
	public static final Steriotype MANY_TO_ONE = new Steriotype("ManyToOne");

	public static final Steriotype COLLECTION = new Steriotype("collection");
	public static final Steriotype DEPRECATED = new Steriotype("deprecated");

	public static final Steriotype ENUM = new Steriotype("enum");
	public static final Steriotype CLASS = new Steriotype("class");
	public static final Steriotype INTERFACE = new Steriotype("interface");

	public static final Steriotype ENTITY = new Steriotype("entity");
	public static final Steriotype FOREIGN_KEY = new Steriotype("ForeignKey");

	private String name;

	public Steriotype() {
		this("");
	}

	public Steriotype(String name) {
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
		return "Steriotype [name=" + name + "]";
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
		Steriotype other = (Steriotype) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
