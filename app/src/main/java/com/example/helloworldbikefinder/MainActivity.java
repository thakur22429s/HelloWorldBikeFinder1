package com.example.helloworldbikefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

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
    }

    public void openFindYourBikeActivity2(){
        Intent intent = new Intent(this, FindYourBikeActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_find_your_bike);
    }

}
