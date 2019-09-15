package com.example.helloworldbikefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    private Button button;
    private Button button2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

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
/**

 File localFile = File.createTempFile("images", "jpg");
 riversRef.getFile(localFile)
 .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
@Override
public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
// Successfully downloaded data to local file
// ...
}
}).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception exception) {
// Handle failed download
// ...
}
});
 **/