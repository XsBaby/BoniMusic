package com.xushuai.work.net;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xushuai.work.utils.HttpInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.string.ok;
import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;

/**
 * 创建时间： 2017/9/22.
 * 创建人： 徐帅
 * 类的作用：
 */

public class OkHttpUtils {
    private NetDataCallBack netDataCallback;
    private Handler mhand = new Handler();

    public <T> void getdata(String url, final NetDataCallBack netDataCallback, final Class<T> tclass) {
        //初始化一个 OkHttpClient 并且设置连接和读取超时时间
        OkHttpClient okhttp = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .build();
        //构造一个Request对象
        Request request = new Request.Builder().url(url).build();
        //通过request的对象去构造得到一个Call对象
        Call call = okhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                Gson gson = new Gson();
                T t = gson.fromJson(string, tclass);
                final Message msg = Message.obtain();
                msg.what = ok;
                msg.obj = t;
                mhand.post(new Runnable() {
                    @Override
                    public void run() {
                        netDataCallback.success(msg.obj);
                    }
                });
            }
        });
    }

    //post请求
    public <T> void getLoadDataPost(String url, final NetDataCallBack netDataCallback, final Class<T> tClass, RequestBody body) {
        this.netDataCallback = netDataCallback;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netDataCallback.faild(499, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                T t = new Gson().fromJson(response.body().string(), tClass);
                final Message msg = Message.obtain();
                msg.what = OK;
                msg.obj = t;
                mhand.post(new Runnable() {
                    @Override
                    public void run() {
                        netDataCallback.success(msg.obj);

                    }
                });
            }
        });
    }
}