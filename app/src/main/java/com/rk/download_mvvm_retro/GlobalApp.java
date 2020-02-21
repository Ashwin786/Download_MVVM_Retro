package com.rk.download_mvvm_retro;

import android.app.Application;

public class GlobalApp extends Application {
    public static String imageUrl = "/img/Sample-jpg-image-50kb.jpg";
    public static String baseUrl = "https://sample-videos.com";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
