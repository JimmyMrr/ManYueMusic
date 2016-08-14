package com.example.ritchie_huang.manyuemusic;

import android.app.Application;
import android.content.Intent;

import com.example.ritchie_huang.manyuemusic.Service.PlayService;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ritchie-huang on 16-8-3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);


        startService(new Intent(this, PlayService.class));

    }


}
