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
        return new ActivityNode(new RequestData(page), pageData, intelligentGraph.getThresholdNavigationsForContentTruncation(), intelligentGraph.getThresholdWeightForContentTruncation());
    }

    private ActivityNode activityNode(String page) {
        return new ActivityNode(new RequestData(page), new PageData(), intelligentGraph.getThresholdNavigationsForContentTruncation(), intelligentGraph.getThresholdWeightForContentTruncation());
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
            ActivityNode node = intelligentGraph.getDestination(path);
            System.out.println("page i would go to" + node.getRequestData().getId() +
                    " weight:" + path.getWeight() + " no of Navigations on visited Node: " + node.getNoOfNavigations());
            return intelligentGraph.getDestination(path).getRequestData().getId();
        }

        return null;
    }

    public Map<String, ActivityNode> createTargetPathsFromGraph(String currentPage) {
        ActivityNode sourceNode = activityNode(currentPage);
        Collection<ActivityNode> sortedTargetNodes = intelligentGraph.sortDestinationsOnEdgeWeight(sourceNode);
        Map<String, ActivityNode> sortedTargetWithContent = new LinkedHashMap<String, ActivityNode>();
        for (ActivityNode targetNode : sortedTargetNodes) {
            sortedTargetWithContent.put(targetNode.getName(), targetNode);
        }
        return sortedTargetWithContent;
    }
}