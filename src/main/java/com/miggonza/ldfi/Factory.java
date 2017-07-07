package com.miggonza.ldfi;

import java.util.HashMap;
import java.util.Map;

public class Factory {

	private int count;

	private final Map<String, Variable> variableCache;

	public Factory() {
		count = 1;
		variableCache = new HashMap<String, Variable>();
	}

	public Variable getVar(String name) {
		Variable var = variableCache.get(name);
		if (var == null) {
			var = new Variable(name, count++);
			variableCache.put(name, var);
		}
		return var;
	}

	public Literal positive(String name) {
		return new Literal(getVar(name), true);
	}

	public Literal negative(String name) {
		return new Literal(getVar(name), false);
	}

}