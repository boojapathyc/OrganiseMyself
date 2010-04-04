package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;

public class TestCommon {

    public static ActivityNode getNewActivityNode() {
        return new ActivityNode(new RequestData(), new PageData());
    }
}