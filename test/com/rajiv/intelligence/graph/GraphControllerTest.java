package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class GraphControllerTest {

    @Test
    public void testCreateVertices() {
        IntelligentGraph intelligentGraph = new IntelligentGraph("index", 0L);
        Map<String, PageData> pages = new HashMap<String, PageData>() {
            {
                put("a", new PageData("abcd", "abcd"));
                put("b", new PageData("abcd", "abcd"));
                put("c", new PageData("abcd", "abcd"));
            }
        };
        GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
        GraphController graphController = new GraphController(intelligentGraph, graphAnalyzer, pages);
        graphController.createVertices();
        assertEquals(4, intelligentGraph.getDirectedGraph().getVertexCount());
        assertEquals(12, intelligentGraph.getDirectedGraph().getEdgeCount());
    }
}
