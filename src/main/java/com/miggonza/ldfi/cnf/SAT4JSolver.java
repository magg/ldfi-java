package com.miggonza.ldfi.cnf;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.tools.ModelIterator;

public class SAT4JSolver {
	
	public SATVars vars;
	
	public IVecInt satformula;
	public List<List<Integer>> satarray;
	
	public ISolver solver;

	public SAT4JSolver(CNFFormula cnf) {
		this.vars = new SATVars();

		this.satarray = new ArrayList<List<Integer>>();
		for( Set<Object> clause: cnf.conjuncts()){
			List<Integer> ll  = clause
			.stream()
			.map( x -> this.vars.lookupVar(x))
			.collect(Collectors.toCollection(ArrayList::new));
			satarray.add(ll);
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Set<Object>> solve(){
		
		
		solver = SolverFactory.newLight();
		solver.setTimeout(3600);
		try {
			
			for(List<Integer> list: satarray){
				int [] temp = list.stream().mapToInt(i->i).toArray();
				solver.addExactly(new VecInt(temp), 1);
			}
			
			
			
			
		} catch (ContradictionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ModelIterator modelIterator = new ModelIterator(solver);
		modelIterator.setTimeoutMs(600);
		Set<Set<Object>> ret = new HashSet<Set<Object>>();
		//		Set<Set<Object>> ret = new TreeSet<Set<Object>>( new SizeComparator());

		
	      try {
			while (modelIterator.isSatisfiable()) {
				
				int [] model = modelIterator.model();
				
				Set<Object> newModel = Arrays.stream(model)
			      .boxed()
			      .filter(x -> x > 0)
				  .map( x -> this.vars.lookupNum(x))
				  .collect(Collectors.toSet());
				Set<Object> tmp = Collections.unmodifiableSet(newModel);
				ret.add(tmp);
				
			  }
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      solver.reset();
	      
	      List<Set<Object>> result = new ArrayList<Set<Object>>();
	      result.addAll(ret);
	      
	      //SortedSet<Set<Object>> result = new TreeSet<Set<Object>>( new SizeComparator());
	      //result.addAll(ret);
	      
	      
	      Collections.sort(result, new SizeComparator());
	      return result;
		
	
	
	}
	
	//
	
}
