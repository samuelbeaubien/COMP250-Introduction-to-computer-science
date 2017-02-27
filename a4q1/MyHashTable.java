package a4q1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;
	
	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;
	
	/**
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;
	
	/**
	 *  buckets to store the key-value pairs.   Traditionally an array is used for the buckets
	 *  and a linked allEntries is use for the entries within each bucket.   
	 *  
	 *  We use an ArrayallEntries rather than array, since the former is simpler to use in Java.   
	 */
	 
	ArrayList<LinkedList<HashEntry>>  buckets;
	
	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */
	 
	MyHashTable(int numBuckets_constructor) 
	{
		
		
		//  ADD YOUR CODE HERE 
		
		buckets = new ArrayList<LinkedList<HashEntry>>(); 	//First, initialize a buckets ArrayList<LinkesList<HashEntry>> 
		
		numBuckets = numBuckets_constructor;	// Set numBuckets = numBuckets_constructor
		
		// Initialize all buckets in ArrayList<LinkedList<HashEntry>> buckets 
		// Each iteration initialize once a LinkedList and add it to the buckets list ArraList
		for (int counter_buckets = 0; counter_buckets < numBuckets_constructor; counter_buckets++)
		{
			//LinkedList<HashEntry> currentList = new LinkedList<HashEntry>();
			
			buckets.add(new LinkedList<HashEntry>());
		}
	
	}
	
	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {
		
		return  Math.abs( key.hashCode() ) % numBuckets ;
	}
	
	/**
	 * Checking if the hash table is empty.  
	 */
	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}
	
	/**
	 *   return the number of entries in the hash table.
	 */
	public int size()
	{
		return(entryCount);
	}
	
	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable, then return it.
	 *  Otherwise return null.   
	 */
	
	public  V  put(K key, V value) {
		
		//  ADD YOUR CODE HERE
		
		int bucketPosition = hashFunction(key);	//Get position of new entry
		int sizeBucket = buckets.get(bucketPosition).size();// Get size of the bucket
		V oldValue = null;	// If there is already a key, the old value will be stored here
		
		// Check if key is already in bucket
		// If yes, save value in oldValue and change V for new value
		for(int counter_inBucket = 0; counter_inBucket < sizeBucket; counter_inBucket++)
		{
			HashEntry currentHashEntry = buckets.get(bucketPosition).get(counter_inBucket);	// Get the current entry
			
			// If key is already here, get old value in olValue and change value
			if (key.equals(currentHashEntry.key))
			{
				oldValue = currentHashEntry.value;
				currentHashEntry.value = value;
			}
		
		}
		
		// Add new HashEntry if key wasn't already in bucket. 
		if (oldValue == null)
		{
			HashEntry newHashEntry = new HashEntry(key, value);	// Create new HashEntry
			buckets.get(bucketPosition).add(newHashEntry);	// Add newHashEntry to linked list
			entryCount++;	//Increment entryCount
		}
		
		
		// Check if rehash
		
		double loadFactor = entryCount/numBuckets;	// get loadFactor = num Entries / num bucketrs
		
		if (loadFactor > MAX_LOAD_FACTOR)
		{
			rehash();
		}
		
		return oldValue;  
	}
	
	/**
	 * Retrieves a value associated with some given key in the hash table.
	   Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {
		
		//  ADD YOUR CODE HERE
		
		int tablePosition = hashFunction(key);// Get the position in table
		
		LinkedList<HashEntry> currentBucket = buckets.get(tablePosition);	// Get bucket 
		int size_currentBucket = currentBucket.size();// Get bucket size
		
		// Go through the bucket and check if the key is there
		// If yes, return value
		for (int counter_currentBucket = 0; counter_currentBucket < size_currentBucket; counter_currentBucket++)
		{
			HashEntry currentEntry = currentBucket.get(counter_currentBucket);	// Get current HashEntry
			
			if (key.equals(currentEntry.key))
			{
				return currentEntry.value;
			}
		
		}
		
		return null;
	}
	
	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		
		//  ADD YOUR CODE HERE
		
		int tablePosition = hashFunction(key);// Get the position in table
		
		LinkedList<HashEntry> currentBucket = buckets.get(tablePosition);	// Get bucket 
		int size_currentBucket = currentBucket.size();// Get bucket size
		
		// Go through the bucket and check if the key is there
		// If yes, return value
		for (int counter_currentBucket = 0; counter_currentBucket < size_currentBucket; counter_currentBucket++)
		{
			HashEntry currentEntry = currentBucket.get(counter_currentBucket);	// Get current HashEntry
			
			if (key.equals(currentEntry.key))
			{
				V keyValue = currentEntry.value;	// Get the value of the key
				currentBucket.remove(counter_currentBucket);	// Remove the HashEntry
				entryCount--;
				
				return keyValue;
			}
		
		}
		
		
		
		return(null);
	}
	
	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */
	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */
	
	//@Override
	public MyHashTable<K, V>.HashIterator	iterator(){
		return new HashIterator();
	}
	
	/**
	 * Removes all the entries from the hash table, but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}
	
	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */
	
	public void rehash()
	{ 
		//   ADD YOUR CODE HERE

		int newNumBuckets = 2 * numBuckets;	// Get size new HashTable
		
		
		// --Get all entries of old ArrayList and put them into list oldEntries--
		ArrayList<HashEntry> oldHashEntries = new ArrayList<HashEntry>();
		
		for (int counter_arrayList = 0; counter_arrayList < numBuckets; counter_arrayList++)
		{
			int maxEntries = buckets.get(counter_arrayList).size();	// Find number of entry in linkedList
			
			for (int counter_linkedList = 0; counter_linkedList < maxEntries; counter_linkedList++)
			{
				oldHashEntries.add(buckets.get(counter_arrayList).get(counter_linkedList));
			}
		}
		
		
		// Create new ArrayList<LinkedList<HashEntry>> buckets
		
		ArrayList<LinkedList<HashEntry>> newBuckets = new ArrayList<LinkedList<HashEntry>>();
		
		// Initialize all buckets in ArrayList<LinkedList<HashEntry>> buckets 
		// Each iteration initialize once a LinkedList and add it to the buckets list ArraList
		for (int counter_buckets = 0; counter_buckets < newNumBuckets; counter_buckets++)
		{	
			newBuckets.add(new LinkedList<HashEntry>());
		}
		
		
		// Add all oldEntries into buckets
		
		for (int counter_oldHashEntries = 0; counter_oldHashEntries < oldHashEntries.size(); counter_oldHashEntries++ )
		{
			
			HashEntry currentEntry = oldHashEntries.get(counter_oldHashEntries);	//Get Entry
			int entryPosition = hashFunction(currentEntry.key);	// Get position in ArrayList
		
			newBuckets.get(entryPosition).add(currentEntry);// Add entry
		}
		
		numBuckets = newNumBuckets;
		buckets = newBuckets;
	}
	
	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */
	public boolean containsKey(K key)
	{
		return (get(key) != null);
	}
		
	/*
	 * return an ArrayList of the keys in the hashtable
	 */
	
	public ArrayList<K>  keys()
	{
		
		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE HERE
			
		// Go over each buckets 
		
		for (int counter_buckets = 0; counter_buckets < numBuckets; counter_buckets++)
		{
			
			LinkedList<HashEntry> currentBucket = buckets.get(counter_buckets);// Get current bucket
			int size_currentBucket = currentBucket.size();// Get size current bucket
		
			// Go over each entry and put key in listKeys
			for (int counter_currentBucket = 0; counter_currentBucket < size_currentBucket; counter_currentBucket++)
			{
				HashEntry currentHashEntry = currentBucket.get(counter_currentBucket);	// Get current Hash entry
				listKeys.add(currentHashEntry.key);// Put key in listKeys
			}
		}
		
		return listKeys;

	}
	
	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();
		
		//   ADD YOUR CODE HERE
		
		for (int counter_buckets = 0; counter_buckets < numBuckets; counter_buckets++)
		{
			
			LinkedList<HashEntry> currentBucket = buckets.get(counter_buckets);// Get current bucket
			int size_currentBucket = currentBucket.size();// Get size current bucket
		
			// Go over each entry and put value in listValue
			for (int counter_currentBucket = 0; counter_currentBucket < size_currentBucket; counter_currentBucket++)
			{
				HashEntry currentHashEntry = currentBucket.get(counter_currentBucket);	// Get current Hash entry
				listValues.add(currentHashEntry.value);// Put value in listValue
			}
		}
		
		
		return listValues;
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}
	
	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashEntry>
	{
		LinkedList<HashEntry>  allEntries = new LinkedList<HashEntry>();

		
		/**
		 * Constructor:   make a linkedlist 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{
			
			// TODO HashIterator constructor
			//  ADD YOUR CODE HERE
		
			// Go over each elements of the ArrayList<LinkedList<hashEntry>> buckets and fetch the entries
			/*
			for (int counter_arrayList = 0; counter_arrayList < numBuckets; counter_arrayList++)
			{
				
				// Get current bucket
				int maxEntries = buckets.get(counter_arrayList).size();	// Find number of entry in linkedList
				
				for (int counter_linkedList = 0; counter_linkedList < maxEntries; counter_linkedList++)
				{
					allEntries.add(buckets.get(counter_arrayList).get(counter_linkedList));
				}
			}
			*/
			
			for (int counter_buckets = 0; counter_buckets < numBuckets; counter_buckets++)
			{
				
				LinkedList<HashEntry> currentBucket = buckets.get(counter_buckets);// Get current bucket
				int size_currentBucket = currentBucket.size();// Get size current bucket
			
				// Go over each entry and put key in allEntries
				for (int counter_currentBucket = 0; counter_currentBucket < size_currentBucket; counter_currentBucket++)
				{
					HashEntry currentHashEntry = currentBucket.get(counter_currentBucket);	// Get current Hash entry
					allEntries.add(currentHashEntry);// Put HashEntry in allEntries
				}
			}
			
			
			
		}
		
		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}
			
		//  Override
		@Override
		public HashEntry next()
		{	
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {

		// not implemented,  but must be declared because it is in the Iterator interface
			
		}		
	}
	
	//  helper method
	
	private HashEntry  getEntry(K key){
		int index = hashFunction(key);
 		LinkedList<HashEntry> bucketList = buckets.get(index);
 		for (HashEntry node : bucketList ){
 			if (node.getKey() == key)
 				return node; 
 		}
 		return null;	 	
	}

	class HashEntry {
		
		private K key;
		private V value;
		
		/*
		 * Constructor.
		 */
		HashEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/*
		 * Returns this hash entry's key.   Assume entry is not null.
		 * @return This hash entry's key
		 */
		K getKey() {
			return(key);
		}
		
		/**
		 * Returns this hash entry's value.  Assume entry is not null.
		 */
		V getValue() {
			return(value);
		}
		
		/**
		 * Sets this hash entry's value.
		 */
		void setValue(V value) {
			this.value =  value;		
		}		
	}


}
