package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class IntelligentGraph {

    private DirectedGraph<ActivityNode, NavigationPath> directedGraph;
    private ActivityNode rootNode;
    private Long thresholdNavigationsForContentTruncation;
    private Integer thresholdWeightForContentTruncation;


    public IntelligentGraph(String rootNodeName, Long thresholdNavigationsForContentTruncation, Integer thresholdWeightForContentTruncation) {
        this.thresholdWeightForContentTruncation = thresholdWeightForContentTruncation;
        this.directedGraph = new DirectedSparseGraph<ActivityNode, NavigationPath>();
        this.thresholdNavigationsForContentTruncation = thresholdNavigationsForContentTruncation;
        rootNode = new ActivityNode(new RequestData(rootNodeName), new PageData("Home", "images/home.jpeg"), thresholdNavigationsForContentTruncation, thresholdWeightForContentTruncation);
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

    public void incrementWeight(ActivityNode source, ActivityNode visited) {
        NavigationPath navigationPath = directedGraph.findEdge(source, visited);
        if (navigationPath != null) {
            navigationPath.incrementWeight();
            ActivityNode destinationNode = getDestination(navigationPath);
            System.out.println("incrementing weight on " + destinationNode.getRequestData().getId() + " " + destinationNode.getNoOfNavigations());
            destinationNode.recordNavigation();
        }
    }

    public Collection<ActivityNode> sortDestinationsOnEdgeWeight(ActivityNode sourceNode) {

        Collection<NavigationPath> sortedEdges = sort(directedGraph.getOutEdges(sourceNode),
                on(NavigationPath.class).getWeight());

        List<ActivityNode> sortedNodes = new ArrayList<ActivityNode>();
        ActivityNode actualSource = getVertexFromGraph(sortedEdges);
        boolean shouldTruncateContent = actualSource.isNavigatedAboveThreshold();
        System.out.println("source node: " + actualSource.getRequestData().getId() + "  " + actualSource.getNoOfNavigations() + " shouldTruncate: " + shouldTruncateContent);
        for (NavigationPath edge : sortedEdges) {
            ActivityNode activityNode = directedGraph.getDest(edge);
            System.out.println("creating sorted list: " + shouldTruncateContent + " " + edge.getWeight());
            sortedNodes.add(activityNode.getProcessedContent(shouldTruncateContent, edge.getWeight()));
        }
        Collections.reverse(sortedNodes);
        return sortedNodes;
    }

    public Long getThresholdNavigationsForContentTruncation() {
        return thresholdNavigationsForContentTruncation;
    }

    public ActivityNode getVertexFromGraph(Collection<NavigationPath> sortedEdges) {
        if (sortedEdges.size() > 0) {
            return directedGraph.getSource((NavigationPath) sortedEdges.toArray()[0]);
        }
        return null;
    }

    public Integer getThresholdWeightForContentTruncation() {
        return thresholdWeightForContentTruncation;
    }
}