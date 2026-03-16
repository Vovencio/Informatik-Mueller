class Main {
    static void main() {

    }

    public boolean isComplete(Graph g) {
        List<Vertex> knotenA = g.getVertices();
        List<Vertex> knotenB = g.getVertices();

        knotenA.toFirst();
        while (knotenA.hasAccess()) {
            Vertex a = knotenA.getContent();
            List<Vertex> aNeighbors = g.getNeighbours(a);

            knotenB.toFirst();
            while (knotenB.hasAccess()) {
                Vertex b = knotenB.getContent();

                if (a==b){
                    knotenB.next();
                    continue;
                }

                aNeighbors.toFirst();
                boolean found = false;
                while (aNeighbors.hasAccess()) {
                    aNeighbors.getContent();

                    if (aNeighbors.getContent() == b) {
                        found = true;
                        break;
                    }

                    aNeighbors.next();
                }

                if (!found) {
                    return false;
                }

                knotenB.next();
            }

            knotenA.next();
        }
        return true;
    }
}