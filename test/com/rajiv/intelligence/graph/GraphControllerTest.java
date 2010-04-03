package com.rajiv.intelligence.graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class GraphControllerTest {

    @Test
    public void testCreateVertices() {
        IntelligentGraph intelligentGraph = new IntelligentGraph("index");
        List<String> pages = Arrays.asList("a", "b", "c");
        GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
        GraphController graphController = new GraphController(intelligentGraph, graphAnalyzer, pages);
        graphController.createVertices();
        assertEquals(4, intelligentGraph.getDirectedGraph().getVertexCount());
        assertEquals(6, intelligentGraph.getDirectedGraph().getEdgeCount());
    }
}
