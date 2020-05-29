package com.example.jeffquarnbergtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;
import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.ViewModel.AssessmentsViewModel;
import com.example.jeffquarnbergtrackingapp.ViewModel.CoursesViewModel;
import com.example.jeffquarnbergtrackingapp.ViewModel.TermsViewModel;

import java.util.ArrayList;

public class ViewAssessmentActivity extends AppCompatActivity {

    private static final String TAG = "ViewAssessmentActivity";

    Assessments assessment;

    private ProgressTrackingRepository deleteAssessmentRepository;
    private ArrayList<Assessments> mAllAssessments = new ArrayList<>();
    private ArrayList<Assessments> mAssessments = new ArrayList<>();

    private TermsViewModel termsViewModel;
    private CoursesViewModel mCoursesViewModel;
    private AssessmentsViewModel mAssessmentsViewModel;

    private Button deleteAssessmentButton;
    private Button editAssessmentButton;
    private Button homeScreenButton;

    private TextView mTitle;
    private TextView mType;
    //    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mStatus;

    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = findViewById(R.id.view_assessment_title);
        mType = findViewById(R.id.view_assessment_type);
//        mStartDate = findViewById(R.id.view_assessment_start_date);
        mEndDate = findViewById(R.id.view_assessment_end_date);
        mStatus = findViewById(R.id.view_assessment_status);
        deleteAssessmentButton = findViewById(R.id.delete_assessment_button);
        editAssessmentButton = findViewById(R.id.edit_assessment_button);
        homeScreenButton = findViewById(R.id.view_assessment_home_button);

        deleteAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAssessment();
            }
        });
        editAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAssessment();
            }
        });
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        deleteAssessmentRepository = new ProgressTrackingRepository(this);

        getIncomingIntent();
        setAssessment();
    }

    private void deleteAssessment() {
        deleteAssessmentRepository.delete(assessment);
//        mCoursesViewModel.delete(course);
        Intent intent2 = new Intent(this, TermsActivity.class);
        startActivity(intent2);
    }

    private void editAssessment() {
        Intent intent = new Intent(this, EditAssessmentActivity.class);
        intent.putExtra(Constants.INTENT_EDIT_ASSESSMENT, assessment);
        startActivity(intent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("selected_assessment")) {
            assessment = getIntent().getParcelableExtra("selected_assessment");
            assessmentId = assessment.getAssessmentId();

            // set remaining variables?
            Log.d(TAG, "getIncomingIntent: " + assessment.toString());
            Log.d(TAG, "onCreate: " + assessment.getAssessmentId() + " " + assessment.getAssessmentTitle() + " from ViewCourseActivity");

        }
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void setAssessment() {
        mTitle.setText(assessment.getAssessmentTitle());
        mType.setText(assessment.getAssessmentType());
//        mStartDate.setText(assessment.getAssessmentStartDate().toString());
        mEndDate.setText(assessment.getAssessmentEndDate().toString());
        mStatus.setText(assessment.getAssessmentStatus());
    }
}
