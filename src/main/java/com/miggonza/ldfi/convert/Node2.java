package com.miggonza.ldfi.convert;

import java.util.*;
 
// This class represents a directed graph using adjacency
// list representation
public class Node2
{
    private int v;   // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List
 
    //Constructor
    public Node2(int v)
    {
        this.v = v;
        
        adj = new LinkedList[this.v];
        for (int i=0; i<this.v; i++)
            adj[i] = new LinkedList<>();
    }
 
    // Function to add an edge into the graph
    public void addEdge(int v,int w) { adj[v].add(w); }
 
    
    
    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean visited[],
                             Stack<Integer> stack)
    {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;
 
        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
 
        // Push current vertex to stack which stores result
        stack.push(new Integer(v));
    }
 
    // The function to do Topological Sort. It uses
    // recursive topologicalSortUtil()
    public void topologicalSort()
    {
        Stack<Integer> stack = new Stack<Integer>();
 
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[this.v];
        for (int i = 0; i < this.v; i++)
            visited[i] = false;
 
        // Call the recursive helper function to store
        // Topological Sort starting from all vertices
        // one by one
        for (int i = 0; i < this.v; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
 
        // Print contents of stack
        while (stack.empty()==false)
            System.out.print(stack.pop() + " ");
        
        System.out.println();
    }
    
}