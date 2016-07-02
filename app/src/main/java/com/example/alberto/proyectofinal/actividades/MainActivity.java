package com.example.alberto.proyectofinal.actividades;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alberto.proyectofinal.R;
import com.example.alberto.proyectofinal.clases.User;
import com.example.alberto.proyectofinal.clases.httpRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private User user;
    EditText email;
    EditText password;
    ObjectMapper mapper = new ObjectMapper();
    JSONObject jsonObject;
    ProgressDialog dialog ;
    ProgressDialog dialog2;
    Boolean succes = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        email=(EditText)findViewById(R.id.emailText);
        password=(EditText)findViewById(R.id.passwordText);
        dialog = new ProgressDialog(this);
        dialog2 = new ProgressDialog(this);
    }


    public void setUser(String json){

             succes = false;
            try {

                jsonObject =  new JSONObject(json);

                succes = (Boolean) jsonObject.get("success");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(succes){

                Intent intent = new Intent(MainActivity.this, dashboardActivity.class);
                intent.putExtra("user",json);

                startActivity(intent);
            }

    }
    public void sendHttp(View view){

        user = new User(email.getText().toString(),password.getText().toString());
        String jsonInString = "";

        try {

             jsonInString = mapper.writeValueAsString(user);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        AsyncCaller caller = new AsyncCaller("http://web-service-donalbertic0.c9users.io:8080/user/login", jsonInString);
        caller.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        private httpRequester requester ;
        public AsyncCaller(String url , String json ){

            this.requester = new httpRequester();
            requester.url  = url;
            requester.json = json;
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

                setUser(requester.post());
            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if(dialog.isShowing()){
                dialog.dismiss();
            }

            if(!succes){

                Context context = getApplicationContext();
                CharSequence text = "Contraseña incorrecta =(";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }

    }


}

