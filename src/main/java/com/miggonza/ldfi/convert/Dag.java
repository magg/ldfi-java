package com.miggonza.ldfi.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.miggonza.ldfi.convert.Dag.DagNode;


/**
 * Simple representation of a directed acyclic graph, used to track the
 * dependencies between messages.
 */
public class Dag<T> {
	/** Map from a descriptor to the DagNode containing it. */
	private final HashMap<T, DagNode> nodeMap = new HashMap<T, DagNode>();

	/** Represent the edges: Multimap from a node to the set of its parents. */
	private final Multimap<DagNode, DagNode> parents = HashMultimap.create();
	/** Represent the edges: Multimap from a node to the set of its children. */
	private final Multimap<DagNode, DagNode> children = HashMultimap.create();

	/**
	 * A node in the Dag, which stores some opaque object and has parents and
	 * children.
	 */
	public class DagNode {
		/** Object stored in the node. */
		T object;

		/**
		 * Create a dagnode around an object. Two DagNodes created with the same
		 * object are considered distinct.
		 */
		public DagNode(T object) {
			this.object = object;
		}

		/** @return the stored object. */
		public T get() {
			return object;
		}

		/** @return the parents of a node. */
		public Collection<DagNode> getParents() {
			return Collections.unmodifiableCollection(parents.get(this));
		}

		/** @return the children of a node. */
		public Collection<DagNode> getChildren() {
			return Collections.unmodifiableCollection(children.get(this));
		}

		/**
		 * Remove this node from the DAG, including edges to parents and
		 * children.
		 */
		void remove() {
			Collection<DagNode> thechildren = new ArrayList<DagNode>(
					getChildren());
			Collection<DagNode> theparents = new ArrayList<DagNode>(
					getParents());
			for (DagNode i : thechildren)
				removeEdge(this, i);
			for (DagNode i : theparents)
				removeEdge(i, this);
			nodeMap.remove(object);
		}

		/** @return one of the parents of the node. */
		DagNode getAParent() {
			Collection<DagNode> tmp = getParents();
			if (tmp.isEmpty())
				return null;
			else
				return tmp.iterator().next();
		}
		
		DagNode getAChild() {
			Collection<DagNode> tmp = getChildren();
			if (tmp.isEmpty())
				return null;
			else
				return tmp.iterator().next();
		}

		@Override
		public String toString() {
			return object.toString();
		}
		
		
		public <A, R> R accept(GraphVisitor<A, R> plv, A arg) {
			R result = null;
			
			
			result = plv.visitNode( (DagNode)this, arg);
			/*
			if (isPropositionSymbol()) {
				result = plv.visitPropositionSymbol((PropositionSymbol) this, arg);
			} else if (isUnarySentence()) {
				result = plv.visitUnarySentence((ComplexSentence) this, arg);
			} else if (isBinarySentence()) {
				result = plv.visitBinarySentence((ComplexSentence) this, arg);
			}
*/
			return result;
	}
		

	}

	/**
	 * Represent a path in the DAG from a node to its parent until reaching the
	 * root
	 */
	public class Path {
		/** The sequence of nodes in the path. */
		ArrayList<DagNode> path = new ArrayList<DagNode>();

		/** The node in the path corresponding to the root */
		public DagNode root() {
			return path.get(path.size() - 1);
		}
		
		public DagNode sink() {
			return path.get(path.size() - 1);
		}

		/** Remove the root node in the path. */
		void removeLast() {
			path.remove(path.size() - 1);
		}

		/**
		 * Extend this (incomplete) path continuing it until we reach the root.
		 * A path in a DAG can be extended even if the DAG underneath it
		 * changes, as long as every node in the path is still in the DAG.
		 */
		void extend() {
			// System.out.println("Extending FROM "+this);
			if (path.size() == 0)
				throw new Error("Cannot extend empty path?");
			DagNode node = root(), parent;
			while ((parent = node.getAParent()) != null) {
				node = parent;
				path.add(node);
			}
			// System.out.println("Extending TO "+this);
		}

		
		void compress() {
			// System.out.println("Extending FROM "+this);
			if (path.size() == 0)
				throw new Error("Cannot extend empty path?");
			DagNode node = root(), sink;
			while ((sink = node.getAChild()) != null) {
				node = sink;
				path.add(node);
			}
			// System.out.println("Extending TO "+this);
		}
		
