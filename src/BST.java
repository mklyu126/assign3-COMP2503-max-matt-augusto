
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *Binary Search Tree class that have all methods to 
 *build, organize, and manage one BST
 */
public class BST<T extends Comparable<T>>{
	
	/**
	 * A node class necessary for the BST class 
	 */
	class BSTNode implements Comparable<BSTNode>{
		private T data;
		private BSTNode left;
		private BSTNode right;
		
		/**
		 * Constructor
		 * @param data is any type of data
		 */
		public BSTNode(T data) {
			setLeft(null);
			setRight(null);
			setData(data);
		}

		public T getData() {
			return data;
		}

		public void setData(T d) {
			data = d;
		}

		public void setLeft(BSTNode leftNode) {
			left = leftNode;
		}

		public void setRight(BSTNode rightNode) {
			right = rightNode;
		}

		public BSTNode getLeft() {
			return left;
		}

		public BSTNode getRight() {
			return right;
		}
		
		/**
		 * Method identify if the node is a leaf
		 * @return true if its a leaf and false otherwise
		 */
		public boolean isLeaf() {
			return (getLeft() == null) && (getRight() == null);
		}
		
		/**
		 * Overrides the compareTo method to organize the nodes in the tree
		 */
		@Override
		public int compareTo(BSTNode otherNode) {
			return this.getData().compareTo(otherNode.getData());
		}



	}

	private Comparator<T> comparator;
	private BSTNode root;
	private int size;
	
	
	/**
	 * Constructor
	 */
	public BST() {
		
		root = null;
		size = 0;
	}
	
	/**
	 * Copy constructor used when the tree needs a different ordering
	 * @param comparator is any type of comparator
	 */
	public BST(Comparator<T> comparator) {
		this.comparator = comparator;
		root = null;
		size = 0;
	}
	
	/**
	 * Size method that returns the size of the tree
	 * @return size of the tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Add method used to add data to the tree
	 * @param data is any type of data
	 */
	public void add(T data) {
		root = add(root, data);
		size++;
	}
	
	/**
	 * Find method can find any data that is in the tree
	 * @param data is any type of data
	 * @return true if the data is in the tree or false otherwise
	 */
	public T find(T data) {
		return find(data, root);
	}
	
	/**
	 * Delete method is used to delete data from the tree
	 * @param data is any type of data
	 */
	public void delete(T data) {
		root = delete(root, data);
		size--;
	}
	
	/**
	 * Height method gives the height of the tree
	 * @return height of the tree
	 */
	public int height() {
		return height(root);
	}
	

	private BSTNode add(BSTNode curr, T data) {
		if (curr == null) {
			curr = new BSTNode(data);
		} else {
			int c;
			if (comparator != null) {
				c = comparator.compare(data, curr.getData());
			} else {
				c = data.compareTo(curr.getData());
			}
			if (c < 0) {
				curr.left = add(curr.left, data);
			} else if (c > 0) {
				curr.right = add(curr.right, data);
			}
		}
		return curr;
	}

	private T find(T data, BSTNode curr) {
		if (curr == null) {
			return null;
		}
		int c = data.compareTo(curr.getData());

		if (c == 0) {
			return curr.getData();
		} else if (c < 0) {
			return find(data, curr.getLeft());
		} else {
			return find(data, curr.getRight());
		}
	}

	private BSTNode delete(BSTNode curr, T data) {
		if (curr == null) {
			return null;
		}

		int c = data.compareTo(curr.data);
		
		if (c < 0) {
			curr.left = delete(curr.left, data);
		} else if (c > 0) {
			curr.right = delete(curr.right, data);
		} else {
			if (curr.left == null) {
				return curr.right;
			} else if (curr.right == null) {
				return curr.left;
			}
			curr.data = findMin(curr.left);
			curr.left = delete(curr.left, curr.data);
		}
		return curr;
	}
	
	/**
	 * Method that finds the last member of the right part of a node 
	 * that is passed to the method
	 * @param curr is a node
	 * @return the last node data in the right tree
	 */
	private T findMin(BSTNode curr) {
		while (curr.right != null) {
			curr = curr.right;
		}
		return curr.data;
	}

	private int height(BSTNode curr) {
		if (curr == null) {
			return -1;
		}
		int leftHeight = height(curr.left);
		int rightHeight = height(curr.right);

		return 1 + Math.max(leftHeight, rightHeight);
	}

	/**
	 * Method that calls an iterator for the tree
	 * @return a new iterator for the tree
	 */
	public Iterator<T> iterator(){
		return new BSTIterator<>(root);
		
	}

	/**
	 * BSTIterator class is a personalized class to iterate 
	 * throughout the trees
	 */
	class BSTIterator<T> implements Iterator<T>{
		
		private Queue<T> queue;
		
		/**
		 * Check if the node has data, and if there is add it to a queue
		 * @param r is a node from the tree
		 */
		private void visit(BSTNode r) {
			if (r != null)
				queue.add((T) r.getData());
				
		}
		
		/**
		 * Check every node of the tree in order from the least to most
		 * @param r is a node from the tree
		 */
		private void inOrderTraversal(BSTNode r) {
			
			if (r == null)
				return;
			else {
				
				inOrderTraversal(r.getLeft());
				visit(r);
				inOrderTraversal(r.getRight());
			}
		}
		
		/**
		 * Constructor
		 * @param root is a node
		 */
		public BSTIterator(BSTNode root) {
			queue = new LinkedList<>();
			inOrderTraversal(root);
		}
		
		/**
		 * Checks if the node has a next 
		 * @return true if there is a next and false otherwise
		 */
		public boolean hasNext() {
			return !queue.isEmpty();
		}
		
		/**
		 * Checks if the node has a next but instead of returning true or false,
		 * it retrieve and removes the data in the head of the queue
		 */
		public T next() {
			if(hasNext())
			{
				return queue.poll();
			}
			throw new UnsupportedOperationException("No more elements in the iteration.");
		}
	}

}
