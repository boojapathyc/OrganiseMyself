package com.rajiv.intelligence.graph;

import com.rajiv.model.PageData;

public class ActivityNode {
    private RequestData data;
    private Long thresholdNavigationsForContentTruncation;
    private String content;
    private String displayName;
    private Long noOfNavigations = 0L;
    private Integer thresholdWeightForContentTruncation = 0;

    public ActivityNode(RequestData data, PageData pageData, Long thresholdNavigationsForContentTruncation, Integer thresholdWeightForContentTruncation) {
        this.data = data;
        this.thresholdNavigationsForContentTruncation = thresholdNavigationsForContentTruncation;
        this.thresholdWeightForContentTruncation = thresholdWeightForContentTruncation;
        this.content = pageData.getContent();
        this.displayName = pageData.getName();
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
        return new ActivityNode(data, new PageData(displayName, null), thresholdNavigationsForContentTruncation, thresholdWeightForContentTruncation);
    }

    public void recordNavigation() {
        noOfNavigations++;
    }


    public ActivityNode getProcessedContent(boolean shouldTruncateContent, Integer pathWeight) {
        if (shouldTruncateContent && pathWeight <= thresholdWeightForContentTruncation)
            return makeCopyWithoutContent();

        return this;
    }

    public boolean isNavigatedAboveThreshold() {
        return noOfNavigations >= thresholdNavigationsForContentTruncation;
    }

    public Long getNoOfNavigations() {
        return noOfNavigations;
    }
}