package com.example.jeffquarnbergtrackingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Entities.Terms;
import com.example.jeffquarnbergtrackingapp.R;

import java.util.ArrayList;
import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsHolder> {
    private List<Terms> termsEntities = new ArrayList<>();
    private OnTermListener onTermListener;

    public TermsAdapter(ArrayList<Terms> termsEntities, OnTermListener onTermListener) {
        this.termsEntities = termsEntities;
        this.onTermListener = onTermListener;
    }

    public interface OnTermListener {
        void onTermClick(int position);
    }

    @NonNull
    @Override
    public TermsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_list_item, parent, false);
        return new TermsHolder(view, onTermListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsHolder holder, int position) {
        Terms currentTerm = termsEntities.get(position);
        holder.textViewTermTitle.setText(currentTerm.getTermTitle());
        holder.textViewTermStart.setText(currentTerm.getTermStartDate().toString());
        holder.textViewTermEnd.setText(currentTerm.getTermEndDate().toString());
    }

    @Override
    public int getItemCount() {
        return termsEntities.size();
    }

    public void setTermsEntities(List<Terms> termsEntities) {
        this.termsEntities = termsEntities;
        notifyDataSetChanged();
    }

    public class TermsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTermTitle;
        private TextView textViewTermStart;
        private TextView textViewTermEnd;

        OnTermListener onTermListener;


        public TermsHolder(@NonNull View itemView, OnTermListener onTermListener) {
            super(itemView);
            textViewTermTitle = itemView.findViewById(R.id.text_view_title);
            textViewTermStart = itemView.findViewById(R.id.text_view_start);
            textViewTermEnd = itemView.findViewById(R.id.text_view_end);

            this.onTermListener = onTermListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTermListener.onTermClick(getAdapterPosition());
        }
    }
}
