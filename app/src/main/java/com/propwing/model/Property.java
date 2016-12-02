package com.propwing.model;

import com.propwing.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public class Property {
    private String agentName;
    private String agentPictureUrl;
    private List<Item> items = new ArrayList<>();

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPictureUrl() {
        return agentPictureUrl;
    }

    public void setAgentPictureUrl(String agentPictureUrl) {
        this.agentPictureUrl = agentPictureUrl;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
