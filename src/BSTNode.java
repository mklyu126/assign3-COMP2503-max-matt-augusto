
public class BSTNode implements Comparable<BSTNode> {
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
