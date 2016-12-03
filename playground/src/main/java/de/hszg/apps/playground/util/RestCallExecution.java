package de.hszg.apps.playground.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Projectname: MobileApp
 * Created on 02.12.2016.
 */

public class RestCallExecution {



    public static String executeGet(String getUrl, Map<String, String> params){

        try {

            if(params != null && params.size() > 0){

                boolean firstParam = true;

                for(String param: params.keySet()){

                    if(firstParam) {

                        getUrl = getUrl + "?" + param + "=" + params.get(param);
                        firstParam = false;
                    }else{
                        getUrl = getUrl + "&" + param + "=" + params.get(param);
                    }
                }
            }

            URL url = new URL(getUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");


            if(httpURLConnection.getResponseCode() == 200){
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
            }else{
                return httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String executePost(String postUrl, Map<String, String> params) {

        try {

            if(params != null || params.size() > 0){

                boolean firstParam = true;

                for(String param: params.keySet()){

                    if(firstParam) {

                        postUrl = postUrl + "?" + param + "=" + params.get(param);
                        firstParam = false;
                    }else{
                        postUrl = postUrl + "&" + param + "=" + params.get(param);
                    }
                }
            }

            URL url = new URL(postUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);


            StringBuilder paramResult = new StringBuilder();
            boolean first = true;

            for (String param : params.keySet())
            {
                if (first)
                    first = false;
                else
                    paramResult.append("&");

                paramResult.append(URLEncoder.encode(param, "UTF-8"));
                paramResult.append("=");
                paramResult.append(URLEncoder.encode(params.get(param), "UTF-8"));
            }

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(paramResult.toString());
            writer.flush();
            writer.close();
            os.close();

            httpURLConnection.connect();
            httpURLConnection.getResponseMessage();

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
}
