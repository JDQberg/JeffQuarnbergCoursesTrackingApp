package com.example.jeffquarnbergtrackingapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.ViewModel.AssessmentsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssessmentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddAssessmentActivity";

    private int courseIdFk;
    private EditText mTitle;
    private EditText mType;
    //    private EditText mStartDate;
    private EditText mEndDate;
    private String mStatus;
    //    private Date mAssessmentStartDate;
    private Date mAssessmentEndDate;
    //    private DatePickerDialog assessmentStartDialog;
    private DatePickerDialog assessmentEndDialog;
    private SimpleDateFormat dateFormat;
    private Button addEndAlertButton;
    private Button homeScreenButton;
    private Button saveButton;

    private Assessments mAddAssessment;
    private AssessmentsViewModel mAssessmentsViewModel;

    private Courses mInitialCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = findViewById(R.id.add_assessment_title);
        mType = findViewById(R.id.add_assessment_type);
//        mStartDate = findViewById(R.id.add_assessment_start_date);
//        mStartDate.setInputType(InputType.TYPE_NULL);
        mEndDate = findViewById(R.id.add_assessment_end_date);
        mEndDate.setInputType(InputType.TYPE_NULL);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US);
        mAssessmentsViewModel = ViewModelProviders.of(this).get(AssessmentsViewModel.class);
        Spinner spinner = findViewById(R.id.add_assessment_status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        addEndAlertButton = findViewById(R.id.add_assessment_alert_end_button);
        homeScreenButton = findViewById(R.id.add_assessment_home_button);
        saveButton = findViewById(R.id.add_assessment_save_button);
        addEndAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEndAlert();
            }
        });
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        getIncomingIntent();
        assessmentDatePickers();
    }

    private void addEndAlert() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("goal_message", "Time To Finish Assessment: " + mTitle.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        try {
            mAssessmentEndDate = dateFormat.parse(mEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startInMillis = mAssessmentEndDate.getTime();
        alarmManager.set(AlarmManager.RTC_WAKEUP, startInMillis, pendingIntent);
        Toast.makeText(getApplicationContext(), "An End Goal Alert has been set for "
                + mTitle.getText().toString(), Toast.LENGTH_LONG).show();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(Constants.INTENT_EDIT_COURSE)) {
            mInitialCourse = getIntent().getParcelableExtra(Constants.INTENT_EDIT_COURSE);
        }
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void saveChanges() {
        try {
//            mAssessmentStartDate = dateFormat.parse(mStartDate.getText().toString());
            mAssessmentEndDate = dateFormat.parse(mEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAddAssessment = new Assessments(mTitle.getText().toString(), mType.getText().toString(),
                mAssessmentEndDate, mStatus, courseIdFk = mInitialCourse.getCourseId());

        mAssessmentsViewModel.insert(mAddAssessment);

        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    private void assessmentDatePickers() {
//        mStartDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();

//        assessmentStartDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, month, dayOfMonth);
//                mStartDate.setText(dateFormat.format(newDate.getTime()));
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        assessmentEndDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mEndDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                assessmentEndDialog.show();
//                if (hasFocus) {
//                    assessmentStartDialog.show();
//                } else {
//                    assessmentEndDialog.show();
//                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mStatus = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
