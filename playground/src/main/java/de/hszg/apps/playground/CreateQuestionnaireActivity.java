package de.hszg.apps.playground;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateQuestionnaireActivity extends AppCompatActivity  implements View.OnClickListener{

    //Begin Kalender
    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questionnaire);
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
}
