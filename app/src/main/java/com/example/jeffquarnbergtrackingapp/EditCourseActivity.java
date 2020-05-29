package com.example.jeffquarnbergtrackingapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.ViewModel.CoursesViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditCourseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "EditCourseActivity";

    private AlarmManager startAlarmManager;
    private AlarmManager endAlarmManager;

    private int mCourseId;
    private int termIdFk;
    private EditText mTitle;
    private EditText mStartDate;
    private EditText mEndDate;
    private EditText mCourseMentorName;
    private EditText mCourseMentorPhone;
    private EditText mCourseMentorEmail;
    private EditText mNote;
    private String mStatus;
    private Date mCourseStartDate;
    private Date mCourseEndDate;
    private DatePickerDialog courseStartDialog;
    private DatePickerDialog courseEndDialog;
    private SimpleDateFormat dateFormat;
    private Button addStartAlertButton;
    private Button addEndAlertButton;
    private Button homeScreenButton;
    private Button saveButton;

    private Courses mInitialCourse;
    private Courses mEditedCourse;
    private CoursesViewModel mCoursesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = findViewById(R.id.edit_course_title);
        mStartDate = findViewById(R.id.edit_course_start_date);
        mStartDate.setInputType(InputType.TYPE_NULL);
        mEndDate = findViewById(R.id.edit_course_end_date);
        mEndDate.setInputType(InputType.TYPE_NULL);
        mCourseMentorName = findViewById(R.id.edit_course_mentor_name);
        mCourseMentorPhone = findViewById(R.id.edit_course_mentor_phone);
        mCourseMentorEmail = findViewById(R.id.edit_course_mentor_email);
        mNote = findViewById(R.id.edit_course_notes);
        Spinner spinner = findViewById(R.id.edit_course_status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US);
        mCoursesViewModel = ViewModelProviders.of(this).get(CoursesViewModel.class);
        addStartAlertButton = findViewById(R.id.edit_course_alert_start_button);
        addEndAlertButton = findViewById(R.id.edit_course_alert_end_button);
        homeScreenButton = findViewById(R.id.edit_course_home_button);
        saveButton = findViewById(R.id.edit_course_save_button);

        addStartAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStartAlert();
            }
        });
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
        courseDatePickers();
        setCourse();
    }

    private void addStartAlert() {
        Intent startIntent = new Intent(this, AlarmReceiver.class);
        startIntent.putExtra("start_message", "Time To Start Course: " + mTitle.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, startIntent, 0);

        startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        try {
            mCourseStartDate = dateFormat.parse(mStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startInMillis = mCourseStartDate.getTime();
        startAlarmManager.set(AlarmManager.RTC_WAKEUP, startInMillis, pendingIntent);
        Toast.makeText(getApplicationContext(), "A Start Alert has been set for "
                + mTitle.getText().toString(), Toast.LENGTH_LONG).show();
    }

    private void addEndAlert() {
        Intent endIntent = new Intent(this, AlarmReceiver.class);
        endIntent.putExtra("end_message", "Time To Finish Course: " + mTitle.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, endIntent, 0);
        endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        try {
            mCourseEndDate = dateFormat.parse(mEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long endInMillis = mCourseEndDate.getTime();
        endAlarmManager.set(AlarmManager.RTC_WAKEUP, endInMillis, pendingIntent);
        Toast.makeText(getApplicationContext(), "An End Alert has been set for "
                + mTitle.getText().toString(), Toast.LENGTH_LONG).show();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(Constants.INTENT_EDIT_COURSE)) {
            mInitialCourse = getIntent().getParcelableExtra(Constants.INTENT_EDIT_COURSE);
            Log.d(TAG, "getIncomingIntent: " + mInitialCourse.getCourseId());
        }
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void saveChanges() {
        try {
            mCourseStartDate = dateFormat.parse(mStartDate.getText().toString());
            mCourseEndDate = dateFormat.parse(mEndDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mEditedCourse = mInitialCourse;
        mEditedCourse.setCourseTitle(mTitle.getText().toString());
        mEditedCourse.setCourseStartDate(mCourseStartDate);
        mEditedCourse.setCourseEndDate(mCourseEndDate);
        mEditedCourse.setCourseStatus(mStatus);
        mEditedCourse.setCourseMentorName(mCourseMentorName.getText().toString());
        mEditedCourse.setCourseMentorPhone(mCourseMentorPhone.getText().toString());
        mEditedCourse.setCourseMentorEmail(mCourseMentorEmail.getText().toString());
        mEditedCourse.setCourseNotes(mNote.getText().toString());
        mCoursesViewModel.update(mEditedCourse);

        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    private void courseDatePickers() {
        mStartDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();

        courseStartDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mStartDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        courseEndDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mEndDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    courseStartDialog.show();
                } else {
                    courseEndDialog.show();
                }
            }
        });
    }

    private void setCourse() {
        mCourseId = mInitialCourse.getCourseId();
        mTitle.setText(mInitialCourse.getCourseTitle());
        mCourseMentorName.setText(mInitialCourse.getCourseMentorName());
        mCourseMentorPhone.setText(mInitialCourse.getCourseMentorPhone());
        mCourseMentorEmail.setText(mInitialCourse.getCourseMentorEmail());
        mNote.setText(mInitialCourse.getCourseNotes());
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
