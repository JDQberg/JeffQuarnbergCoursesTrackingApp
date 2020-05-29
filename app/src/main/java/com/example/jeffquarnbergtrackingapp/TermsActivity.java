package com.example.jeffquarnbergtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeffquarnbergtrackingapp.Entities.Terms;
import com.example.jeffquarnbergtrackingapp.ViewModel.TermsViewModel;
import com.example.jeffquarnbergtrackingapp.ui.TermsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TermsActivity extends AppCompatActivity implements TermsAdapter.OnTermListener {

    private static final String TAG = "TermsActivity";
    private ArrayList<Terms> mTerms = new ArrayList<>();
    private Button addTermButton;
    private Button homeScreenButton;
    private TermsViewModel mTermsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addTermButton = findViewById(R.id.add_term_button);
        homeScreenButton = findViewById(R.id.terms_home_button);
        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, AddTermActivity.class);
                startActivity(intent);
            }
        });
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.term_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TermsAdapter adapter = new TermsAdapter(mTerms, this);
        recyclerView.setAdapter(adapter);

        mTermsViewModel = ViewModelProviders.of(this).get(TermsViewModel.class);
        mTermsViewModel.getAllTerms().observe(this, new Observer<List<Terms>>() {
            @Override
            public void onChanged(List<Terms> termsEntities) {
                adapter.setTermsEntities(termsEntities);
            }
        });
        retrieveTerms();
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    private void retrieveTerms() {
        mTermsViewModel.getAllTerms().observe(this, new Observer<List<Terms>>() {
            @Override
            public void onChanged(List<Terms> termsEntities) {
                if (mTerms.size() > 0) {
                    mTerms.clear();
                }
                if (mTerms != null) {
                    mTerms.addAll(termsEntities);
                }
            }
        });
    }

    @Override
    public void onTermClick(int position) {
        mTerms.get(position);
        Intent intent = new Intent(this, ViewTermActivity.class);
        intent.putExtra("selected_term", mTerms.get(position));
        startActivity(intent);
    }

}
