import java.util.Objects;

/**
 * Eine Array implementation eines allgemeinen Baumes.
 *
 * @implNote Ein Baumknoten kann nur einen Elternknoten haben und damit nur einen Baum zugehören
 * und keine Schleifen enthalten!
 *
 * @author Vladimir V. K.
 */
public class ArrayTree<T> {
    //#region Felder
    /**
     * Der Elternknoten des Baumknotens.
     */
    private ArrayTree<T> parent;

    /**
     * Der Index dieses Knotens im Elternarray.
     */
    private int parentIndex = -1;

    /**
     * Die Kindknoten des Baumes.
     */
    private Object[] children;
    /**
     * Bestimmt, wie viele Elemente unser Baum beinhalten kann.
     */
    private int capacity    = 0;
    /**
     * Bestimmt, wie viele Elemente der Baum hat.
     */
    private int used        = 0;

    /**
     * Der Inhalt des Baumknotens.
     */
    private T content       = null;
    //#endregion

    //#region Konstruktoren
    public ArrayTree(T content) {
        this.content = content;
        children = null;
    }

    public ArrayTree(T content, ArrayTree<T>... children) {
        this.content = content;

        setChildren(children);
    }

    public ArrayTree(ArrayTree<T>... children) {
        setChildren(children);
    }

    public ArrayTree() {
        children = null;
    }
    //#endregion

    //#region Methoden
    /**
     * Entfernt einen Kindknoten mit dem Index im Elternarray.
     */
    public void removeChild(int index) {
        assert index < used;
        assert index >= 0;

        ArrayTree<T> removedChild   = ((ArrayTree<T>) children[index]);

        removedChild.parentIndex    = -1;
        removedChild.parent         = null;

        used--;

        // Wenn wir Elemente verschieben müssen
        if (index != used - 1) {
            for (int i = index; i < used; i++) {
                children[i] = children[i+1];

                getChild(i).parentIndex = i;
            }
        }

        children[used] = null;
    }

    /**
     * Entfernt einen Kindknoten mit einer Referenz.
     */
    public void removeChild(ArrayTree<T> arrayTree) {
        removeChild(arrayTree.parentIndex);
    }

    public void addChild(ArrayTree<T> arrayTree) {
        int newId = used;

        setParent(arrayTree, this, newId);

        used++;

        // Wenn das ganze array benutzt wurde.
        if (used > capacity) {
            // Wenn das Array nicht existiert, eins mit größe 1 erstellen,
            // ansonsten Größe verdoppeln.
            int newCapacity = (children == null || capacity == 0) ? 1 : capacity * 2;

            Object[] newChildren = new Object[newCapacity];

            // Die alten Daten kopieren
            for (int i = 0; i < capacity; i++) {
                newChildren[i] = children[i];
            }

            children = newChildren;
            capacity = newCapacity;
        }
        children[newId] = arrayTree;
    }

    /**
     * Helper Methode, wenn man den Elternknoten eines Baumes verändert.
     */
    private void setParent(ArrayTree<T> tree, ArrayTree<T> parentTree, int index) {
        assert tree         != null;
        assert parentTree   != null;

        if (!tree.isOrphan()) {
            throw new IllegalArgumentException("Man darf nicht einen Baumknoten hinzufügen, welcher" +
                    " bereits verwendet wird!");
        }

        if (tree == parentTree) {
            throw new IllegalArgumentException("Ein Baum darf nicht sein eigenes Kind sein!");
        }

        if (tree.contains(parentTree)) {
            throw new IllegalArgumentException("Ein Baum darf keine Schleifen beinhalten!");
        }

        tree.parent = parentTree;
        tree.parentIndex = index;
    }

    /**
     * Überprüft, ob der Baumknoten einen Elternknoten besitzt.
     */
    public boolean isOrphan() {
        return parent == null;
    }

    /**
     * @return Wahrheitswert, ob der neue Knoten eine Wurzel ist.
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * @return Wahrheitswert, ob der neue Knoten ein Blatt ist.
     */
    public boolean isLeaf() {
        return used == 0;
    }

    /**
     * @return Wahrheitswert, ob der neue Knoten ein innerer Knoten ist.
     */
    public boolean isInner() {
        return !(isRoot() || isLeaf());
    }

    /**
     * @return Ob der Knoten Kinder hat.
     */
    public boolean isEmpty() {
        return used == 0;
    }

    /**
     * @return Gesamtzahl aller Knoten im Teilbaum.
     */
    public int size() {
        int size = 1;

        for (int i = 0; i < used; i++) {
            ArrayTree<T> child = getChild(i);

            size += child.size();
        }

        return size;
    }

    /**
     * @return Wie tief dieser Knoten im Gesamtbaum ist.
     */
    public int depth() {
        int d = 0;
        ArrayTree<T> current = this;

        while (!current.isRoot()) {
            d++;

            current = current.getParent();
        }

        return d;
    }

    /**
     * @return Knoten mit gegebenen Inhalt (nur direkte Kinder). Null, wenn nicht gefunden.
     */
    public ArrayTree<T> shallowSearch(T searchedContent) {
        for (int i = 0; i < used; i++) {
            ArrayTree<T> child = getChild(i);

            if (Objects.equals(child.content, searchedContent)) {
                return child;
            }
        }

        return null;
    }

    /**
     * @return Knoten mit gegebenen Inhalt. Null wenn nicht gefunden.
     */
    public ArrayTree<T> deepSearch(T searchedContent) {
        if (Objects.equals(searchedContent, content))
            return this;

        for (int i = 0; i < used; i++) {
            ArrayTree<T> child = getChild(i);

            ArrayTree<T> found = child.deepSearch(searchedContent);

            if (found != null) {
                return found;
            }
        }

        return null;
    }

