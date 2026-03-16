import java.util.Objects;

public class Freundeskreis extends Graph {

    public void addFreund(String name) {
        List<Vertex> knoten = getVertices();
        knoten.toFirst();

        Vertex neuerFreund = new Vertex(name);
        addVertex(neuerFreund);

        while (knoten.hasAccess()) {
            addEdge(new Edge(neuerFreund, knoten.getContent(), 0.0));

            knoten.next();
        }
    }

    public boolean isInFriendGroup(Graph g, String personA, String personB) {
        Vertex personAVertex = g.getVertex(personA);
        Vertex personBVertex = g.getVertex(personB);

        if (personAVertex == null)
            return false;
        if (personBVertex == null)
            return false;


        List<Vertex> personAFriends = g.getNeighbours(personAVertex);
        personAFriends.toFirst();

        boolean found = false;

        while (personAFriends.hasAccess()) {
            boolean friends = Objects.equals(
                    personBVertex, personAFriends.getContent()
            );
            if (friends) {
                found = true;
                break;
            }

            personAFriends.next();
        }

        return found;
    }
}
