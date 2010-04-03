package com.rajiv.intelligence.graph;

public class ActivityNode {
    private RequestData data;

    public ActivityNode(RequestData data) {
        this.data = data;
    }

    public ActivityNode() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityNode that = (ActivityNode) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    public RequestData getRequestData() {
        return data;
    }

    public String getName() {
        return data.getId();
    }
}
