package com.ero.poro.story;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class TennisAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<TennisModel> tennisModelArrayList;
    private ArrayList<TennisModel> searchList;

    //private InterstitialAd interstitialAd;
    private int i;
    // private FriendFilter friendFilter;

    public TennisAdapter(Context context, ArrayList<TennisModel> tennisModelArrayList) {

        this.context = context;
        this.tennisModelArrayList = tennisModelArrayList;
        if(tennisModelArrayList!=null) {
            this.searchList = tennisModelArrayList;
        }
        //this.interstitialAd=mInterstitialAd;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return tennisModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tennisModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.lv_player, null, true);
            //snip
            convertView.setBackgroundResource(R.drawable.customshape);
            //snip

            /* holder.adView = convertView.findViewById(R.id.adView);*/


            holder.iv = convertView.findViewById(R.id.iv);
            holder.tvname = convertView.findViewById(R.id.name);
            holder.tvcountry = convertView.findViewById(R.id.country);
            holder.tvcity = convertView.findViewById(R.id.city);
             //setUpFadeAnimation(holder.tvcity);
            holder.tvcity.setSelected(true);
            AdRequest adRequest = new AdRequest.Builder().build();
           // holder.adView.loadAd(adRequest);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Spanned htmlCity = HtmlCompat.fromHtml(HtmlCompat.fromHtml(tennisModelArrayList.get(position).getDescription(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString(), HtmlCompat.FROM_HTML_MODE_LEGACY,null, null);
        Spanned htmlName = HtmlCompat.fromHtml(HtmlCompat.fromHtml(tennisModelArrayList.get(position).getName(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString(), HtmlCompat.FROM_HTML_MODE_LEGACY,null, null);
        Spanned htmlTitle = HtmlCompat.fromHtml(HtmlCompat.fromHtml(tennisModelArrayList.get(position).getTitle(),HtmlCompat.FROM_HTML_MODE_LEGACY).toString(), HtmlCompat.FROM_HTML_MODE_LEGACY, null, null);

        SpannableString spanCity = new SpannableString(htmlCity);
        spanCity.setSpan(new UnderlineSpan(), 0, spanCity.length(), 0);
        spanCity.setSpan(new StyleSpan(Typeface.BOLD), 0, spanCity.length(), 0);
        spanCity.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanCity.length(), 0);
        //text.setText(spanString);

        SpannableString spanName = new SpannableString(htmlName);
        spanName.setSpan(new UnderlineSpan(), 0, spanName.length(), 0);
        spanName.setSpan(new StyleSpan(Typeface.BOLD), 0, spanName.length(), 0);
        spanName.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanName.length(), 0);

        SpannableString spanTitle = new SpannableString(htmlTitle);
        spanTitle.setSpan(new UnderlineSpan(), 0, spanTitle.length(), 0);
        spanTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, spanTitle.length(), 0);
        spanTitle.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanTitle.length(), 0);

        holder.tvcity.setText(spanCity);
       // holder.tvcity.setSelected(true);
try {
    ImageFileFilter file = new ImageFileFilter(new File(tennisModelArrayList.get(position).getImgURL()));
    if (file.accept(new File(tennisModelArrayList.get(position).getImgURL()))) {
        Picasso.get().load(tennisModelArrayList.get(position).getImgURL()).into(holder.iv);

    } else {

      //  Picasso.get().load(R.drawable.ic_star_of_david_background).into(holder.iv);
        Log.d("imagepicasso", "" + tennisModelArrayList.get(position).getImgURL());

    }
}catch(Exception e){
    e.printStackTrace();
}
       //Picasso.get().load(tennisModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText(spanName);
        holder.tvcountry.setText(spanTitle);
       //holder.tvcity.setText("Description: "+ Html.fromHtml(tennisModelArrayList.get(position).getDescription()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }*/


                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("city", tennisModelArrayList.get(position).getDescription());
                intent.putExtra("imageUrl", tennisModelArrayList.get(position).getImgURL());

                startActivity(context,intent,null);
            }
        });


        return convertView;
    }
