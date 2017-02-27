package a4q1;

import java.util.ArrayList;
import java.util.LinkedList;

public class Test {

	
	public static void main(String[] args)
	{
	
		/*
		MyHashTable<Integer,String> thyTable = new MyHashTable<Integer,String>(2);
		
		
		thyTable.put(1, "a");
		thyTable.put(1, "b");
		
		System.out.println(thyTable.get(1));
		
		System.out.println(thyTable.remove(1));
		
		System.out.println(thyTable.get(1));
		
		System.out.println(thyTable.isEmpty());
		
		thyTable.put(3, "c");
		thyTable.put(4, "d");
		
		ArrayList<Integer> listKeys = thyTable.keys();
		
		ArrayList<String> listValues = thyTable.values();
		
		System.out.println(thyTable.isEmpty());
		
	*/
		
		LinkedList<String> hello = new LinkedList<String>();
		
		hello.add("a");
		hello.add("b");
		hello.add("c");
		
		LinkedList<String> bonjour = new LinkedList<String>();
		
		bonjour = (LinkedList<String>) hello.clone();
		
		System.out.println(bonjour);
	}
	
	
	
}
