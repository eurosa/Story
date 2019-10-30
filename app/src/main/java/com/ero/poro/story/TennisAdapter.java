package com.ero.poro.story;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class TennisAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TennisModel> tennisModelArrayList;

    public TennisAdapter(Context context, ArrayList<TennisModel> tennisModelArrayList) {

        this.context = context;
        this.tennisModelArrayList = tennisModelArrayList;
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

            holder.iv = convertView.findViewById(R.id.iv);
            holder.tvname = convertView.findViewById(R.id.name);
            holder.tvcountry = convertView.findViewById(R.id.country);
            holder.tvcity = convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.get().load(tennisModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText("Name: "+tennisModelArrayList.get(position).getName());
        holder.tvcountry.setText("Country: "+tennisModelArrayList.get(position).getCountry());
        holder.tvcity.setText("City: "+tennisModelArrayList.get(position).getCity());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("city", tennisModelArrayList.get(position).getCity());
                intent.putExtra("imageUrl", tennisModelArrayList.get(position).getImgURL());

                startActivity(context,intent,null);
            }
        });


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
    }

}
