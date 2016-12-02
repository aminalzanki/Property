package com.propwing.service.event;

import com.propwing.model.Property;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public class OnPropertyEvent extends OnDefaultEvent {
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
