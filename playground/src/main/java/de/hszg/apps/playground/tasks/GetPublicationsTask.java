package de.hszg.apps.playground.tasks;

import android.os.AsyncTask;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Projectname: MobileApp
 * Created on 27.11.2016.
 */

public class GetPublicationsTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String get = "http://80.153.90.104/RisikousRESTful/rest/publications";
        try {
            URL url = new URL(get);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int result=is.read();
            while(result != -1){
                byte b = (byte) result;
                bos.write(b);
                result = is.read();
            }
            System.out.println("Ausgabe: "+ bos.toString());

            return bos.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    protected void onPostExecute(String result) {

    }

}