/*
    @Override
    public Filter getFilter() {
        if (friendFilter == null) {

            friendFilter = new FriendFilter();

        }

        return friendFilter;
    }

*/

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // searchList.clear();
                tennisModelArrayList = (ArrayList<TennisModel>) results.values; // has the filtered values
                  // notifies the data with new filtered values
               // searchList.get(27).getName();
               // Log.d("sara",searchList.get(0).getDescription());

                notifyDataSetChanged();


            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                i=0;
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<TennisModel> FilteredArrList = new ArrayList<>();

               // if (tennisModelArrayList == null) {
                    tennisModelArrayList = new ArrayList<>(searchList); // saves the original data in mOriginalValues
               // }

                Log.d("Size","Sara"+searchList.size()+" "+tennisModelArrayList.size());

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = tennisModelArrayList.size();
                    results.values = tennisModelArrayList;
                   // Toast.makeText(context, results.count, Toast.LENGTH_SHORT).show();
                } else {
                    constraint = constraint.toString().toLowerCase();

                    while ( i < tennisModelArrayList.size()) {
                     //   String data = tennisModelArrayList.get(i).getName() + " " +

                        //           tennisModelArrayList.get(i).getTitle();

                      //  Toast.makeText(context, constraint, Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(context, "lob "+tennisModelArrayList.size(), Toast.LENGTH_SHORT).show();

                       if (tennisModelArrayList.get(i).getName().toLowerCase().startsWith(constraint.toString())||tennisModelArrayList.get(i).getTitle().toLowerCase().startsWith(constraint.toString())) {
                        FilteredArrList.add(tennisModelArrayList.get(i));
                         //  Log.d("increment","sexy "+i+" Tennis: "+tennisModelArrayList.get(i).getName());

                       }
                      //  Toast.makeText(context, "ince "+i, Toast.LENGTH_SHORT).show();

                        i++;
                    }
                    Log.d("increment","sexy "+i+" Tennis: "+tennisModelArrayList.size()+""+searchList.size());

                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public ArrayList<TennisModel> getSearchList()
    {
        return searchList;
    }


    private class ViewHolder {

        TextView tvname, tvcountry, tvcity;
        ImageView iv;
        private AdView adView;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    /*
    private class FriendFilter extends Filter {

        public ArrayList<TennisModel> filteredList;
        ArrayList<TennisModel> tempList;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Toast.makeText(context, "janeman", Toast.LENGTH_SHORT).show();

            FilterResults filterResults = new FilterResults();
           // if (constraint!=null && constraint.length()>0) {
             //   tempList = new ArrayList<>();

                // search content in friend list
               // for (TennisModel model : tennisModelArrayList) {
                  //  if (model.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    //    Toast.makeText(context, model.getName()+"#Sex#"+constraint, Toast.LENGTH_SHORT).show();

                       // tempList.add(model);
                   // }
               // }
                //Toast.makeText(context, "janeman", Toast.LENGTH_SHORT).show();


              //  adapter = new ListViewAdapter(context.getApplicationContext(), tempList);

                //listview.setAdapter(adapter);
               // filteredList=tempList;
                //filterResults.count = tempList.size();
                //filterResults.values = tempList;
          //  } else {
               // filteredList=tempList;

                //filterResults.count = tennisModelArrayList.size();
                //filterResults.values = tennisModelArrayList;
          //  }



            return filterResults;
        }

*/

    private void setUpFadeAnimation(final TextView textView) {
        // Start from 0.1f if you desire 90% fade animation
        final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);//1000
        fadeIn.setStartOffset(500);//3000
        // End to 0.1f if you desire 90% fade animation
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);//1000
        fadeOut.setStartOffset(500);//3000

        fadeIn.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeOut when fadeIn ends (continue)
                textView.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start fadeIn when fadeOut ends (repeat)
                textView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        textView.startAnimation(fadeOut);
    }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        //@SuppressWarnings("unchecked")
        //@Override
        //protected void publishResults(CharSequence constraint, FilterResults results) {

           // filteredList = (ArrayList<TennisModel>) results.values;

         //   notifyDataSetChanged();
       // }




    }




