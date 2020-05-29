package com.example.jeffquarnbergtrackingapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jeffquarnbergtrackingapp.Entities.Assessments;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("DELETE FROM assessments_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments_table ORDER BY assessmentId ASC")
    LiveData<List<Assessments>> getAllAssessments();

    @Query("SELECT * FROM assessments_table ORDER BY assessmentId ASC")
    List<Assessments> getAssessmentsList();
}
