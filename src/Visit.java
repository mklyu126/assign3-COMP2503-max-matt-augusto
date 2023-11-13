public interface Visit<T extends Comparable<T>> {
    void visit(T t);
}
