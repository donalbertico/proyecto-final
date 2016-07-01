package com.example.alberto.proyectofinal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class userFragment extends Fragment {
    TextView userName ;
    User user = new User();
    String us;
    JSONObject jsonObject;
    public userFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view =inflater.inflate(R.layout.fragment_user, container, false);

        userName = (TextView)  view.findViewById(R.id.userNameTextView);
        userName.setText(user.name);

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        us =  getArguments().getString("user");

        try {
            jsonObject = new JSONObject( getArguments().getString("user"));



            JSONObject userJson = (JSONObject) jsonObject.get("user");

            user.name = (String) userJson.get("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
