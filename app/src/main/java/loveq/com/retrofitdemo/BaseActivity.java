package loveq.com.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by rc on 2018/3/6.
 * Description:
 */

public class BaseActivity extends AppCompatActivity {
    protected TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        mTvContent = findViewById(R.id.content);
    }


}
