package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GraphAnalyzerTest {

    private IntelligentGraph intelligentGraph;

    @Before
    public void setUp() {
        intelligentGraph = new IntelligentGraph("index", 0L, 0);
    }

    @Test
    public void shouldReturnTopNavigatedPathsForVertex() {
        ActivityNode source = new ActivityNode(new RequestData(), new PageData(), 10L, 0);

        ActivityNode visited_1 = TestCommon.getNewActivityNode();
        ActivityNode visited_2 = TestCommon.getNewActivityNode();
        ActivityNode visited_3 = TestCommon.getNewActivityNode();
        ActivityNode visited_4 = TestCommon.getNewActivityNode();

        Integer expectedVisited_1Max = 4;
        Integer expectedVisited_2Max = 7;
        Integer expectedVisited_3Max = 45;
        Integer expectedVisited_4Max = 15;

        addPath(source, visited_1, 1);
        addPath(visited_1, visited_2, expectedVisited_1Max);
        addPath(visited_2, visited_3, 5);

        addPath(visited_2, visited_4, expectedVisited_2Max);
        addPath(visited_3, visited_4, 3);
        addPath(visited_3, visited_1, 9);
        addPath(visited_3, visited_2, expectedVisited_3Max);
        addPath(visited_4, visited_2, expectedVisited_4Max);


        GraphAnalyzer analyzer = new GraphAnalyzer();
        assertEquals(expectedVisited_1Max, analyzer.getMaxNavigationPath(intelligentGraph, visited_1).getWeight());
        assertEquals(expectedVisited_2Max, analyzer.getMaxNavigationPath(intelligentGraph, visited_2).getWeight());
        assertEquals(expectedVisited_3Max, analyzer.getMaxNavigationPath(intelligentGraph, visited_3).getWeight());
        assertEquals(expectedVisited_4Max, analyzer.getMaxNavigationPath(intelligentGraph, visited_4).getWeight());
        ActivityNode node = visited_1;
        for (int i = 0; i < 2; i++) {
            node = intelligentGraph.getDestination(analyzer.getMaxNavigationPath(intelligentGraph, node));
        }
        assertEquals(expectedVisited_4Max, analyzer.getMaxNavigationPath(intelligentGraph, node).getWeight());
    }

    private void addPath(ActivityNode source, ActivityNode visited, int count) {
        for (int i = 0; i < count; i++) {
            intelligentGraph.addNode(source, visited);
        }
    }
}
