package com.example.jeffquarnbergtrackingapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;

import java.util.List;

public class AssessmentsViewModel extends AndroidViewModel {
    private ProgressTrackingRepository repository;
    private LiveData<List<Assessments>> allAssessments;
    private List<Assessments> assessmentsList;

    public AssessmentsViewModel(@NonNull Application application) {
        super(application);
        repository = new ProgressTrackingRepository(application);
        allAssessments = repository.getAllAssessments();
        assessmentsList = repository.getAssessmentsList();
    }

    public void insert(Assessments assessments) {
        repository.insert(assessments);
    }

    public void update(Assessments assessments) {
        repository.update(assessments);
    }

    public void delete(Assessments assessments) {
        repository.delete(assessments);
    }

    public void deleteAllAssessments() {
        repository.deleteAllAssessments();
    }

    public List<Assessments> getAssessmentsList() {
        return assessmentsList;
    }

    public LiveData<List<Assessments>> getAllAssessments() {
        return allAssessments;
    }
}
