package com.ero.poro.story;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    static String idNote;
    String image;
    public TextView coiu;
    public String imageURL;
    private Bitmap b;
    InputStream is;
    Drawable d = null;
    Bitmap bmpImage;
    public Drawable imageDraw;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
       // textView.setText(Html.fromHtml(Html.fromHtml(mHtmlString).toString()));
        //coiu.setText(Html.fromHtml(Html.fromHtml(idNote,Html.FROM_HTML_MODE_LEGACY).toString()));
      //  coiu.setText(Html.fromHtml(Html.fromHtml(idNote).toString()));

        // coiu.setText(Html.fromHtml(Html.fromHtml(idNote).toString(), imgGetter,null));
        imageURL=in.getStringExtra("imageUrl");
        Picasso.get().load(imageURL).into(imageView);
      //  new DownloadFilesTask ().execute(idNote);
        //   coiu.setText(Html.fromHtml(Html.fromHtml(idNote, imageGetter, null).toString()));
     //  coiu.setText(Html.fromHtml(Html.fromHtml(idNote).toString(), imageGetter3, null));

        String html = "Hello " +
                "<img src='http://www.gravatar.com/avatar/" +
                "f9dd8b16d54f483f22c0b7a7e3d840f9?s=32&d=identicon&r=PG'/>" +
                " This is a test " +
                "<img src='http://www.gravatar.com/avatar/a9317e7f0a78bb10a980cadd9dd035c9?s=32&d=identicon&r=PG'/>";
//http://youvu.000webhostapp.com/inventory-management-system/assests/images/stock/5064288225db290dd30773.png


        URLImageParser p = new URLImageParser(coiu, this,idNote);
        Spanned htmlSpan = Html.fromHtml(Html.fromHtml(idNote).toString(), p, null);
        coiu.setText(htmlSpan);



/*
        UrlImageParser p = new UrlImageParser(coiu, this);
        Spanned htmlSpan = Html.fromHtml(idNote, p, null);
        coiu.setText(htmlSpan);
*/
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
    private class DownloadFilesTask extends AsyncTask<String, Void, Html.ImageGetter> {
        @Override
        protected Html.ImageGetter doInBackground(String... urls) {
            Html.ImageGetter imageGetter =  new Html.ImageGetter() {

                @Override
                public Drawable getDrawable(final String source) {

                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try
                            {
                                URL url = new URL(source);
                                Log.d("Imahelink" ,"Test_source:"+url);

                                is = new BufferedInputStream(url.openStream());
                                //   b = BitmapFactory.decodeStream(is);
                                //Log.d("Imahelink" ,"Project Picture bit:"+url+""+b);

                                BufferedInputStream bufferedInputStream = new BufferedInputStream(is);

                                bmpImage = BitmapFactory.decodeStream(bufferedInputStream);

                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bmpImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                //Log.d("Imahelink" ,"bitmap: "+""+bmpImage);

                                d = new BitmapDrawable(getResources(), bmpImage);
                                Log.d("Imahelink" ,"drawable first: "+""+d);


                            } catch(Exception e){
                              //  Log.d("Imahelink" ,"Exception:" + e);


                            }
                        }



                    }).start();
                    //  Log.d("Imahelink" ,"drawable: "+""+d);
                    Log.d("Imahelink" ,"drawable second: "+""+d);

                    return d;
                }

            };

            return  imageGetter;
        }
        @Override
        protected void onPostExecute(Html.ImageGetter imageGetterBack) {
            // ImageView i = (ImageView)findViewById(R.id.imgView);
            // coiu.setText(Html.fromHtml(Html.fromHtml(idNote).toString(), imageGetterBack, null));

            //i.setImageBitmap(bitmap);
        }
    }



Html.ImageGetter imageGetter3 =  new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(final String source) {

            new Thread(new Runnable(){
                @Override
                public void run() {
            try
            {
                URL url = new URL(source);
               // Log.d("Imahelink" ,"Project Picture Test_source:"+url);

                is = new BufferedInputStream(url.openStream());
                //   b = BitmapFactory.decodeStream(is);
                //Log.d("Imahelink" ,"Project Picture bit:"+url+""+b);

                BufferedInputStream bufferedInputStream = new BufferedInputStream(is);

                bmpImage = BitmapFactory.decodeStream(bufferedInputStream);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmpImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
               // Log.d("Imahelink" ,"bitmap: "+""+bmpImage);

                d = new BitmapDrawable(getResources(), bmpImage);
                drawableImage(d);

                Log.d("Imahelink" ,"drawable first: "+""+d);


            } catch(Exception e){
                Log.d("Imahelink" ,"Exception:" + e);


            }
                }
            }).start();
           Log.d("Imahelink" ,"drawable: "+""+d);
           // Log.d("Imahelink" ,"drawable second: "+""+getImageDraw());

            return getImageDraw();
        }
    };

public  void drawableImage(Drawable f)
    {
        imageDraw=f;
    }
    public Drawable getImageDraw(){
    return imageDraw;
    }

//Click on Bac button to back in Parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
