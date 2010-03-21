package com.rajiv.intelligence.graph;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.Collection;

public class IntelligentGraph {

    private DirectedGraph<ActivityNode, NavigationPath> directedGraph;
    private ActivityNode rootNode;

    public IntelligentGraph() {
        this.directedGraph = new DirectedSparseGraph<ActivityNode, NavigationPath>();
        rootNode = new ActivityNode();
        directedGraph.addVertex(rootNode);
    }

    public DirectedGraph getDirectedGraph() {
        return directedGraph;
    }

    public void addNode(ActivityNode source, ActivityNode visited) {
        ActivityNode sourceToConnect = source;

        if (sourceToConnect == null) {
            sourceToConnect = rootNode;
        }

        if (!containsVertex(visited)) {
            directedGraph.addVertex(visited);
        }

        addNavigationPath(sourceToConnect, visited);

    }

    private void addNavigationPath(ActivityNode source, ActivityNode visited) {
        NavigationPath navigationPath = directedGraph.findEdge(source, visited);

        if (navigationPath == null) {
            navigationPath = new NavigationPath();
            directedGraph.addEdge(navigationPath, source, visited);
        }

        navigationPath.incrementWeight();
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

    public ActivityNode getDest(NavigationPath path) {
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
}