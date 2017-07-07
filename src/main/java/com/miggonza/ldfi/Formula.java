package com.miggonza.ldfi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Boolean CNF formula.
 */
public class Formula {

	private final List<Clause> clauses;

	private final Set<Variable> variables;

	public Formula(List<Clause> clauses) {
		if (clauses == null) throw new NullPointerException();
		final Set<Variable> variables = new HashSet<Variable>();
		for (Clause clause : clauses) {
			variables.addAll(clause.getVariables());
		}
		this.clauses = Collections.unmodifiableList(new ArrayList<Clause>(clauses));
		this.variables = Collections.unmodifiableSet(variables);
	}

	public Formula(Clause... clauses) {
		this(Arrays.asList(clauses));
	}

	public List<Clause> getClauses() {
		return clauses;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public boolean contains(Variable variable) {
		return variables.contains(variable);
	}

	public int size() {
		return clauses.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Formula)) return false;

		final Formula formula = (Formula) o;
		return clauses.equals(formula.clauses);
	}

	@Override
	public int hashCode() {
		return clauses.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final Iterator<Clause> iterator = clauses.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next().toString());
			if (iterator.hasNext()) sb.append('\n');
		}
		return sb.toString();
	}

	public Appendable print(Appendable writer) throws IOException {
		writer.append(String.format("p cnf %d %d\n", variables.size(), clauses.size()));
		for (Clause clause : clauses) {
			clause.print(writer).append('\n');
		}
		return writer;
	}
}