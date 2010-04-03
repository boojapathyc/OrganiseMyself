package com.rajiv.intelligence.graph;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class IntelligentGraph {

    private DirectedGraph<ActivityNode, NavigationPath> directedGraph;
    private ActivityNode rootNode;

    public IntelligentGraph(String rootNodeName) {
        this.directedGraph = new DirectedSparseGraph<ActivityNode, NavigationPath>();
        rootNode = new ActivityNode(new RequestData(rootNodeName));
        directedGraph.addVertex(rootNode);
    }

    public DirectedGraph getDirectedGraph() {
        return directedGraph;
    }

    public void addNode(ActivityNode source, ActivityNode visited) {
        if (source == null) {
            source = rootNode;
        }

        if (visited == null) {
            visited = rootNode;
        }

        if (!containsVertex(visited)) {
            directedGraph.addVertex(visited);
        }

        addNavigationPath(source, visited);
    }

    private void addNavigationPath(ActivityNode source, ActivityNode visited) {
        NavigationPath navigationPath = directedGraph.findEdge(source, visited);
        if (navigationPath == null) {
            navigationPath = new NavigationPath(source.getName() + " to " + visited.getName());
            directedGraph.addEdge(navigationPath, source, visited);
        }
    }

    public Boolean containsVertex(ActivityNode activityNode) {
        return this.directedGraph.containsVertex(activityNode);
    }

    public ActivityNode getRootNode() {
        return rootNode;
    }

    public Collection<NavigationPath> getOutEdges(ActivityNode node) {
        return this.directedGraph.getOutEdges(node);
    }

    public Collection<ActivityNode> getDestinations(ActivityNode source) {
        Set<ActivityNode> targetNodes = new HashSet<ActivityNode>();
        for (NavigationPath path : directedGraph.getOutEdges(source)) {
            targetNodes.add(directedGraph.getDest(path));
        }
        return targetNodes;
    }

    public ActivityNode getDestination(NavigationPath path) {
        return this.directedGraph.getDest(path);
    }

    public Integer getWeight(ActivityNode source, ActivityNode visited) {
        Integer weight = 0;
        NavigationPath edge = this.directedGraph.findEdge(source, visited);
        if (edge != null) {
            weight = edge.getWeight();
        }
        return weight;
    }

    public Collection<ActivityNode> getVertices() {
        return directedGraph.getVertices();
    }

    public void incrementWeight(ActivityNode source, ActivityNode visited) {
        NavigationPath navigationPath = directedGraph.findEdge(source, visited);

        if (navigationPath != null) {
            navigationPath.incrementWeight();
        }
    }
}