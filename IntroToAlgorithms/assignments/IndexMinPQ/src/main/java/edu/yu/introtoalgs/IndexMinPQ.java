package edu.yu.introtoalgs;

import java.util.*;


public class IndexMinPQ<Key extends Comparable<Key>>
	implements Iterable<Integer>
{
	private int N = 0;
	public int[] pq;
	public int[] qp;
	public Key[] keys;
	
	@SuppressWarnings("unchecked")
	public IndexMinPQ(int maxN) {
		this.keys = (Key[]) new Comparable[maxN + 1];
		this.pq = new int[maxN + 1];
		this.qp = new int[maxN + 1];
		for (int i = 0; i <= maxN; i++) qp[i] = -1;
	}
	
	public IndexMinPQ(Key[] keys, int[] pq, int[] qp, int N) {
		this.keys = keys;
		this.pq = pq;
		this.qp = qp;
		this.N = N;
	}
	
	public void insert(int i, Key key) {
		rejectNullKey(key);
		i++; // translate into internal 1-indexing
		rejectIllegalIndex(i);
		rejectUsedIndex(i);
		N++;
		qp[i] = N;
		pq[N] = i;
		keys[i] = key;
		swim(N);
	}

	public Key keyOf(int i) {
		i++; // translate into internal 1-indexing
		rejectIllegalIndex(i);
		if (!contains(i)) throw new IllegalStateException("No key associated with given index");
		return keys[i];
	}
	
	public int delMin() {
		if (isEmpty()) throw new IllegalStateException("Cannot delMin on empty PQ");
		int indexOfMin = minIndex();
		exch(1, N--);
		sink(1);
		keys[pq[N+1]] = null;
		qp[pq[N+1]] = -1;
		return --indexOfMin; // translate back into 0-indexing
	}
	
	public Key minKey() {
		if (isEmpty()) throw new IllegalStateException("No minKey on empty PQ");
		return keys[pq[1]];
	}
	
	public int minIndex() {
		if (isEmpty()) throw new IllegalStateException("No minIndex on empty PQ");
		return pq[1];
	}
	
	public int size() {
		return N;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	private boolean contains(int i) {
		return qp[i] != -1; // assumes i is 1-indexed already
	}
	
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}
	
	private void exch(int i, int j) {
		int pqi = pq[i];
		int pqj = pq[j];
		pq[i] = pqj;
		pq[j] = pqi;
		// whatever values in pq changed, we need to 
		// change those indices in qp to have new values
		// to index into pq properly
		qp[pqi] = j;
		qp[pqj] = i;
	}
	
	private void swim(int k) {
		while (k > 1 && greater(k/2, k)) {
			exch(k/2, k);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
	
	@Override
	public Iterator<Integer> iterator() {
		Queue<Integer> q = new LinkedList<>();
		// get snapshot
		Key[] keysCopy = Arrays.copyOf(this.keys, this.keys.length);
		int[] pqCopy = Arrays.copyOf(this.pq, this.pq.length);
		int[] qpCopy = Arrays.copyOf(this.qp, this.qp.length);
		IndexMinPQ<Key> copy = new IndexMinPQ<>(keysCopy, pqCopy, qpCopy, N);
		// pop min and add to queue until PQ empty
		while (!copy.isEmpty()) {
			q.add(copy.minIndex() - 1); // translate back into 0-indexing
			copy.delMin();
		}
		return q.iterator();
	}

	/**
	 * expects i to be 1-indexed already
	 * @param i
	 */
	private void rejectIllegalIndex(int i) {
		if (i >= pq.length || i < 1) throw new 
			IllegalArgumentException("Index " + i + "is out of range for this PQ");
	}
	
	/**
	 * expects i to be 1-indexed already
	 * @param i
	 */
	private void rejectUsedIndex(int i) {
		if (keys[i] != null) throw new 
			IllegalArgumentException("Cannot insert with index that is already in use");
	}
	
	private void rejectNullKey(Key key) {
		if (key == null) throw new 
			IllegalArgumentException("Cannot put null key, it cannot be compared");
	}

}
