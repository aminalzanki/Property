package com.propwing.service;

import com.propwing.model.ResponseError;
import com.propwing.service.PropertyService;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */
public class ErrorUtils {
    public static ResponseError parseError(Response<?> response) {
        Converter<ResponseBody, ResponseError> converter =
                PropertyService.getInstance().getRetrofit()
                        .responseBodyConverter(ResponseError.class, new Annotation[0]);

        ResponseError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ResponseError();
        }

        return error;
    }
}
