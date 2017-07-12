package com.miggonza.ldfi.convert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.miggonza.ldfi.cnf.Literal;
import com.miggonza.ldfi.cnf.OrFormula;
import com.miggonza.ldfi.convert.Dag.DagNode;

public abstract class AbstractGraphVisitor implements GraphVisitor< Stack<Literal>, Stack<Literal>> {

	@Override
	public Stack<Literal> visitNode(Dag<?>.DagNode node,  Stack<Literal> arg) {

		
		
		
		
		//}
		
		
		/*
		if (node.getAChild() != null ){
			
		}
		
		
		
		
		for (Dag<?>.DagNode n: node.getChildren()){
			Node x = (Node) n.get();
			System.out.println(x);
		}
		
		
		Node n1 = (Node) node.getAChild().get();
		Literal l1 = new Literal(n1.name);
		
		
		result = new OrFormula(l0,l1);





		Node n1  = (Node) node.get();
		Literal result = new Literal(n1.name);


for (Dag<?>.DagNode j : node.getChildren()) {
	Node n0  = (Node) j.get();
	if (!n0.type.equals("goal") && !n0.type.equals("init")) {
		//out.add(j);
		//unvisited.add(j);
		Literal l0 = new Literal(n0.name);				
		//result = new OrFormula(l0,l1);
		
		
		
		//Literal l1  = this.visitNode(j, arg);
		System.out.println("RULE "+ l0.toString() + " " );
	}
}

*/
	/*	
		Node n1  = (Node) node.get();
		Literal result = new Literal(n1.name);
		arg.push(result);
		//this.visitNode(node, arg);

		
		for (Dag<?>.DagNode j : node.getChildren()) {
			Node n0  = (Node) j.get();
			//if (!n0.type.equals("goal") && !n0.type.equals("init")) {
				//out.add(j);
				//unvisited.add(j);
				result = new Literal(n0.name);				
				//result = new OrFormula(l0,l1);
				arg.push(result);
				
				
				this.visitNode(j, arg);
				//System.out.println("RULE "+ l0.toString() + " " );
			//}
		}
		

		
		
	*/	return arg;
	}
	
	
	/*
	 * 
	 * 
	
Node n0  = (Node) node.get();
		arg.add(new Literal(n0.name));
		
		return arg;
	
	
	* */


}