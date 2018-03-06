package loveq.com.retrofitdemo.ui;

import android.view.View;

import java.io.IOException;

import loveq.com.retrofitdemo.BaseActivity;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * 使用Http注解
 */
public class HttpActivity extends BaseActivity {

    public interface BlogService {
        /**
         * method 表示请求的方法，区分大小写，retrofit 不会做处理
         * path表示路径
         * hasBody表示是否有请求体
         */
        @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
        Call<ResponseBody> getBlog(@Path("id") int id);
    }


    public void execute(View view) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.18:4567/")
                .build();

       BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.getBlog(2);
        // 用法和OkHttp的call如出一辙
        // 不同的是如果是Android系统回调方法执行在主线程
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mTvContent.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
