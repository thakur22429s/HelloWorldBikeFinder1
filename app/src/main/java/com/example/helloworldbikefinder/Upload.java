package com.example.helloworldbikefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.nio.ByteBuffer;

public class Upload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }


    public byte[] convertBitmaptoBytes(Bitmap bitmap) {
        ByteBuffer bb = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(bb);
        return bb.array();
    }
}
