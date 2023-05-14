package com.jdawidowska.myapplication3.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jdawidowska.myapplication3.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String ALL_DATA_URL = "http://192.168.1.4:8099/allData";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundResource(R.drawable.pool_background);
        listView = findViewById(R.id.listView);

        List<String> menuList = new ArrayList();
        menuList.add("Wszystkie Dane");
        menuList.add("Dane z okreslonego przedzialu czasu");
        menuList.add("Dane z basenu z ostatniej godziny");
        menuList.add("Dane ze SPA z ostatniej godziny");
        menuList.add("Usrednione dane z basenu w ciagu dnia");
        menuList.add("Usrednione dane ze SPA w ciagu dnia");
        menuList.add("Usrednione dane z basenu w ciagu tygodnia");
        menuList.add("Terazniejsze dane z basenu");


        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, menuList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(MainActivity.this, AllDataActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(MainActivity.this, SpecifiedPeriodOfTimeActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(MainActivity.this, PoolOneHourActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(MainActivity.this, SpaOneHourActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(MainActivity.this, PoolOneDayActivity.class));
                } else if (position == 5) {
                    startActivity(new Intent(MainActivity.this, SpaOneDayActivity.class));
                } else if (position == 7) {
                    startActivity(new Intent(MainActivity.this, CurrentDataActivity.class));
                }
            }
        });
    }
}