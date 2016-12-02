package com.propwing.model;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */
public class ResponseError {
    private int code;
    private String info;

    public ResponseError() {
    }

    public int code() {
        return code;
    }

    public String info() {
        return info;
    }
}
