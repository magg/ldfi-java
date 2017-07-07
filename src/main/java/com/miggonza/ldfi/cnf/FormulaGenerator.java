package com.miggonza.ldfi.cnf;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FormulaGenerator {
	Random rand;
	int cardinality;

	public FormulaGenerator(int cardinality, int seed) {
		super();
		this.rand = new Random(seed);
		this.cardinality = cardinality;
	}
	
	
	public BooleanFormula formula(int depth){
		if (depth == 1){
			int randomNum = rand.nextInt((this.cardinality - 0) + 1) + 0;
			String stri = "I" + randomNum;
			return new Literal(stri);
		} else {
			BooleanFormula l = this.formula(depth -1);
			//int randomNum = ThreadLocalRandom.current().nextInt(1, depth - 1 + 1);

			int randomNum = rand.nextInt((depth - 1 - 1) + 1) + 1;
			BooleanFormula s = this.formula( randomNum) ;
			BooleanFormula left;
			BooleanFormula right;
			
			if (rand.nextInt((1 - 0) + 1) + 0 == 1){
				left = l;
				right = s;

			} else {
				right = l;
				left = s;
			}
			
			if (rand.nextInt((1 - 0) + 1) + 0  == 1){
				return new AndFormula(left, right);
			} else{
				return new OrFormula(left, right);
			}
			
			
			
		}
		
	}
	
	
}
