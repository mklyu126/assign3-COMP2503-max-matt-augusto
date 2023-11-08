package src;

import java.util.Comparator;

public class BST<T extends Comparable<T>> {

    private Node<T> root;
    private Comparator<T> comparator;

    public BST(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(T data) {
        root = insertHelper(root, data);
    }

    private Node<T> insertHelper(Node<T> root, T data) {
        if (root == null) {
            root = new Node<T>(data);
            return root;
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(insertHelper(root.getLeft(), data));
        } else {
            root.setRight(insertHelper(root.getRight(), data));
        }
        return root;
    }

    public void display() {
        displayHelper(root);
    }

    private void displayHelper(Node<T> root) {
        if (root != null) {
            displayHelper(root.getLeft());
            System.out.println(root.getRight());
            displayHelper(root.getRight());
        }
    }

    public boolean search(T data) {
        return searchHelper(root, data);
    }

    private boolean searchHelper(Node<T> root, T data) {
        if (root == null) {
            return false;
        }
        int compareResult = comparator.compare(data, root.getData());
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return searchHelper(root.getLeft(), data);
        } else {
            return searchHelper(root.getRight(), data);
        }
    }

    public void remove(T data) {

    }

    public Node removeHelper(Node<T> root, T data) {

    }
}
