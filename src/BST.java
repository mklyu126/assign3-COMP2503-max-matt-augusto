
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<T>>{
	private static final int INORDER = 1;
	private static final int PREORDER = 2;
	private static final int POSTORDER = 3;
	private Queue<Token> queue;
	
	
	class BSTNode implements Comparable<BSTNode>{
		private T data;
		private BSTNode left;
		private BSTNode right;
		
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

		public boolean isLeaf() {
			return (getLeft() == null) && (getRight() == null);
		}

		@Override
		public int compareTo(BSTNode otherNode) {
			return this.getData().compareTo(otherNode.getData());
		}



	}

	private Comparator<T> comparator;
	private BSTNode root;
	private int size;
	
	
	
	public BST() {
		
		root = null;
		size = 0;
	}

	public BST(Comparator<T> comparator) {
		this.comparator = comparator;
		root = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public void add(T data) {
		root = add(root, data);
		size++;
	}

	public T find(T data) {
		return find(data, root);
	}

	public void delete(T data) {
		root = delete(root, data);
		size--;
	}

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

			curr.data = findMin(curr.right);
			curr.right = delete(curr.right, curr.data);
		}
		return curr;
	}

	private T findMin(BSTNode curr) {
		while (curr.left != null) {
			curr = curr.left;
		}
		return curr.data;
	}

	private int height(BSTNode curr) {
		if (curr == null) {
			return -1;
		}
		int leftHeight = height(curr.left);
		int rightHeight = height(curr.right);

		return Math.max(leftHeight, rightHeight) + 1;
	}

//    private void traverse(BSTNode root, int order, Visit<T> visit) {
//        if (root != null) {
//            if (order == INORDER) {
//                traverse(root.getLeft(), order, visit);
//                visit.visit(root.getData());
//                traverse(root.getRight(), order, visit);
//            } else if (order == PREORDER) {
//                visit.visit(root.getData());
//                traverse(root.getLeft(), order, visit);
//                traverse(root.getRight(), order, visit);
//            } else if (order == POSTORDER) {
//                traverse(root.getLeft(), order, visit);
//                traverse(root.getRight(), order, visit);
//                visit.visit(root.getData());
//            }
//        }
//    }
	
	


	
	
	/**
	 * This method does the printing 
	 * @return 
	 */
	public Iterator<T> iterator(){
		return new BSTIterator<>(root);
		
	}
//	public T Iterator() {
//		inOrderTraversal(root);
//		
//		
//		while(hasNext()) {
//			return (T) queue.poll().toString();
//		}
		
//	}
	
	class BSTIterator<T> implements Iterator<T>{
		
		private void visit(BSTNode r) {
			if (r != null)
				queue.add((Token)r.getData());
				
//				System.out.println(r.getData());
		}
		

		private void inOrderTraversal(BSTNode r) {
			
			if (r == null)
				return;
			else {
				
				inOrderTraversal(r.getLeft());
				visit(r);
				inOrderTraversal(r.getRight());
			}
		}
		
		public BSTIterator(BSTNode root) {
			queue = new LinkedList<>();
			inOrderTraversal(root);
		}
		
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		public T next() {
			if(hasNext())
			{
				return (T)queue.poll();
			}
			throw new UnsupportedOperationException("No more elements in the iteration.");
		}
	}




	


//	private class IteratorVisit implements Visit<T> {
//		@Override
//		public void visit(T data) {
//			queue.add(data);
//		}
//	}

}