    /**
     * @return Wahrheitswert, ob ein Knoten im Teilbaum enthalten ist (per Referenz).
     */
    public boolean contains(ArrayTree<T> searchedTree) {
        if (searchedTree == this)
            return true;

        for (int i = 0; i < used; i++) {
            ArrayTree<T> child = getChild(i);

            boolean found = child.contains(searchedTree);

            if (found) {
                return true;
            }
        }

        return false;
    }

    //#endregion

    //#region Java Kram

    @Override
    public boolean equals(Object object) {
        // Wenn gleicher Objekt, dann wahr.
        if (this == object)
            return true;

        // Wenn ein das Objekt einer anderen Klasse zugehört oder ungültig ist.
        if (object == null || getClass() != object.getClass()) return false;

        ArrayTree<T> other = (ArrayTree<T>) object;

        // Wenn Inhalt oder Slotanzahl nicht übereinstimmt → nicht gleich
        if (!Objects.equals(content, other.content)) return false;

        if (this.used != other.used) return false;

        // Kinder checken
        for (int i = 0; i < used; i++) {
            if (other.children[i] == null) {
                if (children[i] != null) return false;
            }
            else if (!children[i].equals(other.children[i])) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (content == null) ? 1 : content.hashCode();

        // Wir erstellen ein Array mit den Hashwerten der Kinder
        int[] hashes = new int[used];

        for (int i = 0; i < used; i++) {
            if (getChild(i) == null) {
                hashes[i] = 1;
            }
            else {
                hashes[i] = getChild(i).hashCode();
            }
        }

        // Wir aktualisieren den Hashwert mit der normalen Java Formel für Integer-Arrays:
        // hash = 31 * hash + element

        for (int i = 0; i < used; i++) {
            result = 31 * result + hashes[i];
        }

        return result;
    }

    @Override
    public String toString() {
        String contentString = toStringSimple();

        for (int i = 0; i < used; i++) {
            contentString += ((ArrayTree<T>) children[i]).toString(1, 0, i == used -1);
        }

        return contentString;
    }

    /**
     * @return Eine simple Zusammenfassung des Inhaltes ohne Kinder.
     */
    public String toStringSimple() {
        String result = (isRoot()) ? "" : String.format("[%d] ", parentIndex);

        // Typ des Knotens
        if (isLeaf()) {
            result += "Blatt ";
        }
        else if (isRoot()) {
            result += "Wurzel ";
        }
        else {
            result += "Innerer Knoten ";
        }

        if (content != null) {
            // Klasse und Inhalt
            String inhalt = "(" + content.getClass().getSimpleName() + "): " + content.toString();

            // Wenn zu lang, dann abkürzen
            if (inhalt.length() > 67) {
                inhalt = inhalt.substring(0, 63) + "...";
            }

            result += inhalt;
        }
        else {
            result += ": Null";
        }

        result += "\n";

        return result;
    }

    /**
     * Gibt eine grafische Darstellung eines Baumknotens aus.
     *
     * @param depth Die Tiefe des Knotens.
     * @param strikes Die Anzahl von vertikalen Strichen (Der letzte Knoten eines Elternknotens hat einen weniger).
     * @param isLastChild Wahrheitswert, ob der Kindknoten der letzte ist.
     */
    public String toString(int depth, int strikes, boolean isLastChild) {
        char strike         = '│';
        String junction     = "├";
        String lastChild    = "└";
        String horizontal   = "─";
        String tab          = "    ";

        String result = "";

        //#region Padding
        for (int i = 0; i < depth ; i++) {
            result += tab;
        }

        // Vertikale Striche hinzufügen
        char[] resultAsChar = result.toCharArray();
        for (int i = 0; i < strikes; i++) {
            resultAsChar[(i+1)*tab.length()] = strike;
        }

        result = String.valueOf(resultAsChar);
        //#endregion

        // Prefix
        if (isLastChild) {
            result += lastChild;
        }
        else {
            result += junction;
        }

        result += horizontal;

        result += toStringSimple();

        for (int i = 0; i < used; i++) {
            int childStrikes = (isLastChild) ? strikes : strikes + 1;

            result += ((ArrayTree<T>) children[i]).toString(depth+1, childStrikes, i == used -1);
        }

        return result;
    }

    //#endregion

    //#region Getter und Setter

    public void setChild(int id, ArrayTree<T> arrayTree) {
        assert id < used;

        if (children[id] != null) {
            ArrayTree<T> removedChild = (ArrayTree<T>) children[id];

            removedChild.parent = null;
            removedChild.parentIndex = -1;
        }

        children[id] = arrayTree;
        setParent(arrayTree, this, id);
    }

    public ArrayTree<T> getChild(int id) {
        assert id < used;

        return (ArrayTree<T>) children[id];
    }

    /**
     * @return Eine Kopie des Kindknoten-Arrays. Gibt nur die benutzten Slots zurück.
     */
    public ArrayTree<T>[] getChildren() {
        Object[] subArray = new ArrayTree[used];

        for (int i = 0; i < used; i++) {
            subArray[i] = children[i];
        }

        return (ArrayTree<T>[]) subArray;
    }

    /**
     * @implNote Erstellt eine Kopie des gegebenen Arrays.
     */
    public void setChildren(ArrayTree<T>[] children) {
        assert children != null;

        this.children = new Object[children.length];

        for (int i = 0; i < children.length; i++) {
            this.children[i] = children[i];

            ArrayTree<T> child = ((ArrayTree<T>) children[i]);
            setParent(child, this, i);
        }

        capacity = children.length;
        used     = children.length;
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

    public ArrayTree<T> getParent() {
        return parent;
    }

    //#endregion
}