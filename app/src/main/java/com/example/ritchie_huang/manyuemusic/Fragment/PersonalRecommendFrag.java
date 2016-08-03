package com.example.ritchie_huang.manyuemusic.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritchie_huang.manyuemusic.Activity.HomeActivity;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.Constants;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by ritchie-huang on 16-8-2.
 */
public class PersonalRecommendFrag extends Fragment {
    private OkHttpClient client = HomeActivity.client;
    private TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_personal_recommand, container, false);
        init(view);
//        getInfoFromUrl();
        return view;
    }

    private void init(View view) {
        textView = (TextView) view.findViewById(R.id.text);

    }

    private void getInfoFromUrl(){
        RequestBody formbody = new FormEncodingBuilder()
                .add("params", "0BD8BB39A78692F1744DEFF63EBC30F7889FA0D28FD18C56783C7BF3AADA4C516E269DCEF72717031B0D0797563D21D74A80931032E90A0DBF772B7B86DAB7B29C47227066BA6859EF81B2BDC94960501592EFDBED2FA4BB612DD34C3BE69C1CB997189A2D14BE23FACD2D81694F87D7D86DD3F48F213C035A89EDEE2F6336478BEBA964633B3DB2A074EA2662FE8AEC18A167403EA0D465ED99F6E0BF1B58D64E2F6FAB87BFB382901FB3F8D753ABABE5361DD03E8767F3CC5BE299EDCBF8CEA82126579A7E11CD9A6B7A95AEB41CEC237356031206C2C94443360BB430F44D4CE1F78FE98FDF4468B40977A33CD3A7AD9A9F926C5E1B3979139277DBCDF27E7EB4BFC0C4996CD069835883475527C7D296034459225E90FC0FD45F259EDAD79318B200CCC01B51E4571EFD93F7E7EFE09D1169A86936C7C3D1E0EAAFE6955D2A72808C6F340B4388E57F4443C22DCB267E6BA157E3256F2924B9A2DD0B1F4C001E848DC9F85F05DE82FCCA50763549329EF9DF1BC9746B9CFB7308D72159C5A5DC242B76960F7E62827FD52B8F4BCF7A667EBDAD93E5D34CB68D92ECBCD7FEE9265DD359457ED508F38B088041E5BBFDB949F891FA490B48B24C2C754762F31DC4C0F0C8E3930D08A628D82D10C6CADDEA0BBDF8D9FF405C9FE9B2E5622BD99757F50109BF2BBE0B6804606EB5EF23E3D772D023013244905739680AC5801E039D02D768DDB47BE085BE698DFA91C29B13F34AFEC3DA8E69251F8EB21D1A11B85F89B6383089FEF4713C1C21972D09E2433FEDADBAB3B6ED239935E06E76AACA3A66B3F11E51EFD0F5AD0CE6A32783")
                .build();
        Request request = new Request.Builder()
                .url("http://music.163.com/eapi/batch")
                .post(formbody)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Encoding", "gzip,deflate,sdch")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4")
                .addHeader("Connection", "keep-alive")
                .addHeader("Host", "music.163.com")
                .addHeader("Referer", "http://music.163.com/search/")
                .addHeader("Cookie", "appver=1.5.2")
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
                message.obj = response.body().byteStream();
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.OKHTTP_CALL_FAIL:
                    Toast.makeText(getContext(), "请求出错", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.OKHTTP_CALL_SUCCESS:
                    GZIPInputStream gzipInputStream = null;
                    try {
                        gzipInputStream = new GZIPInputStream((InputStream) msg.obj);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        int length = -1;

                        byte[] cache = new byte[1024];

                        while ((length = gzipInputStream.read(cache)) != -1) {
                            byteArrayOutputStream.write(cache, 0, length);
                        }

                        textView.setText(new String(byteArrayOutputStream.toByteArray()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
