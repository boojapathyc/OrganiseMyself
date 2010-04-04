package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.*;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class IntelligentGraph {

    private DirectedGraph<ActivityNode, NavigationPath> directedGraph;
    private ActivityNode rootNode;
    private Long noOfNavigations;
    private Long thresholdNavigationsForContentTruncation;

    public IntelligentGraph(String rootNodeName, Long thresholdNavigationsForContentTruncation) {
        this.directedGraph = new DirectedSparseGraph<ActivityNode, NavigationPath>();
        this.thresholdNavigationsForContentTruncation = thresholdNavigationsForContentTruncation;
        noOfNavigations = 0L;
        rootNode = new ActivityNode(new RequestData(rootNodeName), new PageData("Home", "images/home.jpeg"));
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
        noOfNavigations++;
        if (navigationPath != null) {
            navigationPath.incrementWeight();
        }
    }

    public Collection<ActivityNode> sortDestinationsOnEdgeWeight(ActivityNode sourceNode) {

        Collection<NavigationPath> sortedEdges = sort(directedGraph.getOutEdges(sourceNode),
                on(NavigationPath.class).getWeight());

        List<ActivityNode> sortedNodes = new ArrayList<ActivityNode>();

        for (NavigationPath edge : sortedEdges) {
            sortedNodes.add(directedGraph.getDest(edge));
        }
        Collections.reverse(sortedNodes);
        return sortedNodes;
    }

    public ActivityNode getProcessedNode(ActivityNode sourceNode, ActivityNode targetNode, int targetNodeIndex) {
        ActivityNode copiedNode = targetNode;
        if (noOfNavigations >= thresholdNavigationsForContentTruncation) {
            if (getWeight(sourceNode, targetNode) == 0) {
                copiedNode = targetNode.makeCopyWithoutContent();
            }
        }
        return copiedNode;
    }
}