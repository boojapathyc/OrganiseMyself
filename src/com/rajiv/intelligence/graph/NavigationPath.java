package com.rajiv.intelligence.graph;

public class NavigationPath {
    private Integer weight;
    private String name;

    public NavigationPath(String name) {
        this.name = name;
        this.weight = 0;
    }

    public Integer getWeight() {
        return weight;
    }

    public void incrementWeight() {
        this.weight++;
    }
}
