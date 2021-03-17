package com.example.jeffquarnbergtrackingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Entities.Assessments;
import com.example.jeffquarnbergtrackingapp.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentsHolder> {

    private List<Assessments> mAssessments = new ArrayList<>();
    private OnAssessmentListener onAssessmentListener;

    public AssessmentsAdapter(ArrayList<Assessments> mAssessments, OnAssessmentListener onAssessmentListener) {
        this.mAssessments = mAssessments;
        this.onAssessmentListener = onAssessmentListener;
    }

    public interface OnAssessmentListener {
        void onAssessmentClick(int position);
    }

    @NonNull
    @Override
    public AssessmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessments_list_item, parent, false);
        return new AssessmentsHolder(view, onAssessmentListener);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentsHolder holder, int position) {

        Assessments assessments = mAssessments.get(position);
        holder.textViewAssessmentTitle.setText(assessments.getAssessmentTitle());
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public void setAssessmentsList(List<Assessments> mAssessments) {
        this.mAssessments = mAssessments;
        notifyDataSetChanged();
    }

    public class AssessmentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewAssessmentTitle;
        private TextView textViewAssessmentStart;
        private TextView textViewAssessmentEnd;

        OnAssessmentListener onAssessmentListener;

        public AssessmentsHolder(@NonNull View itemView, OnAssessmentListener onAssessmentListener) {
            super(itemView);
            textViewAssessmentTitle = itemView.findViewById(R.id.text_view_assessment_title);

            this.onAssessmentListener = onAssessmentListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAssessmentListener.onAssessmentClick(getAdapterPosition());
        }
    }
}
