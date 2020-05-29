package com.example.jeffquarnbergtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;
import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.ViewModel.AssessmentsViewModel;
import com.example.jeffquarnbergtrackingapp.ui.AssessmentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewCourseActivity extends AppCompatActivity implements AssessmentsAdapter.OnAssessmentListener {

    private static final String TAG = "ViewCourseActivity";

    Courses course;

    private ProgressTrackingRepository deleteCourseRepository;
    private ArrayList<Assessments> mAllAssessments = new ArrayList<>();
    private ArrayList<Assessments> mAssessments = new ArrayList<>();
    private AssessmentsViewModel mAssessmentsViewModel;
    private Button editCourseButton;
    private Button deleteCourseButton;
    private Button addAssessmentButton;
    private Button homeScreenButton;
    private String shareNotes;
    private TextView mTitle;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mStatus;
    private TextView mMentorName;
    private TextView mMentorPhone;
    private TextView mMentorEmail;
    private TextView mCourseNotes;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Course Detail");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle = findViewById(R.id.view_course_title);
        mStartDate = findViewById(R.id.view_course_start_date);
        mEndDate = findViewById(R.id.view_course_end_date);
        mStatus = findViewById(R.id.view_course_status);
        mMentorName = findViewById(R.id.view_course_mentor);
        mMentorPhone = findViewById(R.id.view_course_mentor_phone);
        mMentorEmail = findViewById(R.id.view_course_mentor_email);
        mCourseNotes = findViewById(R.id.view_course_notes);
        addAssessmentButton = findViewById(R.id.add_assessment_button);
        deleteCourseButton = findViewById(R.id.delete_course_button);
        editCourseButton = findViewById(R.id.edit_course_button);
        homeScreenButton = findViewById(R.id.view_course_home_button);

        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssessment();
            }
        });
        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });
        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCourse();
            }
        });
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.assessment_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AssessmentsAdapter assessmentsAdapter = new AssessmentsAdapter(mAssessments, this);
        recyclerView.setAdapter(assessmentsAdapter);

        mAssessmentsViewModel = ViewModelProviders.of(this).get(AssessmentsViewModel.class);
        mAssessmentsViewModel.getAllAssessments().observe(this, new Observer<List<Assessments>>() {
            @Override
            public void onChanged(List<Assessments> assessments) {
                assessmentsAdapter.setAssessmentsList(mAssessments);
            }
        });

        deleteCourseRepository = new ProgressTrackingRepository(this);

        getIncomingIntent();
        setCourse();
        retrieveAssessments();
    }

    private void addAssessment() {
        Intent intent3 = new Intent(this, AddAssessmentActivity.class);
        intent3.putExtra(Constants.INTENT_EDIT_COURSE, course);
        startActivity(intent3);
    }

    private void deleteCourse() {
        deleteCourseRepository.delete(course);
        Intent intent2 = new Intent(this, TermsActivity.class);
        startActivity(intent2);
    }

    private void editCourse() {
        Intent intent = new Intent(this, EditCourseActivity.class);
        intent.putExtra(Constants.INTENT_EDIT_COURSE, course);
        startActivity(intent);
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("selected_course")) {
            course = getIntent().getParcelableExtra("selected_course");
            courseId = course.getCourseId();
        }
    }

    private void setCourse() {
        mTitle.setText(course.getCourseTitle());
        mStartDate.setText(course.getCourseStartDate().toString());
        mEndDate.setText(course.getCourseEndDate().toString());
        mStatus.setText(course.getCourseStatus());
        mMentorName.setText(course.getCourseMentorName());
        mMentorPhone.setText(course.getCourseMentorPhone());
        mMentorEmail.setText(course.getCourseMentorEmail());
        mCourseNotes.setText(course.getCourseNotes());
    }

    private void retrieveAssessments() {
        // populate list with assessments associated with the selected course
        mAssessmentsViewModel.getAllAssessments().observe(this, new Observer<List<Assessments>>() {
            @Override
            public void onChanged(List<Assessments> assessments) {
                if (mAllAssessments.size() > 0) {
                    mAllAssessments.clear();
                }
                if (mAllAssessments != null) {
                    mAllAssessments.addAll(assessments);
                }

                if (mAssessments.size() > 0) {
                    mAssessments.clear();
                }

                for (int i = 0; i < mAllAssessments.size(); i++) {
                    if (mAllAssessments.get(i).getCourseIdFk() == courseId) {
                        mAssessments.add(mAllAssessments.get(i));
                    }
                }
            }
        });
    }

    @Override
    public void onAssessmentClick(int position) {
        mAssessments.get(position);
        Intent intent = new Intent(this, ViewAssessmentActivity.class);
        intent.putExtra("selected_assessment", mAssessments.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share_notes) {
            shareNotes = course.getCourseNotes();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareNotes);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Course Notes");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
