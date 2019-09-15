package com.example.helloworldbikefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.nio.ByteBuffer;

public class Upload extends AppCompatActivity {

    // ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        // The following follows https://www.youtube.com/watch?v=ondCeqlAwEI
        Button cameraBtn = (Button)findViewById(R.id.cameraButton);
        //imageView = (ImageView)findViewById(R.id.imageView); // For displaying image after

        System.out.println("1");

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                System.out.println("2");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        //imageView.setImageBitmap(bitmap); //For displaying the image after
        byte[] img = convertBitmaptoBytes(bitmap);
        System.out.println("3");
    }

    public byte[] convertBitmaptoBytes(Bitmap bitmap) {
        ByteBuffer bb = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(bb);
        return bb.array();
    }
}
