import java.util.ArrayList;

public class Graph {

    /**
     * Node class
     **/
    public static class Node {
        int vertex;
        double weight;

        Node(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    private ArrayList<ArrayList<Node>> adj; // adjacency list
    private int size; // represents the number of vertices in the graph
    private int edges; // represents the number of edges in the graph
    boolean isDirected; // true when the graph is directed otherwise false
    // TODO add other instance variables that might be needed
    boolean doubleEdges;
    boolean globalVisited[];

    /***
     * initialize an undirected graph with n vertices and no edges
     * 
     * @param vertices
     */
    public void ugraph(int vertices) {
        size = vertices;
        edges = 0;
        adj = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<Node>());
        }
        isDirected = false;
        doubleEdges = false;
        globalVisited = new boolean[vertices];

    }

    /***
     * initialize a directed graph with n vertices and no edges
     * 
     * @param vertices
     */
    public void dgraph(int vertices) {
        size = vertices;
        edges = 0;
        adj = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<Node>());
        }
        isDirected = true;
        doubleEdges = false;
        globalVisited = new boolean[vertices];
    }

    /***
     * add an edge to a graph that goes from vertex u to vertex v and has weight w;
     * if u or v are not vertices in the graph return false otherwise return true
     *
     * Make sure that you add the edge correctly for both directed and undirected
     * graph
     *
     * @param fromV
     * @param toV
     * @param weight
     */
    public boolean addEdge(int fromV, int toV, double weight) {
        if (fromV >= size || toV >= size || fromV < 0 || toV < 0) {
            return false;
        }
        for (int i = 0; i < adj.get(fromV).size(); i++) {
            if (adj.get(fromV).get(i).vertex == toV) {
                doubleEdges = true;
            }
        }
        Node add = new Node(toV, weight);
        adj.get(fromV).add(add);
        if (!isDirected) {
            Node addBack = new Node(fromV, weight);
            adj.get(toV).add(addBack);
        }
        edges++;
        return true;

    }

    /***
     * print the number of vertices in the graph
     */
    public int vertexSize() {
        return size;
    }

    /***
     * print the number of edges in the graph
     */
    public int edgeSize() {
        return edges;
    }

    /***
     * print the weight of the edge from vertex u to vertex v
     * if u or v are not vertices in the graph return false otherwise return -1.0
     * 
     * @param u
     * @param v
     */
    public double weight(int u, int v) {
        if (u < 0 || u >= size || v >= size || v < 0) {
            return -1.0;
        }
        for (int i = 0; i < adj.get(u).size(); i++) {
            if (adj.get(u).get(i).vertex == v) {
                return adj.get(u).get(i).weight;
            }
        }
        return -1.0;
    }

    /***
     * print a list of vertices that are adjacent to vertex v
     * return a String containing the list of adjacent vertices in ascending order
     * return "none" if there are no adjacent vertices
     * 
     * @param v
     */
    public String adjList(int v) {
        if (v >= size || v < 0) {
            return "none";
        }
        if (adj.get(v).size() == 0) {
            return "none";
        }
        int vertices[] = new int[adj.get(v).size()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = adj.get(v).get(i).vertex;
        }
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i; j < vertices.length; j++) {
                if (vertices[i] > vertices[j]) {
                    int temp = vertices[i];
                    vertices[i] = vertices[j];
                    vertices[j] = temp;
                }
            }
        }
        String list = "";
        for (int i = 0; i < vertices.length; i++) {
            list += String.valueOf(vertices[i]) + " ";
        }
        list.trim();
        return list;
    }

    /***
     * return the adjacency matrix representation of the graph
     * return a string double dimentional array containing the matrix representation
     * Example:
     * X 1.0 X 1.0 X
     * 1.0 X 1.0 1.0 X
     * X 1.0 X X 1.0
     * 1.0 1.0 X X 1.0
     * X X 1.0 1.0 X
     * X represents the vertex combinations where there are no edges
     */
    public String[][] matrix() {
        String adjMartix[][] = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (weight(i, j) != -1) {
                    // adjMartix[i][j] = String.format("%f", weight(i, j));
                    adjMartix[i][j] = "" + weight(i, j);
                } else {
                    adjMartix[i][j] = "X";
                }
            }
        }
        return adjMartix;
    }

    /***
     * return the adjacency matrix representation of the transitive closure of the
     * graph
     * Example:
     * 1 1 1 1
     * 1 1 1 1
     * 1 1 1 1
     * X X X 1
     * This will only be tested on directed graphs where edge weights are all one so
     * the
     * matrix should use "1" to signify reachability or "X" otherwise
     */
    public String[][] tclosure() {
        String transClosMatrix[][] = matrix();
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == j) {
                        transClosMatrix[i][j] = "1";
                    }
                    if ((transClosMatrix[i][j].compareTo("X") != 0) || ((transClosMatrix[i][k].compareTo("X") != 0)
                            && (transClosMatrix[k][j].compareTo("X") != 0))) {
                        transClosMatrix[i][j] = "1";
                    }
                }
            }
        }
        return transClosMatrix;
    }

    /***
     * print the shortest path from vertex u to vertex v
     * use any of the algorithms taught in the class
     * You need to return the shortest path in the following format
     * "4 6 2 0, 5.0"
     * Here 4 6 2 0 are vertices and 5.0 is the maximum edge weight
     * return "path does not exist" if there is no path"
     * You can assume that only one shortest path will exist for each pair of
     * vertices in a given graph.
     * In this section, no negative weight edges will be used in the test cases,
     * 
     * @param u
     * @param v
     */
    public String spath(int u, int v) {
        // dijkstra lessgooo
        // nevermind bellman ford lessgoo
        if (u >= size || v >= size || u < 0 || v < 0) {
            return "path does not exist";
        }
        double dist[] = new double[size];
        int prev[] = new int[size];
        dist[u] = 0;
        for (int i = 0; i < size; i++) {
            if (i != u) {
                dist[i] = Integer.MAX_VALUE;
            }
            prev[i] = -1;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < prev.length; k++) {
                    if (weight(j, k) != -1) {
                        if (dist[j] + weight(j, k) < dist[k]) {
                            dist[k] = weight(j, k) + dist[j];
                            prev[k] = j;
                        }
                    }
                }
            }
        }
        if (dist[v] == Integer.MAX_VALUE) {
            return "path does not exist";
        }
        int i = v;
        String path = "";
        while (i != -1) {
            path = i + " " + path;
            i = prev[i];
        }
        path += ", " + dist[v];
        return path;
    }

    /***
     * return any topological sorting of the graph
     * You need to return a string in the following format
     * "9 6 7 4 3 5 2 1 8 0"
     * Where each number is a vertex
     */
    public String tsort() {
        String order = "";
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vertices.add(i);
        }
        ArrayList<ArrayList<Node>> H = adj;
        while (vertices.size() != 0) {
            for (int i = 0; i < vertices.size(); i++) {
                if (H.get(vertices.get(i)).size() == 0) {
                    order = vertices.get(i) + " " + order;
                    for (int j = 0; j < H.size(); j++) {
                        for (int k = 0; k < H.get(j).size(); k++) {
                            if (H.get(j).get(k).vertex == vertices.get(i)) {
                                H.get(j).remove(k);
                            }
                        }
                    }
                    vertices.remove(i);
                    vertices.trimToSize();
                }
            }
        }
        return order.trim();
    }

    public void removeFromGraph(int v) {
        ArrayList<ArrayList<Node>> h = adj;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < h.size(); j++) {
                if (h.get(i).get(j).vertex == v) {
                    h.get(i).remove(j);
                }
            }
        }
    }

    /***
     * return true if the graph is strongly connected
     * otherwise false
     */
    public boolean sconn() {
        String tclos[][] = tclosure();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tclos[i][j].compareTo("X") == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * return the connected components of the graph
     * Each connected component is stored in a string separated by spaces
     * Return an array of strings in a sorted ordere
     */
    public String[] componentsDFS() {
        ArrayList<String> comps = new ArrayList<>();
        boolean visited[] = new boolean[size];

        for (int i = 0; i < size; i++) {
            visited[i] = false;
            globalVisited[i] = false;
        }
        String path = "";
        for (int i = 0; i < visited.length; i++) {
            if (!globalVisited[i]) {
                path = DFS(i, visited);
                path = path.trim();
                comps.add(path);
            }
        }
        String components[] = new String[comps.size()];
        for (int i = 0; i < components.length; i++) {
            components[i] = comps.get(i);
        }
        return components;
    }

    public String[] components() {
        String transClosMatrix[][] = tclosure();
        ArrayList<String> comps = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String path = "";
            for (int j = 0; j < size; j++) {
                if (transClosMatrix[i][j].compareTo("1") == 0) {
                    path += j + " ";
                }
            }
            if (!comps.contains(path)) {
                comps.add(path);
            }
        }
        String[] comp = new String[comps.size()];
        for (int i = 0; i < comp.length; i++) {
            comp[i] = comps.get(i);
        }
        return comp;

    }

    public String DFS(int v, boolean visited[]) {
        visited[v] = true;
        globalVisited[v] = true;
        String path = String.valueOf(v) + " ";
        for (int i = 0; i < adj.get(v).size(); i++) {
            if (!visited[adj.get(v).get(i).vertex]) {
                path += (DFS(adj.get(v).get(i).vertex, visited));
            }
        }
        return path;
    }

    /***
     * return true if the graph is a simple graph
     * otherwise false
     * 
     * @return
     */
    public boolean simple() {
        // self loop , double edge, acyclic
        // check the doubles edges
        if (doubleEdges) {
            return false;
        }
        // if undirected, check adjMatrix for self loop
        if (!isDirected) {
            String[][] adjMatrix = matrix();
            for (int i = 0; i < size; i++) {
                if (!(adjMatrix[i][i].compareTo("X") == 0)) {
                    return false;
                }
            }
        }

        // if directed, check selp loops and cycles using
        if (isDirected) {
            if (!noSelfLoop()) {
                return false;
            }
        }

        // check for cycles in undirected graph
        for (int i = 0; i < globalVisited.length; i++) {
            globalVisited[i] = false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < globalVisited.length; j++) {
                globalVisited[j] = false;
            }
            if (!DFSAcyclic(i, -1)) {
                return false;
            }
        }
        return true;

    }

    public boolean DFSAcyclic(int v, int parent) {
        globalVisited[v] = true;
        String[] adj = adjList(v).split(" ");
        for (int i = 0; i < adj.length; i++) {
            if (adj[i].compareTo("none") != 0) {
                int w = Integer.valueOf(adj[i]);
                if (!globalVisited[w]) {
                    return DFSAcyclic(w, v);
                } else if (w != parent) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean noSelfLoop() {
        // warshall
        String[][] transClosMatrix = matrix();
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if ((transClosMatrix[i][j].compareTo("X") != 0) || ((transClosMatrix[i][k].compareTo("X") != 0)
                            && (transClosMatrix[k][j].compareTo("X") != 0))) {
                        transClosMatrix[i][j] = "1";
                    }
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (transClosMatrix[i][i].compareTo("1") == 0) {
                return false;
            }
        }
        return true;
    }
}