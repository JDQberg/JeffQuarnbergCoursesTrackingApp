package com.example.jeffquarnbergtrackingapp.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.jeffquarnbergtrackingapp.Constants.Constants;

import java.util.Date;

@Entity(tableName = Constants.TERMS_TABLE_NAME)
public class Terms implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int termId;
    @ColumnInfo(name = Constants.TERMS_TABLE_COL_TERM_TITLE)
    private String termTitle;
    @ColumnInfo(name = Constants.TERMS_TABLE_COL_START)
    private Date termStartDate;
    @ColumnInfo(name = Constants.TERMS_TABLE_COL_END)
    private Date termEndDate;

    public Terms(String termTitle, Date termStartDate, Date termEndDate) {
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    protected Terms(Parcel in) {
        termId = in.readInt();
        termTitle = in.readString();
        termStartDate = (Date) in.readSerializable();
        termEndDate = (Date) in.readSerializable();
    }

    @Ignore
    public Terms() {

    }

    public static final Creator<Terms> CREATOR = new Creator<Terms>() {
        @Override
        public Terms createFromParcel(Parcel in) {
            return new Terms(in);
        }

        @Override
        public Terms[] newArray(int size) {
            return new Terms[size];
        }
    };

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getTermId() {
        return termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(termId);
        parcel.writeString(termTitle);
        parcel.writeSerializable(termStartDate);
        parcel.writeSerializable(termEndDate);
    }

    @Override
    public String toString() {
        return "Terms{" +
                "termId=" + termId +
                ", termTitle='" + termTitle + '\'' +
                ", termStartDate=" + termStartDate +
                ", termEndDate=" + termEndDate +
                '}';
    }
}
