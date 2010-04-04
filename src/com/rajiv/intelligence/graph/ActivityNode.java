package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;

public class ActivityNode {
    private RequestData data;
    private String content;
    private String displayName;

    public ActivityNode(RequestData data, PageData pageData) {
        this.data = data;
        this.content = pageData.getContent();
        this.displayName = pageData.getName();
    }

    public ActivityNode(RequestData data) {
        this(data, new PageData());
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

    public String getContent() {
        return content;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ActivityNode makeCopyWithoutContent() {
        return new ActivityNode(data, new PageData(displayName, null));
    }
}
