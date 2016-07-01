package com.example.alberto.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {
    User user = new User();
    List<needs> needs;
    ListView milistview;
    ObjectMapper mapper = new ObjectMapper();
    TextView name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        JSONObject jsonObject;

        Object obj;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout =    (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1 Item"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2 Item"));

        Intent intent = this.getIntent();

        try {
            jsonObject = new JSONObject(intent.getStringExtra("user"));

            JSONObject userJson = (JSONObject) jsonObject.get("user");

            user.name = (String) userJson.get("name");
            JSONArray jsonNeeds = (JSONArray) userJson.get("needs");


            user.needs = new ArrayList<needs>();
            for(int i=0 ; i < jsonNeeds.length() ; i++){

                needs need = new needs();
                need.name = (String) jsonNeeds.getJSONObject(i).get("name");
                need.quantity = (Integer) jsonNeeds.getJSONObject(i).get("quantity");

                Log.d("sadf", need.toString());
                user.needs.add(need);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        Log.d("user", user.name);
        name = (TextView) findViewById(R.id.userNameText);

        name.setText(user.name);

        milistview = (ListView) findViewById(R.id.needsList);

        ArrayAdapter<needs> Spinerarrayadpater = new ArrayAdapter<needs>(this,android.R.layout.simple_spinner_item,user.needs);

        milistview.setAdapter(Spinerarrayadpater);
    }

}
