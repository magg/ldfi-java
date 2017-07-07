package com.miggonza.ldfi;

import java.io.IOException;

/**
 * Boolean CNF literal.
 */
public class Literal {

	private final Variable variable;

	private final boolean sense;

	public Literal(Variable variable, boolean sense) {
		if (variable == null) throw new NullPointerException();
		this.variable = variable;
		this.sense = sense;
	}

	public Literal(String name, int number, boolean sense) {
		this(new Variable(name, number), sense);
	}

	public Variable getVariable() {
		return variable;
	}

	public String getName() {
		return variable.getName();
	}

	public int getNumber() {
		return variable.getNumber();
	}

	public Boolean getValue() {
		return variable.getValue();
	}

	public void setValue(Boolean value) {
		this.variable.setValue(value);
	}

	public boolean getSense() {
		return sense;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Literal)) return false;

		final Literal literal = (Literal) o;

		if (sense != literal.sense) return false;
		if (!variable.equals(literal.variable)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = variable.hashCode();
		result = 31 * result + (sense ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s%s[%d]", sense ? "" : "-", variable.getName(), variable.getNumber());
	}

	public Appendable print(Appendable writer) throws IOException {
		if (!sense) writer.append('-');
		return variable.print(writer);
	}
}