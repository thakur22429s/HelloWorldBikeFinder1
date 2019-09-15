package com.example.helloworldbikefinder;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.helloworldbikefinder.firebase.AccessData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Map;

public class FindYourBikeActivity extends AppCompatActivity {

    ListView bicycleListView;
    String[] locations;
    ArrayList<Map> data;
    ArrayList<String> docIDs;
    ArrayList<BikeTab> bikeTabs;

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

   public void collectAllData() {
        AccessData access = new AccessData();
        data = access.accessData();
    }

    /**
     * Creates tabs for each object. Needs functionality for constant scrolling.
     */
    public void createTabs() {
        // updateUI to erase previous tabs
        for (int i = 0; i < data.size(); i++) {
            bikeTabs.add(new BikeTab(data.get(i), docIDs.get(i)));
        }

    }
}