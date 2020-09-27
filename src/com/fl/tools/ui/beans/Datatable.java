package com.fl.tools.ui.beans;

import java.util.Collection;

public class Datatable<E> {
	private Collection<E> results;

	public Datatable(Collection<E> results) {
		this.results = results;
	}

	public Collection<E> getResults() {
		return results;
	}

	public boolean getIsEmpty() {
		return results == null || results.isEmpty();
	}
}
