package com.propwing.service.event;

import com.propwing.model.ResponseError;
import com.propwing.service.HttpStatus;

import retrofit2.Response;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */
public class OnDefaultEvent {
    private Object mObject;
    private Integer code;
    private String info;

    public OnDefaultEvent(final Object object) {
        mObject = object;
    }

    public OnDefaultEvent() {
    }

    public Object getObject() {
        return mObject;
    }

    public void setSuccess() {
        code = HttpStatus.SC_OK;
    }

    public void setFail() {
        code = HttpStatus.SC_BAD_REQUEST;
    }

    public void setUnauthorized() {
        code = HttpStatus.SC_FORBIDDEN;
    }

    public void setResponse(final Response response) {
        if (response != null) {
            code = response.code();
            info = response.message();
        }
    }

    public void setResponseError(final ResponseError error) {
        code = error == null ? 0 : error.code();
        info = error == null ? "" : error.info();
    }

    public boolean isSuccess() {
        if (code != null) {
            switch (code) {
                case HttpStatus.SC_OK:
                case HttpStatus.SC_ACCEPTED:
                case HttpStatus.SC_NO_CONTENT:
                    return true;
            }
        }
        return false;
    }

    public boolean isUnprocessableEntity() {
        if (code != null) {
            switch (code) {
                case HttpStatus.SC_UNPROCESSABLE_ENTITY:
                    return true;
            }
        }
        return false;
    }

    public boolean isAuthorizationError() {
        if (code != null) {
            switch (code) {
                case HttpStatus.SC_UNAUTHORIZED:
                case HttpStatus.SC_FORBIDDEN:
                    return true;
            }
        }

        return false;
    }

    public String getInfo() {
        return info;
    }

    public Integer getHttpStatus() {
        return code;
    }
}
