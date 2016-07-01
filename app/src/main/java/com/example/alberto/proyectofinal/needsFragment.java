package com.example.alberto.proyectofinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class needsFragment extends Fragment {

    ListView needs ;
    List<needs> needsList ;
    JSONObject jsonObject;
    User user = new User();
    needs[] needsArray;
    ProgressDialog dialog ;
    View thisView;
    public needsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_needs, container, false);


        this.thisView = view;

        return view;


    }

    public void setNeeds(String needs){

        Log.d("LLEGO ACA", "llego");
        try {
            jsonObject = new JSONObject(needs);

            JSONObject needsJson = (JSONObject) jsonObject.get("response");

            JSONArray jsonNeeds = (JSONArray) needsJson.get("needs");

            user.needs = new ArrayList<needs>();
            for(int i=0 ; i < jsonNeeds.length() ; i++){

                needs need = new needs();
                need.name = (String) jsonNeeds.getJSONObject(i).get("name");
                need.photo = (String) jsonNeeds.getJSONObject(i).get("photo");
                need.quantity = (Integer) jsonNeeds.getJSONObject(i).get("quantity");

                Log.d("sadf", need.toString());
                user.needs.add(need);
            }

            this.needs = (ListView) thisView.findViewById(R.id.needsListView);

            needsArray = user.needs.toArray(new needs[user.needs.size()]);

            this.needs.setAdapter( new customAdapter(getActivity(), needsArray));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("FRAGMENTO", getArguments().getString("user"));

    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        private httpRequester requester;
        private Boolean loadImages = false;

        public AsyncCaller(String url, String json) {

            this.requester = new httpRequester();
            requester.url = url;
            requester.json = json;
            Log.d("LLEGO ACA", "llego");
        }

        public AsyncCaller(){
            this.loadImages = true;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            dialog.setTitle("Procesando");
            dialog.setMessage("Espere...");
            //proDia.setIcon(getResources().getDrawable(R.drawable.ic_sesa));
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            try {
                if(!this.loadImages){
                    Log.d("LLEGO ACA", "llego");
                    setNeeds(requester.post());

                }else{
                    Log.d("destroy", "destryoer");
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }
}
