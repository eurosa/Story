package com.ero.poro.story;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class TennisAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<TennisModel> tennisModelArrayList;
    private ArrayList<TennisModel> searchList;

    private InterstitialAd interstitialAd;
    private int i;
    // private FriendFilter friendFilter;

    public TennisAdapter(Context context, ArrayList<TennisModel> tennisModelArrayList, InterstitialAd mInterstitialAd) {

        this.context = context;
        this.tennisModelArrayList = tennisModelArrayList;
        if(tennisModelArrayList!=null) {
            this.searchList = tennisModelArrayList;
        }
        this.interstitialAd=mInterstitialAd;
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
            convertView = inflater.inflate(R.layout.lv_player, null, true);

           /* holder.adView = convertView.findViewById(R.id.adView);*/


            holder.iv = convertView.findViewById(R.id.iv);
            holder.tvname = convertView.findViewById(R.id.name);
            holder.tvcountry = convertView.findViewById(R.id.country);
            holder.tvcity = convertView.findViewById(R.id.city);

            AdRequest adRequest = new AdRequest.Builder().build();
           // holder.adView.loadAd(adRequest);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Spanned htmlSpan = Html.fromHtml(Html.fromHtml(tennisModelArrayList.get(position).getDescription()).toString(), null, null);
        holder.tvcity.setText("Description: "+htmlSpan);
        Picasso.get().load(tennisModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText("Name: "+Html.fromHtml(tennisModelArrayList.get(position).getName()));
        holder.tvcountry.setText("Title: "+Html.fromHtml(tennisModelArrayList.get(position).getTitle()));
      //  holder.tvcity.setText("Description: "+ Html.fromHtml(tennisModelArrayList.get(position).getDescription()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }


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

                       if (tennisModelArrayList.get(i).getName().toLowerCase().startsWith(constraint.toString())) {
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

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
        protected AdView adView;
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




