package com.rajiv.intelligence.graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class GraphControllerTest {

    @Test
    public void testCreateVertices() {
        IntelligentGraph intelligentGraph = new IntelligentGraph("index");
        Map<String, String> pages = new HashMap<String, String>() {
            {
                put("a", "abcd");
                put("b", "abcd");
                put("c", "abcd");
            }
        };
        GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
        GraphController graphController = new GraphController(intelligentGraph, graphAnalyzer, pages);
        graphController.createVertices();
        assertEquals(4, intelligentGraph.getDirectedGraph().getVertexCount());
        assertEquals(12, intelligentGraph.getDirectedGraph().getEdgeCount());
    }
}
