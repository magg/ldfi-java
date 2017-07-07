package com.miggonza.ldfi.cnf;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

public class OrFormula extends BooleanFormula {
	
	public OrFormula(BooleanFormula left, BooleanFormula right) {
		super(left, right, null);
		this.operator = "OR";

		// TODO Auto-generated constructor stub
	}
	
	public boolean isCNF(){
		if (this.left.operator.equals("AND") || this.right.operator.equals("AND")) {
			return false;
		} else {
			return this.left.isCNF() && this.right.isCNF();
		}
	}
	
	public Set<Object> disjuncts(){
		if (!this.isCNF()){
			System.out.println("FORMULA not cnf");
			System.exit(-1);
		} else {
			//this.left.disjuncts().addAll(this.right.disjuncts());
			//return this.left.disjuncts();
			return Sets.union(this.left.disjuncts(), this.right.disjuncts());
			
		}
		return null;
	}
	
	public Set<Object> conjuncts(){
		Set<Object>  ret = Sets.union(this.left.disjuncts(), this.right.disjuncts());
		//this.left.disjuncts().addAll(this.right.disjuncts());
		return Collections.unmodifiableSet(ret);
		//return Collections.unmodifiableSet(this.left.disjuncts());
	}

	@Override
	public BooleanFormula convertToCNF() {
		// TODO Auto-generated method stub
		
		if(this.right.operator.equals("AND")){
			BooleanFormula lft = this.left.convertToCNF();
			return new AndFormula(new OrFormula(lft, this.right.left.convertToCNF()), new OrFormula(lft,this.right.right.convertToCNF()));
		} else if (this.left.operator.equals("AND")) {
			BooleanFormula rgh = this.right.convertToCNF();
			return new AndFormula(new OrFormula(this.left.left.convertToCNF(), rgh), new OrFormula(this.left.right.convertToCNF(), rgh));
		} else {
			return new OrFormula(this.left.convertToCNF(), this.right.convertToCNF());
			
		}
	}

}
