package a3posted;

//  COMP 250 - Introduction to Computer Science - Fall 2016
//  Assignment #3 - Question 1

import java.util.*;

/*
 *  Trie class.  Each node is associated with a prefix of some key 
 *  stored in the trie.   (Any string is a prefix of itself.)
 */

public class Trie
{
	private TrieNode root;

	// Empty trie has just a root node.  All the children are null.

	public Trie() 
	{
		root = new TrieNode();
	}

	public TrieNode getRoot(){
		return root;
	}

	
	/*
	 * Insert key into the trie.  First, find the longest 
	 * prefix of a key that is already in the trie (use getPrefixNode() below). 
	 * Then, add TrieNode(s) such that the key is inserted 
	 * according to the specification in PDF.
	 */
	public void insert(String key)
	{
		//  ADD YOUR CODE BELOW HERE

		TrieNode currentNode = this.getPrefixNode(key);
		int index = currentNode.depth;
		
		while (index < key.length())
		{
			char currentChar = key.charAt(index);
			currentNode = currentNode.createChild(currentChar);
			index++;
		}
		
		currentNode.endOfKey = true;
		
		//  ADD YOUR CODE ABOVE HERE
	}

	// insert each key in the list (keys)

	public void loadKeys(ArrayList<String> keys)
	{
		for (int i = 0; i < keys.size(); i++)
		{
			insert(keys.get(i));
		}
		return;
	}

	/*
	 * Given an input key, return the TrieNode corresponding the longest prefix that is found. 
	 * If no prefix is found, return the root. 
	 * In the example in the PDF, running getPrefixNode("any") should return the
	 * dashed node under "n", since "an" is the longest prefix of "any" in the trie. 
	 */
	
