package edu.yu.da;

import java.util.*;

/** Encapsulates a set of "ancestors and descendants", modeled as a binary
 * tree, whose vertices are represented as unique non-negative integers.  Each
 * vertex_i is associated with a double-valued value_i which need not be unique
 * over the set of vertices.
 *
 * @author Avraham Leff
 */

public class MaxOverBTDescendants {
	
	class Node {
		Node parent, right, left;
		int id;
		double value;
		boolean considered;
		
		public Node(Node parent, int id, double value) {
			this.parent = parent;
			this.id = id;
			this.value = value;
			right = null;
			left = null;
			considered = false;
		}
		/*
		public Node getCopy() {
			return new Node(this.parent, this.id, this.value); 
		}
		*/
	}
	
	Node root;
	Node[] idToNode;
	Set<Node> leaves;
	
  /** Constructor: initializes a binary tree to have a root and value, and
   * specifies the number of vertices that will eventually populate the tree.
   *
   * @param V the number of vertices that will eventually populate the tree,
   * must be >= 1 (because tree must at least have a root)
   * @param root must be >= 0 and < V, and specifies the root vertex of the tree.
   * @param value the value associated with the root.
   */
  public MaxOverBTDescendants(final int V, final int root, final double value) {
    this.idToNode = new Node[V];
    this.leaves = new HashSet<>();
    Node rootNode = new Node(null, root, value);
    leaves.add(rootNode);
    this.root = rootNode;
    idToNode[root] = rootNode;
  }

  /** Connects the specified child vertex to the specified parent (which must
   * already be connected to the tree).  All vertex ids  must be >= 0 and < V.
   *
   * @param parent a non-negative integer that identifies a vertex already
   * connected to the tree
   * @param child a non-negative integer that identifies a vertex being added
   * for the first (and only) time to the tree.
   * @param value the value associated with the child node
   * @throw IllegalArgumentException if the invariants are violated
   */
  public void addChild(final int parent, final int child, final double value) {
    checkForAddChildErrors(parent, child, value);
	  Node parentNode = idToNode[parent];
    Node childNode = new Node(parentNode, child, value);
    if (parentNode.left == null) {
    	parentNode.left = childNode;
    }
    else if (parentNode.right == null) {
    	parentNode.right = childNode;
    }
    else throw new IllegalStateException("Parent already has 2 children");
	idToNode[child] = childNode;
	leaves.add(childNode);
	leaves.remove(parentNode);
  }

  private void checkForAddChildErrors(int parent, int child, double value) {
	  int V = idToNode.length;
	  if (parent >= V || child >= V || parent < 0 || child < 0)
		  throw new IllegalArgumentException("Node id out of range");
	  if (idToNode[parent] == null) 
		  throw new IllegalArgumentException("parent not in BT");
	  if (idToNode[child] != null)
		  throw new IllegalArgumentException("child id already in BT");
	  if (idToNode[parent].left != null && idToNode[parent].right != null)
		  throw new IllegalArgumentException("parent already has 2 children");	
}

/** Returns an array whose ith element is the the maximum value, over all
   * values associated with the ith element's descendants in the tree,
   * including the value associated with the ith element itself.
   *
   * @return array of doubles with the semantics specified above.
   */
  public double[] maxOverAllBTDescendants() {
    double[] values = new double[idToNode.length];
    
    // put in inherent values
    for (int i = 0; i < idToNode.length; i++) {
    	if (idToNode[i] != null) values[i] = idToNode[i].value;
    }
    
    // virtual leaves start as real leaves
	Stack<Node> virtualLeaves = new Stack<>();
    for (Node n: leaves) virtualLeaves.push(n);
    
    // iterate bottom-up, updating parents as necessary
    while (!virtualLeaves.isEmpty()) {
    	Node current = virtualLeaves.pop();
    	current.considered = true;
    	Node parent = current.parent;
    	if (current.parent == null) continue; // it's the root, don't try to update parent
    	values[current.parent.id] = Math.max(values[current.id], values[current.parent.id]);
    	if (isNewVirtualLeaf(parent)) virtualLeaves.push(parent);
    }
    
    // return every Node's considered field to false
    cleanUp();
    
    return values;
  }

private void cleanUp() {
	for (Node n: idToNode) if (n != null) n.considered = false;
}

private boolean isNewVirtualLeaf(Node parent) {
	Node left = parent.left;
	Node right = parent.right;
	if ( (left == null || left.considered) &&
		 (right == null || right.considered)
	   ) return true;
	return false;
}

}