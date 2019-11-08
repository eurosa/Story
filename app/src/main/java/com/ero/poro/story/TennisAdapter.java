package com.ero.poro.story;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class TennisAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TennisModel> tennisModelArrayList;
    private InterstitialAd interstitialAd;

    public TennisAdapter(Context context, ArrayList<TennisModel> tennisModelArrayList, InterstitialAd mInterstitialAd) {

        this.context = context;
        this.tennisModelArrayList = tennisModelArrayList;
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

    private class ViewHolder {

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
        protected AdView adView;
    }



}
