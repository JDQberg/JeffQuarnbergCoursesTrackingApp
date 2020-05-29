package com.example.jeffquarnbergtrackingapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jeffquarnbergtrackingapp.DAO.AssessmentsDAO;
import com.example.jeffquarnbergtrackingapp.DAO.CoursesDAO;
import com.example.jeffquarnbergtrackingapp.DAO.TermsDAO;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.Entities.Terms;

import java.util.List;

public class ProgressTrackingRepository {
    private ProgressTrackingDatabase db;
    private TermsDAO termsDAO;
    private CoursesDAO coursesDAO;
    private AssessmentsDAO assessmentsDAO;
    private LiveData<List<Terms>> allTerms;
    private LiveData<List<Courses>> allCourses;
    private LiveData<List<Assessments>> allAssessments;
    private List termsList;
    private List coursesList;
    private List assessmentsList;

    public ProgressTrackingRepository(Context context) {
        db = ProgressTrackingDatabase.getInstance(context);
        termsDAO = db.termsDAO();
        coursesDAO = db.coursesDAO();
        assessmentsDAO = db.assessmentsDAO();
        allTerms = termsDAO.getAllTerms();
        termsList = termsDAO.getTermsList();
        allCourses = coursesDAO.getAllCourses();
        coursesList = coursesDAO.getCoursesList();
        allAssessments = assessmentsDAO.getAllAssessments();
        assessmentsList = assessmentsDAO.getAssessmentsList();
    }

    public void insert(Terms terms) {
        new InsertTermAsyncTask(termsDAO).execute(terms);
    }

    public void insert(Courses courses) {
        new InsertCourseAsyncTask(coursesDAO).execute(courses);
    }

    public void insert(Assessments assessments) {
        new InsertAssessmentAsyncTask(assessmentsDAO).execute(assessments);
    }

    public void update(Terms terms) {
        new UpdateTermAsyncTask(termsDAO).execute(terms);
    }

    public void update(Courses courses) {
        new UpdateCourseAsyncTask(coursesDAO).execute(courses);
    }

    public void update(Assessments assessments) {
        new UpdateAssessmentAsyncTask(assessmentsDAO).execute(assessments);
    }

    public void delete(Terms terms) {
        new DeleteTermAsyncTask(termsDAO).execute(terms);
    }

    public void delete(Courses courses) {
        new DeleteCourseAsyncTask(coursesDAO).execute(courses);
    }

    public void delete(Assessments assessments) {
        new DeleteAssessmentAsyncTask(assessmentsDAO).execute(assessments);
    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termsDAO).execute();
    }

    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(coursesDAO).execute();
    }

    public void deleteAllAssessments() {
        new DeleteAllAssessmentsAsyncTask(assessmentsDAO).execute();
    }

    public LiveData<List<Terms>> getAllTerms() {
        return allTerms;
    }

    public LiveData<List<Courses>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Assessments>> getAllAssessments() {
        return allAssessments;
    }

    public List<Terms> getTermsList() {
        return termsList;
    }

    public List<Courses> getCoursesList() {
        return coursesList;
    }

    public List<Assessments> getAssessmentsList() {
        return assessmentsList;
    }

    private static class InsertTermAsyncTask extends AsyncTask<Terms, Void, Void> {
        private TermsDAO termsDAO;

        private InsertTermAsyncTask(TermsDAO termsDAO) {
            this.termsDAO = termsDAO;
        }

        @Override
        protected Void doInBackground(Terms... termsEntities) {
            termsDAO.insert(termsEntities[0]);
            return null;
        }
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Courses, Void, Void> {
        private CoursesDAO coursesDAO;

        private InsertCourseAsyncTask(CoursesDAO coursesDAO) {
            this.coursesDAO = coursesDAO;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.insert(courses[0]);
            return null;
        }
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessments, Void, Void> {
        private AssessmentsDAO assessmentsDAO;

        private InsertAssessmentAsyncTask(AssessmentsDAO assessmentsDAO) {
            this.assessmentsDAO = assessmentsDAO;
        }

        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentsDAO.insert(assessments[0]);
            return null;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Terms, Void, Void> {
        private TermsDAO termsDAO;

        public UpdateTermAsyncTask(TermsDAO termsDAO) {
            this.termsDAO = termsDAO;
        }

        @Override
        protected Void doInBackground(Terms... termsEntities) {
            termsDAO.update(termsEntities[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Courses, Void, Void> {
        private CoursesDAO coursesDAO;

        private UpdateCourseAsyncTask(CoursesDAO coursesDAO) {
            this.coursesDAO = coursesDAO;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.update(courses[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessments, Void, Void> {
        private AssessmentsDAO assessmentsDAO;

        private UpdateAssessmentAsyncTask(AssessmentsDAO assessmentsDAO) {
            this.assessmentsDAO = assessmentsDAO;
        }

        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentsDAO.update(assessments[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Terms, Void, Void> {
        private TermsDAO termsDAO;

        private DeleteTermAsyncTask(TermsDAO termsDAO) {
            this.termsDAO = termsDAO;
        }

        @Override
        protected Void doInBackground(Terms... termsEntities) {
            termsDAO.delete(termsEntities[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Courses, Void, Void> {
        private CoursesDAO coursesDAO;

        private DeleteCourseAsyncTask(CoursesDAO coursesDAO) {
            this.coursesDAO = coursesDAO;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessments, Void, Void> {
        private AssessmentsDAO assessmentsDAO;

        private DeleteAssessmentAsyncTask(AssessmentsDAO assessmentsDAO) {
            this.assessmentsDAO = assessmentsDAO;
        }

        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentsDAO.delete(assessments[0]);
            return null;
        }
    }

    private static class DeleteAllTermsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermsDAO termsDAO;

        private DeleteAllTermsAsyncTask(TermsDAO termsDAO) {
            this.termsDAO = termsDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termsDAO.deleteAllTerms();
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CoursesDAO coursesDAO;

        private DeleteAllCoursesAsyncTask(CoursesDAO coursesDAO) {
            this.coursesDAO = coursesDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            coursesDAO.deleteAllCourses();
            return null;
        }
    }

    private static class DeleteAllAssessmentsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AssessmentsDAO assessmentsDAO;

        private DeleteAllAssessmentsAsyncTask(AssessmentsDAO assessmentsDAO) {
            this.assessmentsDAO = assessmentsDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assessmentsDAO.deleteAllAssessments();
            return null;
        }
    }
}
