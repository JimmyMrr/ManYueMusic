package com.example.ritchie_huang.manyuemusic.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.Api;
import com.example.ritchie_huang.manyuemusic.Util.PersistentCookieStore;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookiePolicy;

/**
 * Created by ritchie-huang on 16-8-5.
 */
public class TestActivity extends Activity {

    private OkHttpClient client;
    private TextView textView;
    InputStream in;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            in = (InputStream) msg.obj;

            textView.setText((String)msg.obj);
            Log.d("TestActivity", (String)msg.obj);
//            textView.setText((String)msg.obj);
//            new save(in).start();

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_test);

        textView = (TextView) findViewById(R.id.text);
        client = new OkHttpClient();
        client.setCookieHandler(new java.net.CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL));

        String url = "http://music.163.com/api/playlist/detail?id=426782554";
        RequestBody formbody = new FormEncodingBuilder()
                .add("os", "pc")
                .add("id", "208920")
                .add("lv", String.valueOf(-1))
                .add("kv", String.valueOf(-1))
                .add("tv", String.valueOf(-1))
                .build();
        Request request = new Request.Builder()
                .url(Api.SONG_LRC)
                .post(formbody)
                .addHeader("Referer","http://music.163.com/")
                .addHeader("Cookie", "appver=1.5.0.75771")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(TestActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = Message.obtain();
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });

    }

    public class save extends Thread {

        private InputStream in;

        public save(InputStream in) {
            this.in = in;
        }
        @Override
        public void run() {

            savetoFile();
            super.run();
        }

        public void savetoFile() {
            File file = new File(Environment.getExternalStorageDirectory(), "playlistsdetail");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len;
                while ((len = in.read(b)) > 0) {
                    fos.write(b,0,len);
                }
                fos.flush();
                fos.close();
                in.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
