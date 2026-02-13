void main() {
    IntComparable a = new IntComparable(10);
    IntComparable b = new IntComparable(21);
    IntComparable c = new IntComparable(1);
    IntComparable d = new IntComparable(-20);

    BinarySearchTree<IntComparable> v = new BinarySearchTree<>();

    v.insert(a);
    v.insert(b);
    v.insert(c);
    v.insert(d);

    v.remove(new IntComparable(12));
}
