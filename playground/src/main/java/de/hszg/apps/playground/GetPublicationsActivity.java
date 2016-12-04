package de.hszg.apps.playground;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hszg.apps.playground.model.Publication;
import de.hszg.apps.playground.parser.Parser;
import de.hszg.apps.playground.tasks.GetPublicationsTask;
import de.hszg.apps.playground.util.PublicationCompareEnum;
import de.hszg.apps.playground.util.PublicationListComparator;
import de.hszg.apps.playground.util.UiUtils;
//http://www.vogella.com/tutorials/AndroidListView/article.html
public class GetPublicationsActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_publications);

        mContext = getApplicationContext();
        mActivity = GetPublicationsActivity.this;
        get_xml get_xml = new get_xml();
        get_xml.execute();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    class get_xml extends AsyncTask<String, Void, String> {
        InputStream is = null;

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
            //TextView textview = (TextView) findViewById(R.id.result);
            //textview.setText(result.toString());
            Parser parser = new Parser();

            List<Publication> publicationList = null;
            String[] values = new String[]{"1", "2", "3"};
            try {
                publicationList = parser.parse(result.toString());
                //int counter = 0;
                for(Publication publication:publicationList){
                    //values[counter] = publication.toString();
                    //counter++;
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Publication> adapter = new ArrayAdapter<Publication>(mContext, android.R.layout.simple_list_item_1, publicationList);
            adapter.sort(Collections.reverseOrder(new PublicationListComparator(PublicationCompareEnum.ENTRY_DATE)));
            setListAdapter(adapter);
        }

    }
}
