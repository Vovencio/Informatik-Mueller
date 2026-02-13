import java.util.Objects;

public class IntComparable implements ComparableContent<IntComparable>{
    Integer wert;

    public IntComparable(Integer wert) {
        this.wert = wert;
    }

    @Override
    public boolean isGreater(IntComparable pContent) {
        return wert > pContent.wert;
    }

    @Override
    public boolean isEqual(IntComparable pContent) {
        return Objects.equals(wert, pContent.wert);
    }

    @Override
    public boolean isLess(IntComparable pContent) {
        return wert < pContent.wert;
    }

    public static IntComparable search(BinarySearchTree<IntComparable> comparable) {
        return null;
    }
}
