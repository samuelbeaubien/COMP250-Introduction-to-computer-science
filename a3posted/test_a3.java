package a3posted;

//import java.util.ArrayList;

public class test_a3 {

	public static void main(String[] args)
	{

		Trie theTrieTree = new Trie();
		
		theTrieTree.insert("abba");
		
		theTrieTree.insert("acba");
		
		theTrieTree.insert("abta");
		
		theTrieTree.insert("A bin la calis");
		
		theTrieTree.insert("hell");
		
		theTrieTree.insert("abracadabra");
		
		//theTrieTree.insert("bcba");
		
		//System.out.println("abba" + "=" + theTrieTree.contains("abba"));
	
		
		System.out.println(theTrieTree.getAllPrefixMatches("A"));
		
		
		
		
		
		
		
		

	}
	
}
