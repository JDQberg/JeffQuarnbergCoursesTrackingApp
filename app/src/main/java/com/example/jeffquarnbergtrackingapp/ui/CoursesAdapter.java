package com.example.jeffquarnbergtrackingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Entities.Courses;
import com.example.jeffquarnbergtrackingapp.R;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesHolder> {

    private List<Courses> mCourses = new ArrayList<>();
    private OnCourseListener onCourseListener;

    public CoursesAdapter(ArrayList<Courses> mCourses, OnCourseListener onCourseListener) {
        this.mCourses = mCourses;
        this.onCourseListener = onCourseListener;
    }

    public interface OnCourseListener {
        void onCourseClick(int position);
    }

    @NonNull
    @Override
    public CoursesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_list_item, parent, false);
        return new CoursesHolder(view, onCourseListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CoursesHolder holder, int position) {
        Courses courses = mCourses.get(position);
        holder.textViewCourseTitle.setText(courses.getCourseTitle());
        holder.textViewCourseStart.setText(courses.getCourseStartDate().toString());
        holder.textViewCourseEnd.setText(courses.getCourseEndDate().toString());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCoursesList(List<Courses> mCourses) {
        this.mCourses = mCourses;
        notifyDataSetChanged();
    }

    public class CoursesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewCourseTitle;
        private TextView textViewCourseStart;
        private TextView textViewCourseEnd;

        OnCourseListener onCourseListener;

        public CoursesHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
            super(itemView);
            textViewCourseTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewCourseStart = itemView.findViewById(R.id.text_view_course_start);
            textViewCourseEnd = itemView.findViewById(R.id.text_view_course_end);

            this.onCourseListener = onCourseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }
}
