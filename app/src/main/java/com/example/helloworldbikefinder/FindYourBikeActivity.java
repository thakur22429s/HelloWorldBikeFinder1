package com.example.helloworldbikefinder;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FindYourBikeActivity extends AppCompatActivity {

    ListView bicycleListView;
    String[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_bike);

        Resources res = getResources();
        bicycleListView = (ListView) findViewById(R.id.bicycleListView);
        locations = res.getStringArray(R.array.locations);

        bicycleListView.setAdapter(new ArrayAdapter<String>(this, R.layout.bicycle_list_view_detail, locations) {

        });
    }
}
