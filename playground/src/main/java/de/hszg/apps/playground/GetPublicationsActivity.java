package de.hszg.apps.playground;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hszg.apps.playground.model.Publication;
import de.hszg.apps.playground.parser.Parser;
import de.hszg.apps.playground.util.UiUtils;

public class GetPublicationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_publications);

        //get_xml get_xml = new get_xml();
        //get_xml.execute();
        GetPublicationsTask getPublicationsTask = new GetPublicationsTask();
        getPublicationsTask.execute();
        /*try {
            TextView textView = UiUtils.findView(this, R.id.result);
            textView.setText(getPublicationsTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/


    }


    class GetPublicationsTask extends AsyncTask<String, Void, String> {
        InputStream is = null;
        String result = null;
        public String getResult(){
            return result;
        }
        @Override
        protected String doInBackground(String... voids) {
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
            TextView textview = (TextView) findViewById(R.id.result);
            textview.setText(result.toString());
            Parser parser = new Parser();

            try {
                List<Publication> publicationList = parser.parse(result.toString());
                for(Publication publication:publicationList){
                    publication.toString();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*class get_xml extends AsyncTask<String, Void, String> {
        InputStream is = null;
        String result = null;
        public String getResult(){
            return result;
        }
        @Override
        protected String doInBackground(String... voids) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://80.153.90.104/RisikousRESTful/rest/publications");
            try {
                HttpResponse httpResponse = httpclient.execute(httpget);
                is = httpResponse.getEntity().getContent();
                BufferedInputStream bis = new BufferedInputStream(is);
                //BufferedOutputStream bos = new BufferedOutputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int result=bis.read();
                while(result != -1){
                    byte b = (byte) result;
                    bos.write(b);
                    result = bis.read();
                }
                System.out.println("Ausgabe: "+ bos.toString());
                this.result = bos.toString();
                return bos.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "kein response";
        }
        protected void onPostExecute(String result) {
            TextView textview = (TextView) findViewById(R.id.get_test);
            textview.setText(result.toString());
        }

    }*/
}
