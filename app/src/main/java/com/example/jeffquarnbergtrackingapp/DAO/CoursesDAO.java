package com.example.jeffquarnbergtrackingapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jeffquarnbergtrackingapp.Entities.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);

    @Query("DELETE FROM courses_table")
    void deleteAllCourses();

    @Query("SELECT * FROM courses_table ORDER BY courseId ASC")
    LiveData<List<Courses>> getAllCourses();

    @Query("SELECT * FROM courses_table ORDER BY courseId ASC")
    List<Courses> getCoursesList();
}
