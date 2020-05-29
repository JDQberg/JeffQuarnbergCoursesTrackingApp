package com.example.jeffquarnbergtrackingapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jeffquarnbergtrackingapp.Database.ProgressTrackingRepository;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;

import java.util.List;

public class CoursesViewModel extends AndroidViewModel {
    private ProgressTrackingRepository repository;
    private LiveData<List<Courses>> allCourses;
    private List<Courses> coursesList;

    public CoursesViewModel(@NonNull Application application) {
        super(application);
        repository = new ProgressTrackingRepository(application);
        allCourses = repository.getAllCourses();
        coursesList = repository.getCoursesList();
    }

    public void insert(Courses courses) {
        repository.insert(courses);
    }

    public void update(Courses courses) {
        repository.update(courses);
    }

    public void delete(Courses courses) {
        repository.delete(courses);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public List<Courses> getCoursesList() {
        return coursesList;
    }

    public LiveData<List<Courses>> getAllCourses() {
        return allCourses;
    }
}