	public TrieNode getPrefixNode(String key)
	{
		//   ADD YOUR CODE BELOW HERE
		
		// -- This method uses a while loop. At each iteration, it checks for a certain 
		//     character. If it can't find it, (biggest prefix possible) it stops --
		
		// Initialize counter for loop
		int index = 0;
		
		TrieNode currentNode = root; 
		
		char currentChar = key.charAt(index);
		
		// While loop
		while (index < key.length())
		{
			currentChar = key.charAt(index);
			
			if (currentNode.children[(int)currentChar] == null)
			{
				return currentNode;
			}
			else 
			{
				index++; 
				currentNode = currentNode.getChild(currentChar);
			}

		}
		
		
		// Returns the current node. If it found nothing, this will be the root
		return currentNode;
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TrieNode.
	 */

	public String getPrefix(String key)
	{
		return getPrefixNode(key).toString();
	}

	
	/*
	 *  Return true if key is contained in the trie (i.e. it was added by insert), false otherwise.
	 *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
	 */
	public boolean contains(String key)
	{  
		//   ADD YOUR CODE BELOW HERE

		TrieNode currentNode = this.getPrefixNode(key);
		
		return currentNode.endOfKey;
		
		//   ADD YOUR CODE ABOVE HERE
	}

	/*
	 *  Return a list of all keys in the Trie that have the given prefix. 
	 *  
	 *  First, the method uses the getPrefixMatches to get to the the farthest node of 
	 *  
	 *  This method uses the helper getKeys, described at the end of the TrieNode class.
	 *  The getKeys method takes a TrieNode and returns all the keys existing in the children
	 *  of the TrieNode. 
	 */
	public ArrayList<String> getAllPrefixMatches( String prefix )
	{
		//  ADD YOUR CODE BELOW 
		ArrayList<String> stringList = new ArrayList<String>();
		
		TrieNode currentNode = this.getPrefixNode(prefix);
		
		if (currentNode == root)
		{
			return stringList;
		}
		
		// Verifies if the prefix exists in the Trie
		
		String firstString = currentNode.toString();
		
		if (firstString.length() != prefix.length())
		{
			return stringList;
		}
		
		for (int i = 0; i < prefix.length(); i++)
		{
			if (firstString.charAt(i) != prefix.charAt(i))
			{
				return stringList;
			}
		}
		
		// Call to the recursive method. Returns an ArrayList of Strings
		
		stringList = currentNode.getKeys();
		
		//  ADD YOUR CODE ABOVE HERE

		return stringList;
	}
	
	

	
	/*
	 *  A node in a Trie (prefix) tree.  
	 *  It contains an array of children: one for each possible character.
	 *  The ith child of a node corresponds to character (char)i
	 *  which is the UNICODE (and ASCII) value of i. 
	 *  Similarly the index of character c is (int)c.
	 *  So children[97] = children[ (int) 'a']  would contain the child for 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97.
	 * 
	 * References:
	 * -For all unicode charactors, see http://unicode.org/charts
	 *  in particular, the ascii characters are listed at http://unicode.org/charts/PDF/U0000.pdf
	 * -For ascii table, see http://www.asciitable.com/
	 * -For basic idea of converting (casting) from one type to another, see 
	 *  any intro to Java book (index "primitive type conversions"), or google
	 *  that phrase.   We will cover casting of reference types when get to the
	 *  Object Oriented Design part of this course.
	 */

	// TODO put back to private and non static
	
	public static class TrieNode
	{
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TrieNode.NUMCHILDREN"
		 */

		public static final int NUMCHILDREN = 256;

		private TrieNode   parent;
		private TrieNode[] children;
		private int        depth;            // 0 for root, 1 for root's children, 2 for their children, etc..
		private char       charInParent;    // Character associated with edge between this node and its parent.
		        			        		// See comment above for relationship between an index in 0 to 255 and a char value.
		private boolean endOfKey;   // Set to true if prefix associated with this node is also a key.

		// Constructor for new, empty node with NUMCHILDREN children.  All the children are null. 
		
		public TrieNode()
		{
			children = new TrieNode[NUMCHILDREN];
			endOfKey = false;
			depth = 0; 
			charInParent = (char)0; 
		}

		
		/*
		 *  Add a child to current node.  The child is associated with the character specified by
		 *  the method parameter.  Make sure you set all fields in the child node.
		 *  
		 *  To implement this method, see the comment above the inner class TrieNode declaration.  
		 */

		public TrieNode createChild(char  c) 
		{	   
			TrieNode child       = new TrieNode();

			// ADD YOUR CODE BELOW HERE
			
			// -- Modifications for the child --
			
			// children doesn't have to be modified
			// endOfKey does not have to be modified
			
			// depth modification : 
			child.depth = this.depth + 1;
			
			// parent modification 
			child.parent = this; 
			
			// charInParent modification
			child.charInParent = c; 
			
			
			// -- Modification for the Parent --
			
			// Only the children[] have to be changed
			
			this.children[(int)c] = child;
			
			
			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is "on" 
		// the edge from this node to the child.  The child could be null.  

		public TrieNode getChild(char c) 
		{
			return children[ c ];
		}

		// Test whether the path from the root to this node is a key in the trie.  
		// Return true if it is, false if it is prefix but not a key.

		public boolean isEndOfKey() 
		{
			return endOfKey;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfKey(boolean endOfKey)
		{
			this.endOfKey = endOfKey;
		}

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
         *  is defined by descending from the root to this node.  However, you will
         *  find it is easier to implement by ascending from the node to the root,
         *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */

		public String toString()
		{
			// ADD YOUR CODE BELOW HERE
			
			// Here, I use recursion in order to get all the chars
			// The base case is when this.parent == null , so when one step below
			// the root. 
			
			String prefix = ""; 
			
			if (this.parent != null)
			{
				TrieNode parent = this.parent;
				prefix = parent.toString();
				prefix = prefix + this.charInParent;
			}

			return prefix;
			// ADD YOUR CODE ABOVE HERE
		}
		

		/* This method receives a TrieNode (this) and uses recursion to get to visit each node
		 * under it. If one given node is a end of key (isEndOfKey == true) its key will 
		 * be added to the keyList arrayList. 
		 */
		public ArrayList<String> getKeys()
		{
			
			ArrayList<String> keyList = new ArrayList<String>(); 	// Creates the arraylist
			
			// If the current node is an endOfKey, its key is added to the arraylist
			if (this.endOfKey == true)	
			{
				keyList.add(this.toString());
			}
			
			
			// The for loop goes over all the element of the children TrieNode[]
			// If a child is null, the loops ends 
			// If a child is a TrieNode, we apply the getChild method
			for (int index = 0; index < 256; index++ )
			{
				
				TrieNode currentChild = this.getChild((char)index);
				
				if (currentChild == null)
				{
				}
				
				else 
				{
					keyList.addAll(currentChild.getKeys());
				}
				
			}
		
			
			return keyList; 
		}
		
		
	}


}
