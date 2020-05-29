package com.example.jeffquarnbergtrackingapp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jeffquarnbergtrackingapp.DAO.AssessmentsDAO;
import com.example.jeffquarnbergtrackingapp.DAO.CoursesDAO;
import com.example.jeffquarnbergtrackingapp.DAO.TermsDAO;
import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingDatabase;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.Entities.Terms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PopulateDb extends AppCompatActivity {
    private static final String TAG = "PopulateDb";
    Terms term1 = new Terms();
    Terms term2 = new Terms();
    Courses course1 = new Courses();
    Assessments assessment1 = new Assessments();
    Assessments assessment2 = new Assessments();
    Assessments assessment3 = new Assessments();
    Assessments assessment4 = new Assessments();
    ProgressTrackingDatabase db;
    private TermsDAO termsDAO;
    private CoursesDAO coursesDAO;
    private AssessmentsDAO assessmentsDAO;
    private int termIdFk;
    private int courseIdFk;

    public void addTestData(Context context) {

        db = ProgressTrackingDatabase.getInstance(context);
        termsDAO = db.termsDAO();
        coursesDAO = db.coursesDAO();
        assessmentsDAO = db.assessmentsDAO();

        clearDb();
        addTerm();
        addCourse();
        addAssessment();
    }

    private void addAssessment() {

        Date startDate, endDate;
        List<Courses> coursesList = coursesDAO.getCoursesList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 31);
        endDate = calendar.getTime();

        courseIdFk = coursesList.get(0).getCourseId();

        assessment1.setAssessmentTitle("Painting Happy Little Trees");
        assessment1.setAssessmentType("PA");
        assessment1.setAssessmentEndDate(endDate);
        assessment1.setAssessmentStatus("Passed");
        assessment1.setCourseIdFk(courseIdFk);

        calendar.set(2019, 1, 28);
        endDate = calendar.getTime();
        assessment2.setAssessmentTitle("Building Happy Little Clouds");
        assessment2.setAssessmentType("PA");
        assessment2.setAssessmentEndDate(endDate);
        assessment2.setAssessmentStatus("Passed");
        assessment2.setCourseIdFk(courseIdFk);

        calendar.set(2019, 2, 15);
        endDate = calendar.getTime();
        assessment3.setAssessmentTitle("Beat The Devil Out Of The Brush");
        assessment3.setAssessmentType("PA");
        assessment3.setAssessmentEndDate(endDate);
        assessment3.setAssessmentStatus("Passed");
        assessment3.setCourseIdFk(courseIdFk);

        calendar.set(2019, 2, 31);
        endDate = calendar.getTime();
        assessment4.setAssessmentTitle("Quaint Little Cabins");
        assessment4.setAssessmentType("PA");
        assessment4.setAssessmentEndDate(endDate);
        assessment4.setAssessmentStatus("Passed");
        assessment4.setCourseIdFk(courseIdFk);

        assessmentsDAO.insert(assessment1);
        assessmentsDAO.insert(assessment2);
        assessmentsDAO.insert(assessment3);
        assessmentsDAO.insert(assessment4);

    }

    private void addCourse() {
        Date startDate, endDate;
        List<Terms> termsList = termsDAO.getTermsList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 1);
        startDate = calendar.getTime();
        calendar.set(2019, 0, 31);
        endDate = calendar.getTime();

        termIdFk = termsList.get(0).getTermId();

        course1.setCourseTitle("P-101 Intro to Painting");
        course1.setCourseStartDate(startDate);
        course1.setCourseEndDate(endDate);
        course1.setCourseStatus("Completed");
        course1.setCourseMentorName("Bob Ross");
        course1.setCourseMentorPhone("101-555-1234");
        course1.setCourseMentorEmail("Bob@painting.com");
        course1.setCourseNotes("This is the place for notes");
        course1.setTermIdFk(termIdFk);

        coursesDAO.insert(course1);

    }

    private void addTerm() {
        Date startDate, endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 0, 1);
        startDate = calendar.getTime();
        calendar.set(2019, 5, 30);
        endDate = calendar.getTime();

        term1.setTermTitle("Winter 2019");
        term1.setTermStartDate(startDate);
        term1.setTermEndDate(endDate);

        calendar.set(2019, 6, 1);
        startDate = calendar.getTime();
        calendar.set(2019, 11, 31);
        endDate = calendar.getTime();

        term2.setTermTitle("Summer 2019");
        term2.setTermStartDate(startDate);
        term2.setTermEndDate(endDate);

        termsDAO.insert(term1);
        termsDAO.insert(term2);
    }

    private void clearDb() {
        coursesDAO.deleteAllCourses();
        termsDAO.deleteAllTerms();
    }

}
