package com.miggonza.ldfi.tests;


import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.miggonza.ldfi.convert.Node;
import com.miggonza.ldfi.convert.NodeVisitor;
import com.miggonza.ldfi.cnf.*;
import com.miggonza.ldfi.convert.Dag;
import com.miggonza.ldfi.convert.Dag.DagNode;
import com.miggonza.ldfi.convert.Dag.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTest
{
    private Logger log;

	public BooleanFormula basic(){	
		return new OrFormula(new AndFormula(new Literal("a"), new Literal("b")), new AndFormula(new Literal("c"), new Literal("d")));
		
	}
	
	@Before	
	public void init(){
		log = LoggerFactory.getLogger(this.getClass());
	}
	
	
	
	@Test
	public void testSize(){
		FormulaGenerator fg = new FormulaGenerator(10, 8);
		BooleanFormula fst = fg.formula(10);
		BooleanFormula snd = fg.formula(20);

		
		assertTrue(snd.clauses() > fst.clauses());
		assertTrue(snd.depth() == 20);
        assertTrue(fst.depth() == 10);
        assertTrue(snd.variables().size() == 11);
	}
	
	@Test
	public void testSimpleConvert(){
		BooleanFormula base = basic();
		CNFFormula cnf = new CNFFormula(base);
		log.info(cnf.formula.toString());
		assertTrue(cnf.formula.isCNF());
		
	}
	
	
	@Test
	public void testConvert(){
		
		FormulaGenerator fg = new FormulaGenerator(10, 8);
		BooleanFormula fst = fg.formula(7);
		CNFFormula c = new CNFFormula(fst);
		BooleanFormula cnf = c.formula;
		assertTrue(cnf.variables().equals(fst.variables()));
		assertTrue(fst.isCNF() == false);
		assertTrue(cnf.isCNF());
		
	}
	
	
	@Test
	public void testSolve(){
		FormulaGenerator fg = new FormulaGenerator(50, 8);
		BooleanFormula fst = fg.formula(4);
		CNFFormula c = new CNFFormula(fst);
		SAT4JSolver s = new SAT4JSolver(c);
		
		for( Set<Object> soln: s.solve()){
			log.info(soln.toString());
		}
		
		//for(E e: fst.conjuncts()){
		//log.info(fst.conjuncts().toString());

		//}
		//log.info(s.satformula.toString());
	
		//for(Object e: c.conjuncts()){
		//	log.info(e.toString());

		//}
		
	}
	
	@Test
	public void testBasicSolve(){
		BooleanFormula base = basic();
		CNFFormula cnf = new CNFFormula(base);
		SAT4JSolver s = new SAT4JSolver(cnf);
		
		//log.info(s.satformula.toString());
		/*
		for(Object e: cnf.conjuncts()){
			log.info(e.toString());

		}
		*/
		for( Set<Object> soln: s.solve()){
			log.info(soln.toString());
		}
		
		
	}
	
	@Test
	public void testMakeOne() {
		Dag<Integer> dag = new Dag<Integer>();
		Dag<Integer>.DagNode n1 = dag.makeOrGet(1);
		Dag<Integer>.DagNode n2 = dag.makeOrGet(2);
		Dag<Integer>.DagNode n3 = dag.makeOrGet(3);
		Dag<Integer>.DagNode n4 = dag.makeOrGet(4);

		dag.addEdge(n1,n2);
		assertTrue(n1.getChildren().contains(n2));
		assertTrue(n2.getParents().contains(n1));

		dag.addEdge(n2,n3);
		assertTrue(n2.getChildren().contains(n3));
		assertTrue(n3.getParents().contains(n2));
		
	}
	
	
	@Test
	public void testDag(){
		Dag<Node> dag = new Dag<Node>();

		Node n1 = new Node("Client1", "init");
		Node n2 = new Node("Client2", "init");
		Node n3 = new Node("Bcast1", "step");
		Node n4 = new Node("Bcast2", "step");
		Node n5 = new Node("RepA", "step");
		Node n6 = new Node("RepB", "step");
		Node n7 = new Node("stableWrite", "goal");

		
		Dag<Node>.DagNode m1 = dag.makeOrGet(n1);
		Dag<Node>.DagNode m2 = dag.makeOrGet(n2);
		Dag<Node>.DagNode m3 = dag.makeOrGet(n3);
		Dag<Node>.DagNode m4 = dag.makeOrGet(n4);
		Dag<Node>.DagNode m5 = dag.makeOrGet(n5);
		Dag<Node>.DagNode m6 = dag.makeOrGet(n6);
		Dag<Node>.DagNode m7 = dag.makeOrGet(n7);

		dag.addEdge(m1,m3);
		dag.addEdge(m2,m4);
		dag.addEdge(m3,m5);
		dag.addEdge(m3,m6);
		
		dag.addEdge(m4,m5);
		dag.addEdge(m4,m6);
		


		dag.addEdge(m5,m7);
		dag.addEdge(m6,m7);
		
		//Dag<Node>.Path rootPath = dag.rootPath(m2);
//log.info(rootPath.toString());
//log.info(rootPath.root().toString());

//Dag<Node>.Path sinkPath = dag.sinkPath(m2);
//log.info(sinkPath.toString());

//log.info(sinkPath.sink().toString());


////log.info("SOURCES");

for (Dag<Node>.DagNode  s : dag.getSources()){
		//log.info(s.toString());
}



//log.info("SINKS");

for (Dag<Node>.DagNode  s : m7.getChildren()){
	log.info(s.toString());
}

List<OrFormula> afmla = new ArrayList<OrFormula>();

for (Stack<Dag<Node>.DagNode> m: dag.getPaths(m1,m7)){
	//log.info("new stack");
	
	List<Literal> ll = new ArrayList<Literal>();
	for ( Dag<Node>.DagNode n : m){
		//log.info(n.toString());
		Literal l = new Literal(n.get().name);
		ll.add(l);
	}
	
	afmla.add(new OrFormula(ll.get(0), ll.get(1)));
	
	
	
}

for (Stack<Dag<Node>.DagNode> m: dag.getPaths(m2,m7)){
	List<Literal> ll = new ArrayList<Literal>();
	//log.info("new stack2");
	for ( Dag<Node>.DagNode n : m){
		//log.info(n.toString());
		Literal l = new Literal(n.get().name);
		ll.add(l);
	}
	
	afmla.add(new OrFormula(ll.get(0), ll.get(1)));

	
}


List<AndFormula> fmla = new ArrayList<AndFormula>();


for(int i= 0; i<afmla.size();i+=2){
	AndFormula of = new AndFormula(afmla.get(i), afmla.get(i+1));
	fmla.add(of);
	//log.info(of.toString());
	
}

AndFormula aaf = new AndFormula(fmla.get(0), fmla.get(1));
//llog.info(aaf.toString());
CNFFormula cnf = new CNFFormula(aaf);
//log.info(cnf.formula.toString());
assertTrue(cnf.formula.isCNF());

SAT4JSolver s = new SAT4JSolver(cnf);

//log.info(s.satformula.toString());
/*
for(Object e: cnf.conjuncts()){
	log.info(e.toString());

}
*/
for( Set<Object> soln: s.solve()){
	log.info(soln.toString());
}

	}	
}