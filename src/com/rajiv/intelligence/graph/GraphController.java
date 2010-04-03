package com.rajiv.intelligence.graph;

import java.util.Collection;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.CoreMatchers.not;

public class GraphController {

    private IntelligentGraph intelligentGraph;
    private GraphAnalyzer graphAnalyzer;
    private List<String> pages;

    public GraphController(IntelligentGraph intelligentGraph, GraphAnalyzer graphAnalyzer, List<String> pages) {
        this.intelligentGraph = intelligentGraph;
        this.graphAnalyzer = graphAnalyzer;
        this.pages = pages;
        createVertices();
    }

    void createVertices() {
        for (String sourcePage : pages) {
            ActivityNode sourceNode = activityNode(sourcePage);
            intelligentGraph.addNode(null, sourceNode);
            intelligentGraph.addNode(sourceNode, null);
            List<String> targetPages = filter(not(sourcePage), pages);
            for (String targetPage : targetPages) {
                intelligentGraph.addNode(sourceNode, activityNode(targetPage));
            }
        }
    }

    private ActivityNode activityNode(String page) {
        return new ActivityNode(new RequestData(page));
    }

    public String maxNavigatedPage(String sourcePage, String currentPageId) {

        ActivityNode sourceNode = null;
        if (sourcePage != null) {
            sourceNode = activityNode(sourcePage);
        }
        ActivityNode visited = activityNode(currentPageId);
        intelligentGraph.incrementWeight(sourceNode, visited);
        NavigationPath path = graphAnalyzer.getMaxNavigationPath(intelligentGraph, visited);
        if (path != null) {
            System.out.println("page i would go to" + intelligentGraph.getDestination(path).getRequestData().getId() +
                    " :" + path.getWeight());
            return intelligentGraph.getDestination(path).getRequestData().getId();
        }
        return null;
    }

    public List<String> createTargetPathsFromGraph(String currentPage) {
        ActivityNode sourceNode = activityNode(currentPage);
        Collection<ActivityNode> targetNodes = intelligentGraph.getDestinations(sourceNode);
        return extract(targetNodes, on(ActivityNode.class).getName());
    }
}