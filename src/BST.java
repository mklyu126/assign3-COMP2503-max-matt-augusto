
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<T>> {
    class BSTNode implements Comparable<BSTNode> {
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

        public int compareTo(BSTNode otherNode) {
            return this.getData().compareTo(otherNode.getData());
        }

    }

    private Comparator<T> comparator;
    private BSTNode root;
    private int size;

    public interface Visit<T> {
        void visit(T t);
    }

    public BST() {
        root = null;
        size = 0;
    }

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
        root = null;
        size = 0;
    }

    public Iterator<T> iterator() {
        Iterator<T> it = new BSTIterator<T>();
        return it;
    }

    public int size() {
        return size;
    }

    public void add(T data) {
        root = add(root, data);
        size++;
    }

    public boolean find(T data) {
        return find(root, data);
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

    private boolean find(BSTNode curr, T data) {
        if (curr == null) {
            return false;
        }
        int c = data.compareTo(curr.data);

        if (c < 0) {
            return find(curr.left, data);
        } else if (c > 0) {
            return find(curr.right, data);
        } else {
            return true;
        }
    }

    private BSTNode delete(BSTNode curr, T data) {
        if (curr == null) {
            return null;
        }

        int c = data.compareTo(curr.data);

        if(c < 0 ){
            curr.left = delete(curr.left, data)
        } else if(c > 0){
            curr.right = delete(curr.right, data);
        } else {
            if (curr.left == null){
                return curr.right;
            } else if (curr.right == null){
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

    private void inOrderTraversal(BSTNode curr, LinkedList<T> list) {
        if (curr == null) {
            return;
        } else {
            inOrderTraversal(curr.left, list);
            curr.getData();
            inOrderTraversal(curr.right, list);
        }

    }

    private class BSTIterator<T> implements Iterator<T>, Visit<T> {
        private Queue<T> queue = new LinkedList<T>();

        public BSTIterator(){
            queue.clear();
            inOrderTraversal(root, this);

            
        }

        @Override
        public void visit(T t) {
            if (t != null) {
                queue.add(t);
            }
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        public T next() {
            return queue.poll();
        }

}
