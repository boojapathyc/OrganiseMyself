package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.filter;
import static org.hamcrest.CoreMatchers.not;

public class GraphController {

    private IntelligentGraph intelligentGraph;
    private GraphAnalyzer graphAnalyzer;
    private Map<String, PageData> pages;

    public GraphController(IntelligentGraph intelligentGraph, GraphAnalyzer graphAnalyzer, Map<String, PageData> pages) {
        this.intelligentGraph = intelligentGraph;
        this.graphAnalyzer = graphAnalyzer;
        this.pages = pages;
        createVertices();
    }

    void createVertices() {
        for (String sourcePage : pages.keySet()) {
            ActivityNode sourceNode = activityNode(sourcePage, pages.get(sourcePage));
            intelligentGraph.addNode(null, sourceNode);
            intelligentGraph.addNode(sourceNode, null);
            List<String> targetPages = filter(not(sourcePage), pages.keySet());
            for (String targetPage : targetPages) {
                intelligentGraph.addNode(sourceNode, activityNode(targetPage, pages.get(targetPage)));
            }
        }
    }

    private ActivityNode activityNode(String page, PageData pageData) {
        return new ActivityNode(new RequestData(page), pageData);
    }

    private ActivityNode activityNode(String page) {
        return new ActivityNode(new RequestData(page), new PageData());
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

    public Map<String, ActivityNode> createTargetPathsFromGraph(String currentPage) {
        ActivityNode sourceNode = activityNode(currentPage);
        Collection<ActivityNode> sortedTargetNodes = intelligentGraph.sortDestinationsOnEdgeWeight(sourceNode);
        Map<String, ActivityNode> sortedTargetWithContent = new LinkedHashMap<String, ActivityNode>();
        int targetNodeIndex = 0;
        for (ActivityNode targetNode : sortedTargetNodes) {
            targetNodeIndex++;
            sortedTargetWithContent.put(targetNode.getName(), intelligentGraph.getProcessedNode(sourceNode, targetNode, targetNodeIndex));
        }
        return sortedTargetWithContent;
    }
}