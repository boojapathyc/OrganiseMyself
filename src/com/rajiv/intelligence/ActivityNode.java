package com.rajiv.intelligence;

public class ActivityNode {
    private RequestData data;

    public ActivityNode(RequestData data) {
        //To change body of created methods use File | Settings | File Templates.
        this.data = data;
    }

    public ActivityNode() {
        //To change body of created methods use File | Settings | File Templates.
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
}
