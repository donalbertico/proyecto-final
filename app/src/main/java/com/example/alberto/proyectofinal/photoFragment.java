package com.example.alberto.proyectofinal;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class photoFragment extends Fragment {

    ImageView imagev ;
    Button takePhoto;
    public photoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_photo, container, false);


        imagev = (ImageView) view.findViewById(R.id.imageView);
        takePhoto = (Button) view.findViewById(R.id.takePhotoBtn);


        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("cornetonPHOTO", getArguments().getString("user"));


    }


}

