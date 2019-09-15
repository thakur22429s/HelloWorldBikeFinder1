package com.example.helloworldbikefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindYourBikeActivity2();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpload();
            }
        });
    }

    public void openFindYourBikeActivity2(){
        Intent intent = new Intent(this, FindYourBikeActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_find_your_bike);
    }

    public void openUpload(){
        Intent intent2 = new Intent(this, Upload.class);
        startActivity(intent2);
        //setContentView(R.layout.activity_upload);
    }

}
