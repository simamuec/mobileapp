package de.hszg.apps.playground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.hszg.apps.playground.util.UiUtils;

public class MainActivity extends AppCompatActivity {

    //will be used as key-value - name needs to be unique
    public final static String EXTRA_MESSAGE = "de.hszg.apps.mobileapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void createQuestionnaire(View view){
        Intent in = new Intent(this, CreateQuestionnaireActivity.class);
        startActivity(in);
    }


    public void getPublications(View view){
        Intent in = new Intent(this, GetPublicationsActivity.class);
        startActivity(in);
    }

    /**
     * Method will called when the button with id "sendMessage"
     * is pressed, needs to be public and a void return value
     *
     * @param view the view that was clicked
     *//*
    public void sendMessage(View view) {
        //an object that provides runtime binding between separate components (ex. two activities)
        //constructor takes two parameter: context(Activity is subclass of Context) and class(app component which the intent used)
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //widget for editable Text, here its initialized with the content of "edit_message"-tag in values.xml
        EditText editText = UiUtils.findView(this, R.id.edit_message);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //get String-value of editText, if user type something it will be set as message
        String message = editText.getText().toString();
        //intent carry dataTypes as key-value-pairs: key is public "EXTRA_MESSAGE"(will be used by DisplayMessageActivity.class to find text-value)
        //good practice: defining key with package-name as prefix to prevent namespace-problems with other apps(unique name)
        intent.putExtra(EXTRA_MESSAGE, message);
        //starts an instance of DisplayMessageActivity which was specified in the intent
        startActivity(intent);
    }*/

}
