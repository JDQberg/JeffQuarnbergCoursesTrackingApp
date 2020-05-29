package com.example.jeffquarnbergtrackingapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jeffquarnbergtrackingapp.Entities.Terms;

import java.util.List;

@Dao
public interface TermsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Terms terms);

    @Update
    void update(Terms terms);

    @Delete
    void delete(Terms terms);

    @Query("DELETE FROM terms_table")
    void deleteAllTerms();

    @Query("SELECT * FROM terms_table ORDER BY termId ASC")
    LiveData<List<Terms>> getAllTerms();

    @Query("SELECT * FROM terms_table ORDER BY termId ASC")
    List<Terms> getTermsList();
}
