package com.example.jeffquarnbergtrackingapp.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static com.example.jeffquarnbergtrackingapp.Constants.Constants.ASSESSMENTS_TABLE_COL_COURSEID_FK;
import static com.example.jeffquarnbergtrackingapp.Constants.Constants.COURSES_TABLE_COL_COURSEID;

@Entity(tableName = Constants.ASSESSMENTS_TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = Courses.class,
                parentColumns = COURSES_TABLE_COL_COURSEID,
                childColumns = ASSESSMENTS_TABLE_COL_COURSEID_FK,
                onDelete = CASCADE
        ))
public class Assessments implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    @ColumnInfo(name = Constants.ASSESSMENTS_TABLE_COL_TITLE)
    private String assessmentTitle;
    @ColumnInfo(name = Constants.ASSESSMENTS_TABLE_COL_TYPE)
    private String assessmentType;
    @ColumnInfo(name = Constants.ASSESSMENTS_TABLE_COL_END)
    private Date assessmentEndDate;
    @ColumnInfo(name = Constants.ASSESSMENTS_TABLE_COL_STATUS)
    private String assessmentStatus;
    @ColumnInfo(name = Constants.ASSESSMENTS_TABLE_COL_COURSEID_FK)
    private int courseIdFk;

    public Assessments(String assessmentTitle, String assessmentType, Date assessmentEndDate,
                       String assessmentStatus, int courseIdFk) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentStatus = assessmentStatus;
        this.courseIdFk = courseIdFk;
    }

    @Ignore
    public Assessments() {
    }

    protected Assessments(Parcel in) {
        assessmentId = in.readInt();
        assessmentTitle = in.readString();
        assessmentType = in.readString();
        assessmentEndDate = (Date) in.readSerializable();
        assessmentStatus = in.readString();
        courseIdFk = in.readInt();
    }

    public static final Creator<Assessments> CREATOR = new Creator<Assessments>() {
        @Override
        public Assessments createFromParcel(Parcel in) {
            return new Assessments(in);
        }

        @Override
        public Assessments[] newArray(int size) {
            return new Assessments[size];
        }
    };

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(Date assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public int getCourseIdFk() {
        return courseIdFk;
    }

    public void setCourseIdFk(int courseIdFk) {
        this.courseIdFk = courseIdFk;
    }

    @NonNull
    @Override
    public String toString() {
        return "Assessments{" +
                "assessmentId=" + assessmentId +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", assessmentEndDate=" + assessmentEndDate +
                ", assessmentStatus=" + assessmentStatus +
                ", courseIdFk=" + courseIdFk +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(assessmentId);
        parcel.writeString(assessmentTitle);
        parcel.writeString(assessmentType);
        parcel.writeSerializable(assessmentEndDate);
        parcel.writeString(assessmentStatus);
        parcel.writeInt(courseIdFk);
    }
}
