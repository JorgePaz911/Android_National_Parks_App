package com.example.nationalparks.controller;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//Singleton class. one point of reference for our RequestQueue (which is part of Volley library)
//Uses only one Volley instance. So many instances are not created whenever we use Volley
public class AppController extends Application {

    private static AppController instance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
