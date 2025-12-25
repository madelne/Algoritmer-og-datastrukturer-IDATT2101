package org.example;

import java.io.*;
import java.util.*;


class Node {
  int value;
  Node next;

  Node(int value) {
    this.value = value;
    this.next = null;
  }
}

public class Graph {
  int amtNode;
  int amtEdge;
  Node[] table;

  Graph(int amtNode, int amtEdge) {
    this.amtNode = amtNode;
    this.amtEdge = amtEdge;
    this.table = new Node[amtNode];
    Arrays.fill(this.table, null);
  }

  void addEdge(int from, int to) {
    Node newNode = new Node(to);
    newNode.next = table[from];
    table[from] = newNode;
  }
}

class Main {

  // Read graph from a file
  public static Graph readFile(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String[] firstLine = reader.readLine().split("\\s+");
    int amtNode = Integer.parseInt(firstLine[0]);
    int amtEdge = Integer.parseInt(firstLine[1]);
    System.out.println("noder: " + amtNode + ", kanter: " + amtEdge);

    Graph g = new Graph(amtNode, amtEdge);

    for (int i = 0; i < amtEdge; i++) {
      String line = reader.readLine();
      if (line == null || line.trim().isEmpty()) {
        continue; // Skip empty lines
      }
      String[] edge = line.split("\\s+");
      if (edge.length < 2) {
        continue; // Skip lines that do not have enough elements
      }
      int from = Integer.parseInt(edge[0]);
      int to = Integer.parseInt(edge[1]);
      g.addEdge(from, to);
    }

    reader.close();
    return g;
  }

  // Reverse the graph
  public static Graph reverseGraph(Graph graph) {
    Graph reversedGraph = new Graph(graph.amtNode, graph.amtEdge);
    for (int i = 0; i < graph.amtNode; i++) {
      Node adjList = graph.table[i];
      while (adjList != null) {
        reversedGraph.addEdge(adjList.value, i);
        adjList = adjList.next;
      }
    }
    return reversedGraph;
  }

  // DFS algorithm
  private static void dfs(Node[] table, int start, boolean[] visited, List<Integer> order) {
    visited[start] = true;
    order.add(start);
    Node adjList = table[start];
    while (adjList != null) {
      if (!visited[adjList.value]) {
        dfs(table, adjList.value, visited, order);
      }
      adjList = adjList.next;
    }
    order = insertFront(order, start);
  }

  public static void printSSK(Graph g) {
    List<Integer> order = new ArrayList<>();
    boolean[] visited = new boolean[g.amtNode];

    for (int i = 0; i < g.amtNode; i++) {
      if (!visited[i]) {
        dfs(g.table, i, visited, order);
      }
    }

    Graph reversedG = reverseGraph(g);
    Arrays.fill(visited, false);

    for (int i = order.size() - 1; i >= 0; i--) {
      int currentNode = order.get(i);
      if (!visited[currentNode]) {
        List<Integer> SSK = new ArrayList<>();
        dfs(reversedG.table, currentNode, visited, SSK);
        for (int node : SSK) {
          System.out.print(node + " ");
        }
        System.out.println();
      }
    }
  }

  public static void main(String[] args) {
    try {
      System.out.println("File: ø5g1.txt");
      Graph g = readFile("ø5g1.txt");
      printSSK(g);
    } catch (IOException e) {
      System.err.println("Could not read the file: " + e.getMessage());
    }
    try {
      System.out.println("File: ø5g2.txt");
      Graph g = readFile("ø5g2.txt");
      printSSK(g);
    } catch (IOException e) {
      System.err.println("Could not read the file: " + e.getMessage());
    }
    try {
      System.out.println("File: ø5g5.txt");
      Graph g = readFile("ø5g5.txt");

      printSSK(g);
    } catch (IOException e) {
      System.err.println("Could not read the file: " + e.getMessage());
    }
    try {
      System.out.println("File: ø5g6.txt");
      Graph g = readFile("ø5g6.txt");

      printSSK(g);
    } catch (IOException e) {
      System.err.println("Could not read the file: " + e.getMessage());
    }
  }
}