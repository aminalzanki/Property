package com.propwing.service;

import com.propwing.model.Property;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public interface PropertyServiceInterface {

    @GET("listing")
    Call<Property> getProperty(@Query("url") String url, @Query("source") String source);

    @GET("listing")
    Call<Property> getProperty(@Query("url") String url, @Query("refresh") int id, @Query("source") String source);

    @GET
    Call<Property> getProperty(@Url String url);
}
