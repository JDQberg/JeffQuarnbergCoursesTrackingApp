package com.example.jeffquarnbergtrackingapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;
import com.example.jeffquarnbergtrackingapp.DAO.AssessmentsDAO;
import com.example.jeffquarnbergtrackingapp.DAO.CoursesDAO;
import com.example.jeffquarnbergtrackingapp.DAO.TermsDAO;
import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.Entities.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class ProgressTrackingDatabase extends RoomDatabase {

    private static ProgressTrackingDatabase instance;

    public abstract TermsDAO termsDAO();

    public abstract CoursesDAO coursesDAO();

    public abstract AssessmentsDAO assessmentsDAO();

    public static synchronized ProgressTrackingDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProgressTrackingDatabase.class, Constants.PROGRESS_TRACKING_DB)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {

            super.onOpen(db);

        }
    };
}
