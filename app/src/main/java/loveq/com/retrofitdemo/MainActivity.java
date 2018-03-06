package loveq.com.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import loveq.com.retrofitdemo.ui.FormActivity;
import loveq.com.retrofitdemo.ui.HelloWorldActivity;
import loveq.com.retrofitdemo.ui.HttpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void HellWorld(View view) {
        jumpToActivity(HelloWorldActivity.class);
    }

    public void Http(View view) {
        jumpToActivity(HttpActivity.class);
    }

    public void Form(View view) {
        jumpToActivity(FormActivity.class);
    }

    public void jumpToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


}
