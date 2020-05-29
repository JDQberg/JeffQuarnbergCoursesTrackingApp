package com.example.jeffquarnbergtrackingapp.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static com.example.jeffquarnbergtrackingapp.Constants.Constants.COURSES_TABLE_COL_TERMID_FK;
import static com.example.jeffquarnbergtrackingapp.Constants.Constants.TERMS_TABLE_COL_TERMID;

@Entity(tableName = Constants.COURSES_TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = Terms.class,
                parentColumns = TERMS_TABLE_COL_TERMID,
                childColumns = COURSES_TABLE_COL_TERMID_FK,
                onDelete = CASCADE
        ))
public class Courses implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_TITLE)
    private String courseTitle;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_START)
    private Date courseStartDate;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_ANTICIPATED_END)
    private Date courseEndDate;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_STATUS)
    private String courseStatus;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_MENTOR_NAME)
    private String courseMentorName;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_MENTOR_PHONE)
    private String courseMentorPhone;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_MENTOR_EMAIL)
    private String courseMentorEmail;
    @ColumnInfo(name = Constants.COURSES_TABLE_COL_NOTES)
    private String courseNotes;
    @ColumnInfo(name = COURSES_TABLE_COL_TERMID_FK)
    private int termIdFk;

    public Courses(String courseTitle, Date courseStartDate, Date courseEndDate, String courseStatus,
                   String courseMentorName, String courseMentorPhone, String courseMentorEmail,
                   String courseNotes, int termIdFk) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentorName = courseMentorName;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;
        this.courseNotes = courseNotes;
        this.termIdFk = termIdFk;
    }

    @Ignore
    public Courses() {
    }

    protected Courses(Parcel in) {
        courseId = in.readInt();
        courseTitle = in.readString();
        courseStartDate = (Date) in.readSerializable();
        courseEndDate = (Date) in.readSerializable();
        courseStatus = in.readString();
        courseMentorName = in.readString();
        courseMentorPhone = in.readString();
        courseMentorEmail = in.readString();
        courseNotes = in.readString();
        termIdFk = in.readInt();
    }

    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel in) {
            return new Courses(in);
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getTermIdFk() {
        return termIdFk;
    }

    public void setTermIdFk(int termIdFk) {
        this.termIdFk = termIdFk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(courseId);
        parcel.writeString(courseTitle);
        parcel.writeSerializable(courseStartDate);
        parcel.writeSerializable(courseEndDate);
        parcel.writeString(courseStatus);
        parcel.writeString(courseMentorName);
        parcel.writeString(courseMentorPhone);
        parcel.writeString(courseMentorEmail);
        parcel.writeString(courseNotes);
        parcel.writeInt(termIdFk);
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStartDate=" + courseStartDate +
                ", courseEndDate=" + courseEndDate +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseMentorName='" + courseMentorName + '\'' +
                ", courseMentorPhone='" + courseMentorPhone + '\'' +
                ", courseMentorEmail='" + courseMentorEmail + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                ", termIdFk=" + termIdFk +
                '}';
    }
}
