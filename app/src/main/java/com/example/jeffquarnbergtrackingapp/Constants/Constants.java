package com.example.jeffquarnbergtrackingapp.Constants;

public class Constants {

    public static final String PROGRESS_TRACKING_DB = "wgu_progress_tracking_database";
    public static final String TERMS_TABLE_NAME = "terms_table";
    public static final String TERMS_TABLE_COL_TERMID = "termId";
    public static final String TERMS_TABLE_COL_TERM_TITLE = "term_title";
    public static final String TERMS_TABLE_COL_START = "term_start_date";
    public static final String TERMS_TABLE_COL_END = "term_end_date";

    public static final String COURSES_TABLE_NAME = "courses_table";
    public static final String COURSES_TABLE_COL_COURSEID = "courseId";
    public static final String COURSES_TABLE_COL_TITLE = "course_title";
    public static final String COURSES_TABLE_COL_START = "course_start_date";
    public static final String COURSES_TABLE_COL_ANTICIPATED_END = "course_anticipated_end";
    public static final String COURSES_TABLE_COL_STATUS = "course_status";
    public static final String COURSES_TABLE_COL_MENTOR_NAME = "course_mentor_name";
    public static final String COURSES_TABLE_COL_MENTOR_PHONE = "course_mentor_phone";
    public static final String COURSES_TABLE_COL_MENTOR_EMAIL = "course_mentor_email";
    public static final String COURSES_TABLE_COL_NOTES = "course_notes";
    public static final String COURSES_TABLE_COL_TERMID_FK = "term_id_fk";

    public static final String ASSESSMENTS_TABLE_NAME = "assessments_table";
    public static final String ASSESSMENTS_TABLE_COL_ASSESSMENTID = "assessmentId";
    public static final String ASSESSMENTS_TABLE_COL_TITLE = "assessment_title";
    public static final String ASSESSMENTS_TABLE_COL_TYPE = "assessment_type";
    public static final String ASSESSMENTS_TABLE_COL_START = "assessment_start";
    public static final String ASSESSMENTS_TABLE_COL_END = "assessment_end";
    public static final String ASSESSMENTS_TABLE_COL_STATUS = "assessment_status";
    public static final String ASSESSMENTS_TABLE_COL_COURSEID_FK = "course_id_fk";

    public static final String INTENT_EDIT_TERM = "edit_term";
    public static final String INTENT_EDIT_COURSE = "edit_course";
    public static final String INTENT_EDIT_ASSESSMENT = "edit_assessment";

}