		/**
		 * If this path is bad, remove the last node on it, and extend it and
		 * try extending it again, until we get to another root.
		 */
		public void next() {
			removeLast();
			extend();
		}

		@Override
		public String toString() {
			return "{{ " + Joiner.on(",").join(path) + " }}";
		}

	}

	/** Make or get the node corresponding to some key. */
	public DagNode makeOrGet(T key) {
		if (nodeMap.containsKey(key))
			return nodeMap.get(key);
		DagNode out = new DagNode(key);
		nodeMap.put(key, out);
		return out;
	}

	/** Get a path from the given node to the root. */
	public Path rootPath(DagNode node) {
		Path out = new Path();
		out.path.add(node);
		out.extend();
		return out;
	}
	
	public Path sinkPath(DagNode node) {
		Path out = new Path();
		out.path.add(node);
		out.compress();
		return out;
	}

	/** Add an edge to the dag from a parent to a child. */
	public void addEdge(DagNode parent, DagNode child) {
	 //System.out.format("Adding dag edge for %s -> %s\n",parent.get().toString(),child.get().toString());
		if (children.get(parent).contains(child))
			return;
		children.put(parent, child);
		parents.put(child, parent);
	}

	/** Remove an edge in the dag from a parent to a child. */
	public void removeEdge(DagNode parent, DagNode child) {
		if (!children.get(parent).contains(child))
			throw new Error("Problem, removing non-existant edge!!");
		children.remove(parent, child);
		parents.remove(child, parent);
	}

	/** All descendants of a node, including itself. */
	public Collection<DagNode> getAllChildren(DagNode node) {
		HashSet<DagNode> todo = new HashSet<DagNode>();
		HashSet<DagNode> out = new HashSet<DagNode>();
		todo.add(node);
		out.add(node);

		while (todo.size() > 0) {
			HashSet<DagNode> unvisited = new HashSet<DagNode>();
			for (DagNode i : todo) {
				for (DagNode j : i.getChildren()) {
					if (!out.contains(j)) {
						out.add(j);
						unvisited.add(j);
					}
				}
			}
			todo = unvisited;
		}
		return out;
	}
	
	
	
	/**
	 * Returns the sources of the receiver. A source is a vertex with no incoming edges. The
	 * returned set's iterator traverses the nodes in the order they were added to the graph.
	 * 
	 * @return the sources of the receiver
	 */
	public Set<DagNode> getSources() {
		return computeZeroEdgeVertices(parents);
	}

	/**
	 * Returns the sinks of the receiver. A sink is a vertex with no outgoing edges. The returned
	 * set's iterator traverses the nodes in the order they were added to the graph.
	 * 
	 * @return the sinks of the receiver
	 */
	public Set<DagNode> getSinks() {
		return computeZeroEdgeVertices(children);
	}

	private Set<DagNode> computeZeroEdgeVertices(Multimap<DagNode, DagNode> map) {
		Set<DagNode> candidates= map.keySet();
		Set<DagNode> roots= new LinkedHashSet<DagNode>(candidates.size());
		for (Iterator<Dag<T>.DagNode> it= candidates.iterator(); it.hasNext();) {
			DagNode candidate= it.next();
			//System.out.println(candidate.toString());
			//System.out.println(map.get(candidate).toString());

			if (map.get(candidate).isEmpty())
				roots.add(candidate);
		
		}
		return roots;
	}
	
	
	public Stack<DagNode> connectionPath;
	public List<Stack<DagNode>> connectionPaths;
	// Push to connectionsPath the object that would be passed as the parameter 'node' into the method below
	void findAllPaths(DagNode node, DagNode targetNode) {
	    for (DagNode nextNode : node.getChildren()) {
	       if (nextNode.equals(targetNode)) {
	    	   Stack<DagNode> temp = new Stack<DagNode>();
	           for (DagNode node1 : connectionPath)
	               temp.add(node1);
	           connectionPaths.add(temp);
	       } else if (!connectionPath.contains(nextNode)) {
	           connectionPath.push(nextNode);
	           findAllPaths(nextNode, targetNode);
	           connectionPath.pop();
	        }
	    }
	}
	
	public List<Stack<DagNode>> getPaths(DagNode node, DagNode targetNode){
		connectionPath = new Stack<DagNode>();
		connectionPaths = new ArrayList<Stack<DagNode>>();
		findAllPaths(node, targetNode);
		return connectionPaths;
	}
	
	
	
	
}