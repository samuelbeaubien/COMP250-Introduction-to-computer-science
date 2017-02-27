package a4q2;

import java.util.LinkedList;
import java.util.Set;
import java.util.*;

public class ShapeGraph extends Graph<Shape> {	
	
	// Constructor
	public ShapeGraph() {
	}
 	
	// Reset visited 
	public void resetVisited() {
		Set<String>  vertexKeySet =  vertexMap.keySet(); 
		for( String key : vertexKeySet ){
			vertexMap.get(key).visited = false;
		}				
	}
	
	
	/**
	 * Returns a list of lists, each inner list is a path to a node that can be reached from a given node
	 * if the total area along the path to that node is greater than the threshold.
	 * Your solution must be a recursive, depth first implementation for a graph traversal.
	 * The Strings in the returned list of lists should be the vertex labels (keys).
	 */
	
	public LinkedList<LinkedList<String>> traverseFrom(String key, float threshold)
	{
		LinkedList<LinkedList<String>> masterList = new LinkedList<LinkedList<String>>();
		
		// TODO traverseFrom
		//   ADD YOUR CODE HERE.  (IF YOU WISH TO ADD A HELPER METHOD, THEN ADD IT AFTER THIS METHOD.)
		
		// Get the initial vertex
		
		Vertex<Shape> currentVertex = vertexMap.get(key);
		
		// Recursive method 
		
		
		float initialTotalArea = 0;
		
		LinkedList<String> initialReceivedList = new LinkedList<String>();
		
		graphRecursion(currentVertex, initialReceivedList, initialTotalArea, masterList, threshold);
		
		// Reset visited nodes 
		
		
		return masterList;
		
	}	
	
	/*
	 * This method get all possible path from a starting Vertex and put the path that have a higher totalArea in the masterList
	 * The method receives a Vertex, a list of string, the total area associated with this list of strings, the master list, and the threshold
	 * This is a recursive method
	 */
	
	public static void graphRecursion(Vertex<Shape> currentVertex, LinkedList<String> receivedList, float receivedTotalArea, LinkedList<LinkedList<String>> masterList, float threshold)
	{
		// Quit if the vertex is null (shouldn't happen in theory)
		if (currentVertex == null)
		{
			System.exit(0);
		}
		
		// Quit if vertex have already been visited
		if (currentVertex.visited == true)
		{
			System.exit(0);
		}
		
		// Initialize sentList : list that will be given to next generation
		LinkedList<String> sentList = new LinkedList<String>();
		sentList = (LinkedList<String>) receivedList.clone();
		
		// Initialize sentTotalArea
		float sentTotalArea = receivedTotalArea;
		
		sentList.add(currentVertex.key);	// Add the key of currentVertex to sentList
		
		sentTotalArea = sentTotalArea + currentVertex.element.getArea();	// Add area to sentTotalArea
	
		// If sentTotalArea if greater than threshold, put sentTotal Area in masterList
		if (sentTotalArea > threshold)
		{
			LinkedList<String> tempList = new LinkedList<String>();
			tempList = (LinkedList<String>) sentList.clone();
			masterList.add(tempList);
		}
		
		int size_adjList = currentVertex.adjList.size(); // Get adjList size
		
		// For all LinkedList<Edge> adjList's edges, use graphRecursion on Edge.getEndVertex()
		for (int counter_adjList = 0; counter_adjList < size_adjList; counter_adjList++)
		{
			Vertex<Shape> nextVertex = currentVertex.adjList.get(counter_adjList).getEndVertex();
			graphRecursion(nextVertex, sentList, sentTotalArea, masterList, threshold);
		}
	
	}
	
	
	
	
}








