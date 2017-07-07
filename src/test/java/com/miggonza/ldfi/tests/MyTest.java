package com.miggonza.ldfi.tests;


import org.junit.*;
import static org.junit.Assert.*;

import java.util.Set;

import com.miggonza.ldfi.cnf.*;

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
	
}