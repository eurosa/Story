package com.ero.poro.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    String idNote;
    String image;
    public TextView coiu;
    public String imageURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView=findViewById(R.id.imagePreview);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);/*showing Back button on toolbar*/
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent in = getIntent();
        idNote = in.getStringExtra("city");
        coiu=findViewById(R.id.wer);
        coiu.setText(idNote);
        imageURL=in.getStringExtra("imageUrl");
        Picasso.get().load(imageURL).into(imageView);

        //imageView.setImageURI(imageView);

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
