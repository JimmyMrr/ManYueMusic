package com.example.ritchie_huang.manyuemusic.Util;

import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by ritchie-huang on 16-8-2.
 */
public class OkPOST extends Thread {
    private OkHttpClient client;
    private Handler handler;
    private RequestBody formbody;
    private String url;

    public OkPOST(OkHttpClient client, String url, RequestBody formbody, Handler handler) {
        this.client = client;
        this.url = url;
        this.formbody = formbody;
        this.handler = handler;
    }

    @Override
    public void run() {
        doPost();

    }

    private void doPost() {
        Request request = new Request.Builder()
                .url(url)
                .post(formbody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(Constants.OKHTTP_CALL_FAIL);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = Message.obtain();
                message.what = Constants.OKHTTP_CALL_SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }
}
