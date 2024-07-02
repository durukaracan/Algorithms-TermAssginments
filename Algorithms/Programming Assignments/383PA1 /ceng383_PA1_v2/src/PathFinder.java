//CENG 383 - PA1
// Duru Karacan 202128022

//PathFinder Class has methods for find the shortest path using BFS algorithm for Knight to reach Gold, print the shortest path.


import java.util.LinkedList;
import java.util.Queue;

class PathFinder {
    private Graph graph;
    private int[] edgeTo; // stores the index of the previous vertex on the shortest path to each vertex.
    private boolean[] visited;   //array that holds whether nodes have been visited or not

    // constructor
    public PathFinder(Graph graph) { //for PathFinder Object we took a graph to be a parameter
        this.graph = graph;
        this.edgeTo = new int[graph.V()]; //'edgeTo' array of the same size as the # of V in the graph
        this.visited = new boolean[graph.V()];
    }


    // BFS algorithm
    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            int v = queue.remove();

            for (int w : graph.adj(v)) { // for each : iterates over the adjacent of the removed node

                if (!visited[w]) { // if the neighbor node has NOT been visited,
                    edgeTo[w] = v; // records how we reached this node
                    visited[w] = true; // marks the adjacent node as visited
                    queue.add(w); // adds the adjacent node to the queue
                }
            }
        }
    }

    //finds shortest path using BFS
    public LinkedList<Integer> findShortestPath() {
        bfs(graph.getStartNode());  //calls bfs method

        // if the end node cannot be reached, return null and print method will print: "No path to the target." ;
        if (!visited[graph.getEndNode()]) {
            return null;
        }

        // constructs the shortest path
        LinkedList<Integer> path = new LinkedList<>();
        for (int x = graph.getEndNode(); x != graph.getStartNode(); x = edgeTo[x]) {
            path.addFirst(x);
        }
        path.addFirst(graph.getStartNode()); // adds the start node to the path

        return path;
    }


    //method for printing the path
    public void printPath(LinkedList<Integer> path) {
        if (path == null) {
            System.out.println("No path to the target.");
            return;
        }

        int rows = (int) Math.sqrt(graph.V()); // calculates the number of rows in the board
        System.out.println(path.size() - 1 + " steps");

        // prints the path from start to end nodes
        System.out.print("Path from c(" + (path.getFirst() / rows + 1) + "," + (path.getFirst() % rows + 1) +
                ") to c(" + (path.getLast() / rows + 1) + "," + (path.getLast() % rows + 1) + ") : ");

        System.out.println();

        for (int step : path) {
            System.out.print("c(" + (step / rows + 1) + "," + (step % rows + 1) + ") -> ");
        }
        System.out.println();
    }

}