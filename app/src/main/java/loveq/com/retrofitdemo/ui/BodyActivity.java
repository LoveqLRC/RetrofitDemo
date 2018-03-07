package loveq.com.retrofitdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import loveq.com.retrofitdemo.R;
import loveq.com.retrofitdemo.entity.Blog;
import loveq.com.retrofitdemo.entity.Result;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class BodyActivity extends AppCompatActivity {

    public interface BlogService {
        @POST("blog")
        Call<Result<Blog>> createBlog(@Body Blog blog);
    }

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        mTvContent = findViewById(R.id.content);
    }

    public void body(View view) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.1.18:4567/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Blog blog = new Blog();
        blog.content = " new blog";
        blog.title = "retrofit 练习";
        blog.author = "rc";
        Call<Result<Blog>> call = service.createBlog(blog);
        call.enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                Result<Blog> result = response.body();
                mTvContent.setText(result.toString());
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {

            }
        });

    }
}
