package com.franmontiel.networkcalls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.franmontiel.commons.adapters.BaseAdapter;
import com.franmontiel.networkcalls.entities.Photo;

/**
 * Created by Francisco J. Montiel on 1/12/16.
 */

public class PhotoAdapter extends BaseAdapter<Photo> {

    Context context;

    public PhotoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo, parent, false);

            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photo photo = getItem(position);

//        Picasso.with(parent.getContext()).load(photo.getThumbnailUrl()).noFade().into(holder.thumbnail);
        Glide.with(parent.getContext()).load(photo.getThumbnailUrl()).into(holder.thumbnail);

        holder.title.setText(photo.getTitle());

        return convertView;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView title;
    }
}
