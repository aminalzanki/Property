package com.propwing.model;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public class Item {
    private String propertyTitle;
    private String detailsSiteUrl;
    private String shortenerSiteUrl;
    private String shortenerStatUrl;
    private String summaryText;

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public String getDetailsSiteUrl() {
        return detailsSiteUrl;
    }

    public void setDetailsSiteUrl(String detailsSiteUrl) {
        this.detailsSiteUrl = detailsSiteUrl;
    }

    public String getShortenerSiteUrl() {
        return shortenerSiteUrl;
    }

    public void setShortenerSiteUrl(String shortenerSiteUrl) {
        this.shortenerSiteUrl = shortenerSiteUrl;
    }

    public String getShortenerStatUrl() {
        return shortenerStatUrl;
    }

    public void setShortenerStatUrl(String shortenerStatUrl) {
        this.shortenerStatUrl = shortenerStatUrl;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }
}
