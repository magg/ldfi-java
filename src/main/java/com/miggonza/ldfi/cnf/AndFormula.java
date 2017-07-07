package com.miggonza.ldfi.cnf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Sets;

public class AndFormula extends BooleanFormula {
	
	
	public AndFormula(BooleanFormula left, BooleanFormula right) {
		super(left, right, null);
		this.operator = "AND";
		// TODO Auto-generated constructor stub
	}
	
	
	public Set<Object> disjuncts(){
		return new HashSet<Object>();
	}
	
	public Set<Set<Object>> conjuncts(){
		Set<Set<Object>> ret = new HashSet<Set<Object>>();
		
		if (this.left.operator.equals("OR")){
			Set<Object> tmp = Collections.unmodifiableSet(this.left.disjuncts());
			ret.add(tmp);
			
		} else {
			//ret.addAll(Collections.unmodifiableSet(this.left.conjuncts()));
			ret = Sets.union(ret, Collections.unmodifiableSet(this.left.conjuncts()) );

		}
		
		if (this.right.operator.equals("OR")){
			Set<Object> tmp = Collections.unmodifiableSet(this.right.disjuncts());
			ret.add(tmp);
			
		} else {
			//ret.addAll(Collections.unmodifiableSet(this.right.conjuncts()));
			
			ret = Sets.union(ret, Collections.unmodifiableSet(this.right.conjuncts()) );

		}
		
		
		
		return ret;
	}
	
	
	public boolean isCNF(){
		return this.left.isCNF() && this.right.isCNF();
	}


	@Override
	public BooleanFormula convertToCNF() {
		// TODO Auto-generated method stub
		return new AndFormula(this.left.convertToCNF(), this.right.convertToCNF());
	}

    
}