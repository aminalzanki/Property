package com.propwing.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nikmuhammadamin on 02/12/2016.
 */

public class PropertyDatabase {
    private static final PropertyDatabase sInstance = new PropertyDatabase();
    private static final String PREF_CUSTOM_URL = "custom_url";

    private SharedPreferences mSharedPreferences;


    public static synchronized PropertyDatabase getInstance() {
        return PropertyDatabase.sInstance;
    }

    public PropertyDatabase() {
        super();
    }

    public void setCustomUrl(final String url) {
        setString(PREF_CUSTOM_URL, url);
    }

    public String getCustomUrl() {
        return getString(PREF_CUSTOM_URL);
    }

    public void init(final Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setString(final String key, final String value) {
        if (mSharedPreferences != null) {
            mSharedPreferences.edit().putString(key, value).apply();
        }
    }

    public String getString(final String key) {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(key, null);
        } else {
            return null;
        }
    }

}
