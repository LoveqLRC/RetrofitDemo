package loveq.com.retrofitdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import loveq.com.retrofitdemo.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * GET参数注解
 */
public class GetActivity extends AppCompatActivity {

    protected TextView mTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        mTvContent = findViewById(R.id.content);
    }




    public interface BlogService {
        @GET("blog/{id}")
            //这里的{id} 表示是一个变量
        Call<ResponseBody> getBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);

        @GET("/blog")
            //拼接查询参数/blog?id=1
            //注意/blog后面不能有/
        Call<ResponseBody> getBlog2(@Query("id") int id);

        @GET("/blog")
         //注意/blog后面不能有/
        Call<ResponseBody> getBlog3(@QueryMap Map<String, String> map);
    }

    public void path(View view) {
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
        Call<ResponseBody> call = service.getBlog(1);
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


    public void query(View view) {
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
        Call<ResponseBody> call = service.getBlog2(1);
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


    public void queryMap(View view) {
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
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        Call<ResponseBody> call = service.getBlog3(map);
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
