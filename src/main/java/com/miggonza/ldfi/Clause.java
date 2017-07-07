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
 * Boolean CNF clause.
 */
public class Clause {

	private final List<Literal> literals;

	private final Set<Variable> variables;

	public Clause(List<Literal> literals) {
		if (literals == null) throw new NullPointerException();
		final Set<Variable> variables = new HashSet<Variable>(literals.size());
		for (Literal literal : literals) {
			variables.add(literal.getVariable());
		}
		this.literals = Collections.unmodifiableList(new ArrayList<Literal>(literals));
		this.variables = Collections.unmodifiableSet(variables);
	}

	public Clause(Literal... literals) {
		this(Arrays.asList(literals));
	}

	public List<Literal> getLiterals() {
		return literals;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public boolean contains(Variable variable) {
		return variables.contains(variable);
	}

	public int size() {
		return literals.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Clause)) return false;

		final Clause clause = (Clause) o;
		return literals.equals(clause.literals);
	}

	@Override
	public int hashCode() {
		return literals.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final Iterator<Literal> iterator = literals.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next().toString());
			if (iterator.hasNext()) sb.append(' ');
		}
		return sb.toString();
	}

	public Appendable print(Appendable writer) throws IOException {
		for (Literal literal : literals) {
			literal.print(writer).append(' ');
		}
		return writer.append('0');
	}
}