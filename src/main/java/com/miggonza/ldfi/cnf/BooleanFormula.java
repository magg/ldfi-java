package com.miggonza.ldfi.cnf;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Sets;

public abstract class BooleanFormula {
	
    public BooleanFormula left;

    public BooleanFormula right;
    
    public String value;
    
    public String operator;

	@Override
	public String toString() {
		if(this.value != null){
			return this.value;
			
		} else {
			return "(" + this.left +" "+ this.operator + " " + this.right + ")";
					
			
		}
	}
	
	
	public int clauses(){
		
		if (this.value != null){
			return 1;
			
		} else {
			return 1 + this.left.clauses() + this.right.clauses();
		}
		
	}
	
	public Set<Object> variables(){
		
		if (this.value != null){
			
			Set<Object> set = new HashSet<Object>();
			set.add(this.value);
			return set;
		} else {
			//this.left.variables().addAll(this.right.variables());
			
			//return this.left.variables();
			
			return  Sets.union(this.left.variables(), this.right.variables());
		}
		
	}

	public BooleanFormula(BooleanFormula left, BooleanFormula right, String value) {
		super();
		this.left = left;
		this.right = right;
		this.value = value;
		this.operator = "UNIMPLEMENTED";
	}
	
	public abstract <T> T conjuncts();
	
	public abstract Set<Object> disjuncts();
	
	public abstract boolean isCNF();
	
	public abstract BooleanFormula convertToCNF();




	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) 
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		BooleanFormula other = (BooleanFormula) obj;
		
	
		if ( value != null){
			return value.equals(other.value);
		} else {
			
			if (operator.equals(other.operator)){
				if (left.equals(other.left) && right.equals(other.right))
				
				return true;
				else
					return false;
			} else 
				return false;
					
		}
	}
	
	
	public int depth(){
		
		if (this.value != null){
			return 1;
		} else {
			int lft = this.left.depth();
			int rgh = this.right.depth();
			
			if (lft > rgh )
				return 1 + lft;
			else
				return 1 + rgh;	
		}
	}

}
