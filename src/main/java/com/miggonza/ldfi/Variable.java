package com.miggonza.ldfi;

import java.io.IOException;

/**
 * Boolean variable.
 */
public class Variable {

	private final String name;

	private final int number;

	private Boolean value;

	public Variable(String name, int number) {
		if (name == null) throw new NullPointerException();
		this.number = number;
		this.name = name;
		value = null;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Variable)) return false;

		final Variable variable = (Variable) o;

		if (number != variable.number) return false;
		if (!name.equals(variable.name)) return false;
		if (value != null ? !value.equals(variable.value) : variable.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + number;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s[%d]=%s", name, number, (value == null) ? "?" : (value ? "T" : "F"));
	}

	public Appendable print(Appendable writer) throws IOException {
		return writer.append(String.valueOf(number));
	}
}