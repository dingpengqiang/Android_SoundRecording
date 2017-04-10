package com.xiaoq.android_soundrecording;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtn1;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn1 = (Button) findViewById(R.id.record);
        mBtn2 = (Button) findViewById(R.id.record2);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.record:
                startActivity(new Intent(this,SecondActivity.class));
                break;
            case R.id.record2:
                startActivity(new Intent(this,ThirdActivity.class));
                break;
        }
    }



}
