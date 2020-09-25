package com.fl.tools.infr.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.tools.infr.domain.v2.ComponentsMapView;

public class Patch {
	private String n;
	private String sn;
	private Set<Attr> attrs;

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Set<Attr> getAttrs() {
		return attrs;
	}

	public void setAttrs(Set<Attr> attrs) {
		this.attrs = attrs;
	}

	public static Map<String, Patch> patches() {
		Map<String, Patch> p = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			// objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			Set<Patch> patches = objectMapper.readValue(
					BusinessEntityJsonDaoImpl.class.getClassLoader().getResourceAsStream("config/test.json"),
					new TypeReference<Set<Patch>>() {
					});
			patches.forEach((e) -> {
				p.put(e.getN(), e);
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return p;
	}

}
