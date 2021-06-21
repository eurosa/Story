package com.ero.poro.story;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLImageParser implements Html.ImageGetter {
    Context c;
    View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     * @param idNote
     */
    public URLImageParser(View t, Context c, String idNote) {
        this.c = c;
        this.container = t;

    }
    @Override
    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
        Log.d("urlget" ,"drawable url: "+""+source);

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;
        private URL url;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {

            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            try {
                urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0
                        + result.getIntrinsicHeight());
                Log.d("urlget", "love sex: " + "" + result);
            }catch (Exception e){
                Log.d("urlget", "drawable_image_error:" + "" + e);

            }
            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = (InputStream) new URL(urlString).getContent();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                Drawable drawable = new BitmapDrawable(c.getResources(), bmp);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            Log.d("urlget" ,"drawable sex: "+""+urlString);

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = urlConnection.getInputStream();

            return stream;
        }
    }
}
