package com.example.alberto.proyectofinal;

/**
 * Created by Alberto on 6/10/2016.
 */
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httpRequester {

    OkHttpClient client = new OkHttpClient();



    public String url;
    public String json;


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public  String get() throws IOException {
        Request request = new Request.Builder()
                .url(url)

                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String post() throws IOException {



        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
