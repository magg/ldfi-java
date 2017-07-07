package com.miggonza.ldfi.cnf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Literal extends BooleanFormula {

	public Literal(String value) {
		super(null, null, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Set<Object>> conjuncts() {
		Set<Object> ret = new HashSet<Object>();
		ret.add(this.value);
		Set<Object> tmp = Collections.unmodifiableSet(ret);
		Set<Set<Object>> ret2 = new HashSet<Set<Object>>();
		ret2.add(tmp);
		return  ret2;
	}

	@Override
	public Set<Object> disjuncts() {
		Set<Object> ret = new TreeSet<Object>();
		ret.add(this.value);
		return ret;
	}

	@Override
	public boolean isCNF() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public BooleanFormula convertToCNF() {
		// TODO Auto-generated method stub
		return this;
	}
	
	

}
