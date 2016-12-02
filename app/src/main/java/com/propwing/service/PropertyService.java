package com.propwing.service;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.propwing.BuildConfig;
import com.propwing.model.Property;
import com.propwing.model.ResponseError;
import com.propwing.service.event.OnPropertyEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public class PropertyService {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static PropertyService sInstance = null;
    private static OkHttpClient.Builder mHttpClient = new OkHttpClient.Builder();
    private static PropertyServiceInterface mService = null;

    private static Retrofit mRetrofit = null;
    private Realm mRealm;
    private EventBus mEventBus = EventBus.getDefault();

    public PropertyService() {
        // Enforce Singleton
    }

    public static synchronized PropertyService getInstance() {
        if (sInstance == null) {
            synchronized ((PropertyService.class)) {
                if (sInstance == null) {
                    sInstance = new PropertyService();
                }
            }
        }

        mService = createService(PropertyServiceInterface.class, "");
        return sInstance;
    }

    private static Gson mGson = new GsonBuilder()
            //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(DATE_FORMAT)
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create(mGson));

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest;
                if (authToken != null) {
                    newRequest = chain.request().newBuilder()
                            .header("Authorization", authToken)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .build();
                } else {
                    newRequest = chain.request().newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .build();
                }


                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        builder.interceptors().add(logging);
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(client)
                .build();

        return mRetrofit.create(serviceClass);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public void getProperties(final String url, final int refresh, final String source) {
        final Call<Property> call;
        if (refresh == 1) {
            call = mService.getProperty(url, 1, source);
        } else {
            call = mService.getProperty(url, source);
        }

        call.enqueue(new Callback<Property>() {
            @Override
            public void onResponse(Call<Property> call, Response<Property> response) {
                if (response.isSuccessful()) {
                    OnPropertyEvent event = new OnPropertyEvent();
                    event.setProperty(response.body());
                    event.setResponse(response);
                    mEventBus.post(event);
                } else {
                    ResponseError error = ErrorUtils.parseError(response);
                    OnPropertyEvent event = new OnPropertyEvent();
                    event.setResponseError(error);
                    mEventBus.post(event);
                }
            }

            @Override
            public void onFailure(Call<Property> call, Throwable t) {
                Log.d("AAA", t.getMessage());
            }
        });
    }

    public void getPropertiesFromCustomUrl(final String url) {
        final Call<Property> call;
        call = mService.getProperty(url);
        call.enqueue(new Callback<Property>() {
            @Override
            public void onResponse(Call<Property> call, Response<Property> response) {
                if (response.isSuccessful()) {
                    OnPropertyEvent event = new OnPropertyEvent();
                    event.setProperty(response.body());
                    event.setResponse(response);
                    mEventBus.post(event);
                } else {
                    ResponseError error = ErrorUtils.parseError(response);
                    OnPropertyEvent event = new OnPropertyEvent();
                    event.setResponseError(error);
                    mEventBus.post(event);
                }
            }

            @Override
            public void onFailure(Call<Property> call, Throwable t) {
                Log.d("AAA", t.getMessage());
            }
        });
    }
}
