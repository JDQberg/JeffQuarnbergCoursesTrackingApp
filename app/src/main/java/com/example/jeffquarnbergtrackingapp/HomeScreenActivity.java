package com.example.jeffquarnbergtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.jeffquarnbergtrackingapp.DAO.CoursesDAO;
import com.example.jeffquarnbergtrackingapp.DAO.TermsDAO;
import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingDatabase;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.ViewModel.AssessmentsViewModel;
import com.example.jeffquarnbergtrackingapp.ViewModel.CoursesViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {
    private CoursesViewModel mCoursesViewModel;
    private AssessmentsViewModel mAssessmentsViewModel;
    private List<Courses> mAllCourses;
    private List<Assessments> mAllAssessments;
    private Button termsButton;
    private double coursesComplete = 0;
    private double coursesPercentComplete = 0;
    private double assessmentsComplete = 0;
    private double assessmentsPercentComplete = 0;
    private int coursesPlanToTake = 0;
    private int coursesInProgress = 0;
    private int coursesDropped = 0;
    private int coursesCompleted = 0;
    private int assessmentsPlanToTake = 0;
    private int assessmentsFailed = 0;
    private int assessmentsPassed = 0;
    private TextView coursesPlanToTakeTV;
    private TextView coursesInProgressTV;
    private TextView coursesDroppedTV;
    private TextView coursesCompletedTV;
    private TextView coursesProgressTV;
    private TextView assessmentsPlanToTakeTV;
    private TextView assessmentsFailedTV;
    private TextView assessmentsPassedTV;
    private TextView assessmentsProgressTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set button programmatically. (One of the task requirements.)
        Button populateDbButton = new Button(this);
        populateDbButton.setText("Populate The DB With Test Data");
        populateDbButton.setY(170);
        populateDbButton.setX(50);
        CoordinatorLayout relativeLayout = (CoordinatorLayout) View.inflate(this, R.layout.activity_main, null);
        relativeLayout.addView(populateDbButton);
        setContentView(relativeLayout);
        mCoursesViewModel = ViewModelProviders.of(this).get(CoursesViewModel.class);
        mAssessmentsViewModel = ViewModelProviders.of(this).get(AssessmentsViewModel.class);
        mAllCourses = mCoursesViewModel.getCoursesList();
        mAllAssessments = mAssessmentsViewModel.getAssessmentsList();
        coursesPlanToTakeTV = findViewById(R.id.home_courses_plan_to_take);
        coursesInProgressTV = findViewById(R.id.home_courses_in_progress);
        coursesDroppedTV = findViewById(R.id.home_courses_dropped);
        coursesCompletedTV = findViewById(R.id.home_courses_completed);
        coursesProgressTV = findViewById(R.id.home_courses_progress);
        assessmentsPlanToTakeTV = findViewById(R.id.home_assessments_plan_to_take);
        assessmentsFailedTV = findViewById(R.id.home_assessments_failed);
        assessmentsPassedTV = findViewById(R.id.home_assessments_passed);
        assessmentsProgressTV = findViewById(R.id.home_assessments_progress);
        termsButton = (Button) findViewById(R.id.termsButton);

        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTermsActivity();
            }
        });
        populateDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopulateDb populateDb = new PopulateDb();
                populateDb.addTestData(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Test data added",
                        Toast.LENGTH_LONG).show();
            }
        });

        retrieveCourses();
        retrieveAssessments();
    }

    private void calculateCourseProgress() {
        coursesComplete = coursesCompleted;

        if (coursesComplete != 0) {
            coursesPercentComplete = (coursesComplete / mAllCourses.size()) * 100;
        } else {
            coursesPercentComplete = 0;
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        coursesProgressTV.setText(String.format("You are currently %s%% complete with your assigned courses.", numberFormat.format(coursesPercentComplete)));
    }

    private void retrieveCourses() {
        for (int i = 0; i < mAllCourses.size(); i++) {
            if (mAllCourses.get(i).getCourseStatus().contains("Plan To Take")) {
                coursesPlanToTake++;
            }
            if (mAllCourses.get(i).getCourseStatus().contains("In Progress")) {
                coursesInProgress++;
            }
            if (mAllCourses.get(i).getCourseStatus().contains("Dropped")) {
                coursesDropped++;
            }
            if (mAllCourses.get(i).getCourseStatus().contains("Completed")) {
                coursesCompleted++;
            }
        }

        coursesPlanToTakeTV.setText(String.valueOf(coursesPlanToTake));
        coursesInProgressTV.setText(String.valueOf(coursesInProgress));
        coursesDroppedTV.setText(String.valueOf(coursesDropped));
        coursesCompletedTV.setText(String.valueOf(coursesCompleted));
        calculateCourseProgress();
    }

    private void calculateAssessmentProgress() {
        assessmentsComplete = assessmentsPassed;

        if (assessmentsComplete != 0) {
            assessmentsPercentComplete = (assessmentsComplete / mAllAssessments.size()) * 100;
        } else {
            assessmentsPercentComplete = 0;
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        assessmentsProgressTV.setText(String.format("You are currently %s%% complete with your assigned assessments.", numberFormat.format(assessmentsPercentComplete)));
    }

    private void retrieveAssessments() {
        for (int i = 0; i < mAllAssessments.size(); i++) {
            if (mAllAssessments.get(i).getAssessmentStatus().contains("Plan To Take")) {
                assessmentsPlanToTake++;
            }
            if (mAllAssessments.get(i).getAssessmentStatus().contains("Failed")) {
                assessmentsFailed++;
            }
            if (mAllAssessments.get(i).getAssessmentStatus().contains("Passed")) {
                assessmentsPassed++;
            }
        }

        assessmentsPlanToTakeTV.setText(String.valueOf(assessmentsPlanToTake));
        assessmentsFailedTV.setText(String.valueOf(assessmentsFailed));
        assessmentsPassedTV.setText(String.valueOf(assessmentsPassed));
        calculateAssessmentProgress();
    }

    public void openTermsActivity() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }
}
