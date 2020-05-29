package com.example.jeffquarnbergtrackingapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Terms;

import java.util.List;

public class TermsViewModel extends AndroidViewModel {
    private ProgressTrackingRepository repository;
    private LiveData<List<Terms>> allTerms;
    private List termsList;

    public TermsViewModel(@NonNull Application application) {
        super(application);
        repository = new ProgressTrackingRepository(application);
        allTerms = repository.getAllTerms();
        termsList = repository.getTermsList();
    }

    public void insert(Terms terms) {
        repository.insert(terms);
    }

    public void update(Terms terms) {
        repository.update(terms);
    }

    public void delete(Terms terms) {
        repository.delete(terms);
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public List<Terms> getTermsList() {
        return termsList;
    }

    public LiveData<List<Terms>> getAllTerms() {
        return allTerms;
    }
}
