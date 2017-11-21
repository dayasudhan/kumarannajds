package com.kuruvatech.kumarannajds.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by gagan on 10/27/2017.
 */
public class Adapter extends RecyclerView.Adapter {
    // I assume that you will pass images as list of resources, but this can be easily switched to a list of URLS

    public ArrayList<String> urls = new ArrayList<String>();
    public ImageLoader imageLoader;
    Activity con;
    public Adapter(Activity context, ArrayList<String> aurls) {

        this.urls = aurls;
        con = context;
        imageLoader = new ImageLoader(con.getApplicationContext(),500,500);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        imageLoader.DisplayImage(urls.get(i),  itemViewHolder.item);
       // itemViewHolder.item.setImageResource(imageResList.get(i));

    }
    public ArrayList<String> getFilePaths()
    {
        ArrayList<String> paths = new ArrayList<String>();
        for(int i = 0 ; i< urls.size();i++)
        {
            paths.add(imageLoader.getFilePath(urls.get(i)));
        }
        return paths;
    }

    public ArrayList<String> getImages()
    {
        return urls;
    }
    @Override
    public int getItemCount() {
        return urls.size();
    }


    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView item;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.item = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}