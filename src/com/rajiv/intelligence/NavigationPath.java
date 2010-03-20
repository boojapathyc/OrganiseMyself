package com.rajiv.intelligence;

public class NavigationPath {
    private Integer weight;

    public NavigationPath() {
        this.weight = 0;
    }

    public Integer getWeight() {
        return weight;
    }

    public void incrementWeight() {
        this.weight++;
    }
}
