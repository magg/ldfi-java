package com.miggonza.ldfi.convert;

public interface GraphVisitor<A, R> {
	/**
	 * Visit a proposition symbol (e.g A).
	 * 
	 * @param sentence
	 *            a Sentence that is a propositional symbol.
	 * @param arg
	 *            optional argument to be used by the visitor.
	 * @return optional return value to be used by the visitor.
	 */
	R visitNode(Dag<?>.DagNode node, A arg);
	//R visitEmpty();

}
