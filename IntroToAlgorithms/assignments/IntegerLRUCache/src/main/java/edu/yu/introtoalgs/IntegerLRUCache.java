package edu.yu.introtoalgs;

import java.util.*;

/** A class that provides a cache associating Integer-valued keys and values.
 * The implementation is constrained to a given capacity such that if the
 * number of entries exceeds the capacity, entries are removed to to maintain
 * the "does not exceed capacity" constraint.  When removing elements to
 * maintain the capacity constraint, the implementation follows the
 * "least-recently-used" policy.
 *
 */

public class IntegerLRUCache {

  private Map<Integer,Node> nodeMap;

  private int capacity;
  public int nodeCount;
  
  
  // private class for nodes in a doubly-linked queue
  // used to keep most-recently-used data
  private class Node {
      private Node prev, next;
      private final Integer key; 
      private final Integer value;

      Node(Integer key, Integer value) {
          prev = next = null;
          this.key = key;
          this.value = value;
      }  
      
      @Override
      public String toString() {
    	  return String.format("(%d:%d)", this.key, this.value);
      }
  }
  
  private Node head = null; // least-recently used, get's chopped off
  private Node tail = null; // most-recently used, newest get's added here
	
  /** Constructs an empty cache with the specified capacity.  The cache
   * implementation is forbidden from exceeding this capacity, but may choose
   * to use less than this capacity.
   *
   * @param initialCapacity the initial capacity
   */
  public IntegerLRUCache(final int capacity) {
      if (capacity < 0) throw new IllegalArgumentException("Cannot have negative capacity");
	  this.capacity = capacity;
      this.nodeCount = 0;
      this.nodeMap = new HashMap<>();
  }

  /** Returns the cached value associated with the specified key, null if not
   * cached previously
   *
   * @param key the key whose associated value is to be returned
   * @return the previously cached value, or null if not previously cached
   * @throws IllegalArgumentException if the key is null
   */
  public Integer get(final Integer key) {
	  if (key == null) throw new IllegalArgumentException("Cannot get with null key");
	  Node node = nodeMap.get(key);
	  if (node == null) return null;
	  else {
		  deleteFromQueue(node);
		  enqueue(node);
		  return node.value;
	  }
  }

  /** Associates the specified value with the specified key. If the cache
   * previously contained an association for this key, the old value is
   * replaced, and the key is now associated with the specified value.  If
   * inserting this item will cause the cache to exceed its capacity, the
   * method will evict some other item to maintain the capacity constraint.
   * The item selected is the least-recently-used ("accessed") item.
   *
   * @param key key with which the specified value is to be associated
   * @param value value to be cached
   * @return the value associated with this key if previously cached, otherwise
   * null
   * @throws IllegalArgumentException if either the key or value is null
   */
  public Integer put (final Integer key, final Integer value) {
	  if (key == null || value == null) throw new IllegalArgumentException("Cannot put null key or value");
	  Node newNode = new Node(key, value);
	  Node oldNode = nodeMap.put(key, newNode);
      if (oldNode == null) { // this is a new addition to cache
    	  enqueue(newNode);
    	  if (this.nodeCount > this.capacity) { // if over capacity, dequeue and remove LRU from map
    			Node previousHead = dequeue();
    			Integer previousHeadKey = previousHead.key;
    			nodeMap.remove(previousHeadKey);
    	  }
    	  return null;
      }
      else {
    	  deleteFromQueue(oldNode);
    	  enqueue(newNode); // should this be oldNode?
    	  return oldNode.value;
      }
  }

  /** Removes the specified key-value mapping if present (returning the value
   * previously associated with the key), no-op returns null if no previous
   * association.
   *
   * @param key key whose mapping is to be removed
   * @return previous value associated with key, otherwise null
   * @throws IllegalArgumentException if the key is null
   */
  public Integer remove(Object key) {
      if (key == null) throw new IllegalArgumentException("Cannot use null key");
      if (!(key instanceof Integer)) throw new IllegalArgumentException("Cannot remove non-Integer");
      Node previousNode = nodeMap.remove(key);
      if (previousNode == null) return null;
      deleteFromQueue(previousNode);
      return previousNode.value;
  }

  /*
   * adds the node to the tail, increments nodeCount
   */
  private void enqueue(Node node) {
  	if (node == null) throw new IllegalArgumentException("Cannot enqueue a null");
  	Node oldTail = this.tail;
  	if (oldTail == null) { // an empty queue
  		this.tail = node;
  		this.head = node;
  		node.prev = node.next = null;
  		this.nodeCount = 1;
  		return;
  	}
  	node.next = oldTail;
  	node.prev = null;
  	oldTail.prev = node;
  	this.tail = node;
  	this.nodeCount++;
  }
  
  /*
   * removes the current head, sets head to the next-to-last, decrements nodeCount
   * @return the old head
   */
  private Node dequeue() {
  	Node oldHead = this.head;
  	if (oldHead == null) throw new IllegalStateException("Cannot dequeue on an empty queue");
  	this.head = (oldHead.prev);
  	if (this.head != null) this.head.next = null; // prepare oldHead for GC
  	this.nodeCount--;
  	return oldHead;
  }
  
  /*
   * If this is the only node in the queue, effectively redefines a new queue
   * If tail is input, it sets the next node as new tail
   * If head is input, it dequeues
   * Otherwise it just has node.prev skip over this node to node.next
   * Always decrements nodeCount (indirectly in the case where it calls dequeue)
   */
  private void deleteFromQueue(Node node) {
  	if (node == null) throw new IllegalArgumentException("Cannot delete null from queue");
  	if (this.nodeCount == 1) {
  		this.tail = this.head = null;
  		this.nodeCount = 0;
  		return;
  	}
  	if (node == this.tail) {
  		this.tail = node.next;
  		this.tail.prev = null;
  		this.nodeCount--;
  		return;
  	}
  	if (node == this.head) {
  		dequeue(); // this will take care of decrementing nodeCount
  		return;
  	}
		(node.prev).next = node.next;
		this.nodeCount--; // this reflects the "skipping over" of node
  }
  
  @Override
  public String toString() {
	  String result = "Map: %s\nQueue (MRU on the left): %s";
	  String mapString = this.nodeMap.toString();
	  String queueString = "";
	  Node current = this.tail;
	  while (current != null) {
		  queueString += current.toString() + ",";
		  current = current.next;
	  }
	  return String.format(result, mapString, queueString);
  }
  

}
