package com.example.jeffquarnbergtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;
import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.Entities.Terms;
import com.example.jeffquarnbergtrackingapp.ViewModel.CoursesViewModel;
import com.example.jeffquarnbergtrackingapp.ViewModel.TermsViewModel;
import com.example.jeffquarnbergtrackingapp.ui.CoursesAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewTermActivity extends AppCompatActivity implements CoursesAdapter.OnCourseListener {

    private static final String TAG = "ViewTermActivity";
    Terms term;
    private ProgressTrackingRepository deleteTermRepository;
    private TermsViewModel termsViewModel;
    private CoursesViewModel mCoursesViewModel;
    private ArrayList<Courses> mAllCourses = new ArrayList<>();
    private ArrayList<Courses> mCourses = new ArrayList<>();
    private Button editTermButton;
    private Button deleteTermButton;
    private Button addCourseButton;
    private Button homeScreenButton;
    private TextView mTitle;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mProgress;
    private int termId;
    private double numComplete = 0;
    private double percentComplete = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = findViewById(R.id.view_term_title);
        mStartDate = findViewById(R.id.view_term_start_date);
        mEndDate = findViewById(R.id.view_term_end_date);
        mProgress = findViewById(R.id.view_term_progress);
        deleteTermRepository = new ProgressTrackingRepository(this);
        addCourseButton = findViewById(R.id.add_course_button);
        deleteTermButton = findViewById(R.id.delete_term_button);
        editTermButton = findViewById(R.id.edit_term_button);
        homeScreenButton = findViewById(R.id.view_term_home_button);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });
        deleteTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTerm();
            }
        });
        editTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTerm();
            }
        });
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        getIncomingIntent();


        RecyclerView recyclerView = findViewById(R.id.course_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CoursesAdapter adapter = new CoursesAdapter(mCourses, this);
        Log.d(TAG, "retrieveCourses: mCourses" + mCourses);
        recyclerView.setAdapter(adapter);

        mCoursesViewModel = ViewModelProviders.of(this).get(CoursesViewModel.class);
        mCoursesViewModel.getAllCourses().observe(this, new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                adapter.setCoursesList(mCourses);
            }
        });

        retrieveCourses();
        setTerm();

    }

    private void addCourse() {
        Intent intent3 = new Intent(this, AddCourseActivity.class);
        intent3.putExtra(Constants.INTENT_EDIT_TERM, term);
        startActivity(intent3);
    }

    private void deleteTerm() {
        if (mCourses.size() == 0) {
            deleteTermRepository.delete(term);
            Intent intent2 = new Intent(this, HomeScreenActivity.class);
            startActivity(intent2);
        } else {
            Toast.makeText(getApplicationContext(), "Can't delete a Term if it has a Course assigned", Toast.LENGTH_LONG).show();
        }
    }

    private void editTerm() {
        Intent intent = new Intent(this, EditTermActivity.class);
        intent.putExtra("edit_term", term);
        startActivity(intent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("selected_term")) {
            term = getIntent().getParcelableExtra("selected_term");
            termId = term.getTermId();
        }
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void setTerm() {
        mTitle.setText(term.getTermTitle());
        mStartDate.setText(term.getTermStartDate().toString());
        mEndDate.setText(term.getTermEndDate().toString());
    }

    private void retrieveCourses() {
        // populate list with courses associated with the selected term
        mCoursesViewModel.getAllCourses().observe(this, new Observer<List<Courses>>() {

            @Override
            public void onChanged(List<Courses> courses) {
                mAllCourses.clear();
                mAllCourses.addAll(courses);

                if (mCourses.size() > 0) {
                    mCourses.clear();
                }

                for (int i = 0; i < mAllCourses.size(); i++) {
                    if (mAllCourses.get(i).getTermIdFk() == termId) {
                        mCourses.add(mAllCourses.get(i));
                    }
                }
                calculateProgress();
            }
        });
    }

    private void calculateProgress() {
        for (int i = 0; i < mCourses.size(); i++) {
            if (mCourses.get(i).getCourseStatus().contains("Completed")) {
                numComplete++;
            }
        }
        if (numComplete != 0) {
            percentComplete = (numComplete / mCourses.size()) * 100;
        } else {
            percentComplete = 0;
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        mProgress.setText(String.format("You are currently %s%% complete with this Term's assigned courses.", numberFormat.format(percentComplete)));
    }

    @Override
    public void onCourseClick(int position) {
        mCourses.get(position);
        Intent intent = new Intent(this, ViewCourseActivity.class);
        intent.putExtra("selected_course", mCourses.get(position));
        startActivity(intent);
    }
}