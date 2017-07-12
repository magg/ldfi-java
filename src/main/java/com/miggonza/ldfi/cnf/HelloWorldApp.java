package com.miggonza.ldfi.cnf;

import com.miggonza.ldfi.convert.Node2;

public class HelloWorldApp {
	
	
    public static void main(String[] args) {
        
    	
    	Literal r = new Literal("foo");
    	Literal	l = new Literal("bar");

    	AndFormula	t = new AndFormula(l, r);
    	OrFormula	o = new OrFormula(t, t);

    	AndFormula	s = new AndFormula(o, t);
    	
    	
    	System.out.println(s);
    	
        Node2 g = new Node2(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
        
        System.out.println("Following is a Topological " +
                "sort of the given graph");
g.topologicalSort();
   
   	
    }
}