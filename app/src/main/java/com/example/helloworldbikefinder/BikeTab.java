package com.example.helloworldbikefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworldbikefinder.firebase.AccessData;
import com.example.helloworldbikefinder.firebase.DeleteData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Map;

public class BikeTab extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map data;
    private Object latitude;
    private Object longitude;
    private Object downloadUrl;
    private String docID;
    private Button button;

    public BikeTab(Map data, String docID) {
        setData(data);
        setDocID(docID);
        parseData();
    }

    public String convertToGeoJSON(Object latitude, Object longitude) {
        return "{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" +
                latitude + ", " + longitude + "]},properties: { \"name\": " + latitude + longitude + "} }";

    }

    public void parseData() {
        Gson gson = new Gson();
        setLatitude(data.get("latitude"));
        setLongitude(data.get("longitude"));
        setDownloadUrl(data.get("downloadUrl"));
        String jsonString = convertToGeoJSON(latitude, longitude);
        jsonString = gson.toJson(jsonString);
    }

    public void foundBike() {
        DeleteData delete = new DeleteData(docID);
        delete.deleteData();
        // Access all the data again to update main page with new data
        AccessData access = new AccessData();
        access.accessData();
        goBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bike_tab);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void goBack() {
        Intent intent = new Intent(this, FindYourBikeActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_find_your_bike);
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(Object downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
