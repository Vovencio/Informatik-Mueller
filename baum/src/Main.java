import java.util.Objects;

public class Main {

    // Ein baum zum Testen.
    public static ArrayTree<Integer> testBaum() {
        ArrayTree<Integer> baum = new ArrayTree<>(4);

        for (int i = 0; i < 4; i++) {
            ArrayTree<Integer> child = new ArrayTree<Integer>(i);

            baum.addChild(child);
            for (int j = 0; j < 4; j++) {
                child.addChild(new ArrayTree<Integer>(j));
            }
        }

        return baum;
    }

    public static void main(String[] args) {
        ArrayTree<Integer> baumA = testBaum();
        ArrayTree<Integer> baumB = testBaum();

        System.out.println(baumA);

        System.out.println(Objects.equals(baumA, baumB));

        System.out.printf("%nHash-Werte:%nA = %d%nB = %d%n", baumA.hashCode(), baumB.hashCode());

        baumA.removeChild(0);

        System.out.printf("%nHash-Werte:%nA = %d%nB = %d%n", baumA.hashCode(), baumB.hashCode());

    }
}