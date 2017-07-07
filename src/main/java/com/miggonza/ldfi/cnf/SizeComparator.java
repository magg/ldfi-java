package com.miggonza.ldfi.cnf;

import java.util.Comparator;
import java.util.Set;

public class SizeComparator<T extends Comparable<T>> implements Comparator<Set<T>> { 


	@Override
	public int compare(Set<T> o1, Set<T> o2) {
		// TODO Auto-generated method stub
	    return Integer.compare(o1.size(), o2.size());
	}

        
    
}
