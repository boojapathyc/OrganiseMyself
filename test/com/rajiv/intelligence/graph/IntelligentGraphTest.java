package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntelligentGraphTest {
    private IntelligentGraph intelligentGraph;

    @Before
    public void setUp() {
        intelligentGraph = new IntelligentGraph("index", 0L);
    }

    @Test
    public void createDirectedGraph() {
        assertNotNull("graph is empty dog", intelligentGraph.getDirectedGraph());
        assertNotNull(intelligentGraph.getRootNode());
    }

    @Test
    public void shouldAddToRootNodeWhenSourceIsNull() {
        addAndVerifyAddition(null);
    }

    @Test
    public void shouldNotAddSourceNodeWhenItAlreadyExists() {
        ActivityNode sourceNode = TestCommon.getNewActivityNode();
        intelligentGraph.addNode(null, sourceNode);
        addAndVerifyAddition(sourceNode);
    }

    @Test
    public void shouldNotDuplicateVertex() {
        ActivityNode visited = TestCommon.getNewActivityNode();
        ActivityNode source = new ActivityNode(new RequestData(), new PageData());

        intelligentGraph.addNode(source, visited);
        assertTrue(intelligentGraph.containsVertex(visited));

        int initialCount = edgeCount(source);
        intelligentGraph.addNode(source, visited);

        assertTrue(intelligentGraph.containsVertex(visited));
        assertEquals(initialCount, edgeCount(source));
    }

    private void addAndVerifyAddition(ActivityNode source) {
        addAndVerifyAddition(source, TestCommon.getNewActivityNode());
    }

    private void addAndVerifyAddition(ActivityNode source, ActivityNode visited) {
        int initialCount = edgeCount(source);
        intelligentGraph.addNode(source, visited);
        assertTrue(intelligentGraph.containsVertex(visited));
        assertEquals(initialCount + 1, edgeCount(source));
    }

    private int edgeCount(ActivityNode source) {
        ActivityNode sourceToCount = (source != null) ? source : intelligentGraph.getRootNode();
        return intelligentGraph.getOutEdges(sourceToCount).size();
    }

    @Test
    public void shouldIncrementNavigationPathWeight() {
        ActivityNode visited = TestCommon.getNewActivityNode();
        ActivityNode source = new ActivityNode(new RequestData(), new PageData());
        Integer navigationCount = 5;
        for (int i = 0; i < navigationCount; i++) {
            intelligentGraph.addNode(source, visited);
        }
        assertEquals(navigationCount, intelligentGraph.getWeight(source, visited));
    }
}