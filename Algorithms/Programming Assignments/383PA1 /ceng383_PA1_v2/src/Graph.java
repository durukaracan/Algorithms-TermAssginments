//CENG 383 - PA1
// Duru Karacan 202128022

// Graph Class creates a graph with the information it reads from the PA1_test.txt
// Determines the position of Knight node, Gold node and Tree nodes
// and with under some constraints defines the edges of graph

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private final int V; // number of vertices
    private int E; // number of edges
    private List<Integer>[] adj; // adj is an array that stores the list of adjacent(neighbor) nodes for each node
    private int startNode;
    private int endNode;
    private boolean[] trees; // a boolean array that indicates whether each node is a tree



    // constructor
    public Graph(int V) {
        this.V = V; // assigns the number of nodes of the graph
        this.E = 0; // initializes the number of edges to 0
        adj = (List<Integer>[]) new List[V]; // initializes the adj array to create a list of adjacent nodes for each node

        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
        trees = new boolean[V];
    }


    //If v and w are NOT tree, then method add undirected edge between v and w
    public void addEdge(int v, int w) {
        if (!trees[v] && !trees[w]) {
            adj[v].add(w);  //add edges in both
            adj[w].add(v);  //add edges in both
            E++;
        }
    }


    //returns nodes adjacent to a given node
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }


    //return num of nodes
    public int V() {
        return V;
    }

    //getter methods
    public int getStartNode() {return startNode;}
    public int getEndNode() {return endNode;}



    public static void main(String[] args) {
        String inputFileName = "PA1_test.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String[] dimensions = reader.readLine().split(" ");

            //parse into integers according to the file
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            Graph graph = new Graph(rows * cols);

            // determines the start(K), end(G) and tree(T) nodes
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();

                for (int j = 0; j < cols; j++) {
                    char c = line.charAt(j);
                    int nodeIndex = i * cols + j;

                    if (c == 'K') {
                        graph.startNode = nodeIndex;
                    } else if (c == 'G') {
                        graph.endNode = nodeIndex;
                    } else if (c == 'T') {
                        graph.trees[nodeIndex] = true;
                    }

                }
            }

            //adds edges for each non-tree node based on the Knight's movements
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!graph.trees[i * cols + j]) {
                        addKnightMovesAsEdges(graph, i, j, rows, cols);
                    }
                }
            }

            //create a PathFinder object to find and print the shortest path of the Knight
            PathFinder pathFinder = new PathFinder(graph);
            pathFinder.printPath(pathFinder.findShortestPath());


        } catch (IOException e) {
            System.out.println("file read error: " + e.getMessage());
        }
    }

    // adds all edges to the graph that the knight can move from a given(starting) position
    private static void addKnightMovesAsEdges(Graph graph, int i, int j, int rows, int cols) {

        //possible knight moves, we kept in 2D array
        int[][] moves = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};

        for (int[] move : moves) { // for each movement
            int newRow = i + move[0];
            int newCol = j + move[1];

            //We check if the new position is within the boundaries of the board
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                // new position is legal, add edge
                graph.addEdge(i * cols + j, newRow * cols + newCol);
            }
        }
    }
}