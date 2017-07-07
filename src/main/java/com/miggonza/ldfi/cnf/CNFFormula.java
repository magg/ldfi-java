package com.miggonza.ldfi.cnf;

import java.util.Set;

public class CNFFormula {

	public BooleanFormula formula;
	

	public CNFFormula(BooleanFormula formula) {
		

	
		this.formula = null;
		
		BooleanFormula form = formula;
		int i = 0;
		
		while(!form.equals(this.formula)){
			//System.out.println(form.toString() + " is not "+ this.formula.toString());
			this.formula = form;
			form = form.convertToCNF();
			//log.info("iteration "+ i);
			//System.out.println("iteration "+ i);
			i = i +1;
		}
		
		this.formula = form.convertToCNF();
		
	}
	
	public Set<Set<Object>> conjuncts(){
		return this.formula.conjuncts();
	}
	
	
	
}
