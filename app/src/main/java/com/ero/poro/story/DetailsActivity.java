package com.ero.poro.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String idNote;
    public TextView coiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);/*showing Back button on toolbar*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent in = getIntent();
        idNote = in.getStringExtra("country");
        coiu=findViewById(R.id.wer);
        coiu.setText(idNote);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }


//Click on Bac button to back in Parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
