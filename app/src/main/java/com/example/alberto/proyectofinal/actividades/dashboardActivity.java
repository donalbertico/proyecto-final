package com.example.alberto.proyectofinal.actividades;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.ListView;
import android.widget.Toast;

import com.example.alberto.proyectofinal.R;
import com.example.alberto.proyectofinal.clases.User;
import com.example.alberto.proyectofinal.clases.httpRequester;
import com.example.alberto.proyectofinal.clases.needs;
import com.example.alberto.proyectofinal.clasesGUI.customAdapter;
import com.example.alberto.proyectofinal.clasesGUI.pageAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class dashboardActivity extends AppCompatActivity {
    JSONObject jsonObjectUser;
    JSONObject jsonObjectPhoto;
    Object obj;
    Bitmap imageBitMap;
    ProgressDialog dialog;
    ObjectMapper mapper = new ObjectMapper();
    User user = new User();
    ImageView img2;
    CharSequence badPass = "Contraseña incorrecta";

    ListView needsListView ;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Intent intent = this.getIntent();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        tabLayout.addTab(tabLayout.newTab().setText("Yo"));
        tabLayout.addTab(tabLayout.newTab().setText("Necesidades"));
        tabLayout.addTab(tabLayout.newTab().setText("Otra necesidad"));


        dialog = new ProgressDialog(this);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PagerAdapter adapter = new pageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), intent.getStringExtra("user"));

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new    TabLayout.TabLayoutOnPageChangeListener(tabLayout));

         tabLayout.setOnTabSelectedListener(new    TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==1){

                    AsyncCaller caller = new AsyncCaller("http://web-service-donalbertic0.c9users.io:8080/needs/get",intent.getStringExtra("user"),true );
                    caller.execute();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        int duration = Toast.LENGTH_SHORT;

        try {


            jsonObjectUser = new JSONObject(intent.getStringExtra("user"));


            JSONObject userJson = (JSONObject) jsonObjectUser.get("user");

            user.name = (String) userJson.get("name");
            user.id = (String) userJson.get("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void takePhoto(View view) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1313);
    }

    public void sendPhoto(View view) {


        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS);
        String strBase64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);


        User user = new User();

        user.photo = strBase64;
        Log.d("STRING DE LA FOTO", strBase64);

        String jsonInString = "";
        try {

            jsonInString = mapper.writeValueAsString(user);
            AsyncCaller caller = new AsyncCaller("http://web-service-donalbertic0.c9users.io:8080/photo/insert", jsonInString);
            caller.execute();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    public void insertNeed(View view) {

        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS);
        String strBase64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);


        needs need = new needs();
        need.name = ((EditText) findViewById(R.id.needNameText)).getText().toString();
        need.quantity = Integer.parseInt(((EditText) findViewById(R.id.needQuantText)).getText().toString());
        need.photo = strBase64;

        User user = new User();
        user.needs = new ArrayList<needs>();
        user.needs.add(need);
        user.id = this.user.id;

        String jsonInString = "";
        try {

            jsonInString = mapper.writeValueAsString(user);
            AsyncCaller caller = new AsyncCaller("http://web-service-donalbertic0.c9users.io:8080/needs/insert", jsonInString);
            caller.execute();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("corneton", "AQUIIII VIENE");

        if (requestCode == 1313) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            this.imageBitMap = photo;
            ((ImageView) findViewById(R.id.newNeedPhoto)).setImageBitmap(photo);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "test Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alberto.proyectofinal/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "test Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alberto.proyectofinal/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void setNeeds(String needs){

        Log.d("LLEGO ACA", needs);


        try {
            JSONObject jsonObject = new JSONObject(needs);

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



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void loadNeeds(){
        needs[] needsArray;
        this.needsListView = (ListView) findViewById(R.id.needsListView);

        needsArray = user.needs.toArray(new needs[user.needs.size()]);

        this.needsListView.setAdapter( new customAdapter(this, needsArray));
    }
    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        private httpRequester requester;
        private Boolean needs = false;
        public AsyncCaller(String url, String json) {

            this.requester = new httpRequester();
            requester.url = url;
            requester.json = json;
        }

        public AsyncCaller(String url, String json, Boolean needs) {

            this.requester = new httpRequester();
            requester.url = url;
            requester.json = json;
            this.needs = needs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(!needs){
                dialog.setTitle("Procesando");
                dialog.setMessage("Espere...");
                //proDia.setIcon(getResources().getDrawable(R.drawable.ic_sesa));
                dialog.setCancelable(false);
                dialog.show();

            }else{
                dialog.setTitle("Cargando fotos");
                dialog.setMessage("Espere...");
                //proDia.setIcon(getResources().getDrawable(R.drawable.ic_sesa));
                dialog.setCancelable(false);
                dialog.show();


            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            try {
                if(!needs){
                    requester.post();
                }else{
                    Log.d("AQI DEBE IRSE A FUNCION", "aqiii");
                    setNeeds(requester.post());
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
            if(needs){
                loadNeeds();
            }else{

                Context context = getApplicationContext();
                CharSequence text = "Necesidad agregada";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }

    }
}
