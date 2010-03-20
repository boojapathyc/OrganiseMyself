package com.rajiv.intelligence;

import java.util.Collection;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectMax;

public class GraphAnalyzer {
    public NavigationPath getMaxNavigationPath(IntelligentGraph intelligentGraph, ActivityNode node) {

        if (intelligentGraph.containsVertex(node)) {
            Collection<NavigationPath> navigationPaths = intelligentGraph.getOutEdges(node);
            return selectMax(navigationPaths, on(NavigationPath.class).getWeight());
        }

        return null;
    }
}
