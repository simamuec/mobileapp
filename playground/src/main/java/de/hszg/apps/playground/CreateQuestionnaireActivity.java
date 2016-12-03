package de.hszg.apps.playground;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hszg.apps.playground.model.Publication;
import de.hszg.apps.playground.model.ReportingArea;
import de.hszg.apps.playground.parser.Parser;
import de.hszg.apps.playground.util.RestCallExecution;
import de.hszg.apps.playground.util.UiUtils;

//http://80.153.90.104/RisikousRESTful/rest/reportingareas
public class CreateQuestionnaireActivity extends AppCompatActivity  implements View.OnClickListener{
    //Begin Kalender
    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private PopupWindow popupWindow;
    private Context mContext;
    LinearLayout relativeLayout;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questionnaire);

        relativeLayout = (LinearLayout) findViewById(R.id.activity_create_questionnaire);
        mActivity = CreateQuestionnaireActivity.this;

        Button sendButton = UiUtils.findView(this, R.id.btnErstellte_Meldung_abschicken);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add ReportingArea from Spinner
                EditText incidentDescription = (EditText)findViewById(R.id.incidentDescription);
                //EditText incidentDescription = UiUtils.findView(CreateQuestionnaireActivity.class, R.id.incidentDescription);
                Editable incidentDescriptionText = incidentDescription.getText();
                RadioGroup occurrenceRadioGroup = (RadioGroup) findViewById(R.id.occurrenceRadioGroup);
                int occurrenceRadioGroupValue = occurrenceRadioGroup.getCheckedRadioButtonId(); //-1 = none selected
                RadioGroup detectionRadioGroup = (RadioGroup) findViewById(R.id.detectionRadioGroup);
                int detectionRadioGroupValue = detectionRadioGroup.getCheckedRadioButtonId();
                RadioGroup significanceRadioGroup = (RadioGroup) findViewById(R.id.significanceRadioGroup);
                int significanceRadioGroupValue = significanceRadioGroup.getCheckedRadioButtonId();

                mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.popup_layout,null);

                PopupWindow popupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                if(     incidentDescriptionText != null &&
                        occurrenceRadioGroupValue != -1 &&
                        detectionRadioGroupValue != -1 &&
                        significanceRadioGroupValue != -1) {

                    System.out.println("alle Pflichtfelder wurden ausgefüllt");
                }else{
                    System.out.println("fehlt was");
                }

                int gravityPosition = Gravity.CENTER;
                if(relativeLayout == null)
                    System.out.println("relativeLayout is null");
                if(gravityPosition != 0)
                    System.out.println("gravitiyPosition is 0");

                popupWindow.showAtLocation(
                        relativeLayout,
                        gravityPosition,0,0);
            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }
    public void onClicked(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"TimePicker");


    }


    //End Kalender
    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.date);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //begin time
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current time as the default values for the time picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Create and return a new instance of TimePickerDialog
            return new TimePickerDialog(getActivity(),this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            //Do something with the user chosen time
            //Get reference of host activity (XML Layout File) TextView widget
            EditText time = (EditText) getActivity().findViewById(R.id.time);
            //Display the user changed time on TextView
            time.setText(String.valueOf(hourOfDay)+ " : " + String.valueOf(minute));
        }
    }
    //end time
    class Get_reportingAreas extends AsyncTask<String, Void, String> {
        InputStream is = null;
        String result = null;
        public String getResult(){
            return result;
        }

        @Override
        protected String doInBackground(String... voids) {
            String get = "http://80.153.90.104/RisikousRESTful/rest/reportingareas";

            String result = RestCallExecution.executeGet(get, null);

            if(result.contains("<")) {
                Parser parser = new Parser();
                try {
                    List<ReportingArea> reportingAreas = parser.parse(result);
                    return "";
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                return null;
            }
            return null;
        }
        protected void onPostExecute(String result) {
            //Spinner spinner= (Spinner) findViewById(R.id.result);
            //spinner.set(result.toString());
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
}
