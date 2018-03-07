package loveq.com.retrofitdemo.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import loveq.com.retrofitdemo.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class PostActivity extends AppCompatActivity {

    private TextView mTvContent;

    public interface BlogService {
        /**
         * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);


        /**
         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);


        /**
         * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
         * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
         * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mTvContent = findViewById(R.id.content);
    }

    public void field(View view) {

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
        Call<ResponseBody> call = service.testFormUrlEncoded1("rc", 18);
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


    public void part(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "avatar.jpg";
        File extra = new File(path);
        if (!extra.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
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

        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "rc");
        RequestBody age = RequestBody.create(textType, "16");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"),
                extra);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                extra.getName(), file);

        Call<ResponseBody> call1 = service.testFileUpload1(name, age, filePart);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mTvContent.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void partMap(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "avatar.jpg";
        File extra = new File(path);
        if (!extra.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
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

        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "rc");
        RequestBody age = RequestBody.create(textType, "16");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"),
                extra);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                extra.getName(), file);
        Map<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        Call<ResponseBody> call1 = service.testFileUpload2(map, filePart);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mTvContent.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
