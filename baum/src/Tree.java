/**
 * Eine Array implementation eines generällen Baumes
 */
public class Tree<T> {
    private Object[] children;
    private int capacity    = 0;   // Wie viele Elemente unser Baum beinhalten kann.
    private int used        = 0;       // Wie viele Elemente der Baum hat.

    private T content       = null;

    //#region Konstruktoren
    public Tree(T content) {
        this.content = content;
    }

    public Tree(T content, Tree<T>[] children) {
        this.content = content;
        this.children = children;

        capacity = children.length;
        used     = children.length;
    }

    public Tree(Tree<T>[] children) {
        this.children = children;

        capacity = children.length;
        used     = children.length;
    }

    public Tree() {
        children = null;
    }
    //#endregion

    //#region Getter und Setter
    public void removeChild(int index) {
        assert index < used;
        assert index >= 0;

        children[used - 1] = null;
        used--;

        // Wenn wir Elemente verschieben müssen
        if (index != used - 1) {
            for (int i = index; i < used; i++) {
                children[i] = children[i+1];
            }
        }
    }

    public void addChild(Tree<T> tree) {
        int newId = used;
        used++;

        // Wenn das ganze array benutzt wurde.
        if (used > capacity) {
            // Wenn das Array nicht existiert, eins mit größe 1 erstellen,
            // ansonsten Größe verdoppeln.
            int newCapacity = (children == null) ? 1 : capacity * 2;

            Object[] newChildren = new Object[newCapacity];

            // Die alten Daten kopieren
            for (int i = 0; i < capacity; i++) {
                newChildren[i] = children[i];
            }

            children = newChildren;
            capacity = newCapacity;
        }
        children[newId] = tree;
    }

    public void setChild(int id, Tree<T> tree) {
        assert id < used;

        children[id] = tree;
    }

    public Tree<T> getChild(int id) {
        assert id < used;

        return (Tree<T>) children[id];
    }

    public Tree<T>[] getChildren() {
        Object[] subArray = new Tree[used];

        for (int i = 0; i < used; i++) {
            subArray[i] = children[i];
        }

        return (Tree<T>[]) subArray;
    }

    public void setChildren(Tree<T>[] children) {
        this.children = children;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUsed() {
        return used;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    //#endregion
}
