public class Freundeskreis {
    Graph meinGraph = new Graph();

    public Freundeskreis(List<String> freunde) {
        freunde.toFirst();

        while (freunde.hasAccess()){
            Vertex freundKnoten = new Vertex(freunde.getContent());
            meinGraph.addVertex(freundKnoten);

            List<Vertex> alteFreunde = meinGraph.getVertices();
            alteFreunde.toFirst();

            while (alteFreunde.hasAccess()) {
                if (alteFreunde.getContent() == freundKnoten) {
                    break;
                }

                Edge neueFreundschaft = new Edge(freundKnoten, alteFreunde.getContent(), 0);

                meinGraph.addEdge(neueFreundschaft);

                alteFreunde.next();
            }

            freunde.next();
        }
    }
}
