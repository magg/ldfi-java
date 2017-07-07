package com.miggonza.ldfi.cnf;


public class HelloWorldApp {
	
	
    public static void main(String[] args) {
        
    	
    	Literal r = new Literal("foo");
    	Literal	l = new Literal("bar");

    	AndFormula	t = new AndFormula(l, r);
    	OrFormula	o = new OrFormula(t, t);

    	AndFormula	s = new AndFormula(o, t);
    	
    	
    	System.out.println(s);
   
   	
    }
}