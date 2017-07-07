package com.miggonza.ldfi.cnf;

import java.util.HashMap;

public class SATVars {
	
	public HashMap<Object, Integer> var2num;
	public HashMap<Integer, Object> num2var;
	public int counter;
	
	
	public SATVars() {
		super();
		this.var2num = new HashMap<Object, Integer>();
		this.num2var = new HashMap<Integer, Object>();
		this.counter = 1;
	}
	
	
	public int lookupVar(Object var){
		if(!this.var2num.containsKey(var)){
			this.var2num.put(var, this.counter);
			this.num2var.put(this.counter, var);
			this.counter++;
		}
		
		return this.var2num.get(var);
	}
	
	public Object lookupNum(int num){
		if (num < 0){
			return "NOT "+ this.num2var.get(-num);
		} else {
			return this.num2var.get(num);
		}
	}

}
