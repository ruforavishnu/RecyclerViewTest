package com.efgh.recyclerviewtest;

/**
 * Created by Vishnu on 07-Jul-16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Vishnu on 05-Jul-16.
 */



public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>
{
    private int counter = 0;
    private ArrayList<String> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView albumArtImageView;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.songtitle);
            txtFooter = (TextView) v.findViewById(R.id.albumname);
            albumArtImageView = (ImageView)v.findViewById(R.id.thumbnail);
        }
    }

    /////////////////////////////////////////////////////////////

    public void add(int position, String item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item)
    {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewAdapter(ArrayList<String> myDataset, Context myContext)
    {
        mContext = myContext;
        mDataset = myDataset;//here we have obtained the mp3FilesList -> array containing the all mp3 file paths
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Log.i("log","invoked onBindViewHolder invoked "+ ++counter+"th time");
        try
        {


            MetaData metaData = new MetaData(mDataset.get(position),mContext);
            Log.i("log", "metaData:" + metaData);
            Bitmap albumArt = metaData.getAlbumArtBitmap();
            if(albumArt == null)
            {
                Log.i("log", "albumart is null, setting default albumart:" + metaData);
                holder.albumArtImageView.setImageResource(R.drawable.cover);
            }
            else
            {
                Log.i("log", "albumart exists, setting associated albumart:" + metaData);
                holder.albumArtImageView.setImageBitmap(metaData.getAlbumArtBitmap());
            }


            holder.txtHeader.setText( metaData.getSongTitle());
            holder.txtFooter.setText(metaData.getAlbumName());
            holder.txtHeader.setSelected(true);
            MyTag pathTag = new MyTag(mDataset.get(position));
            Log.i("logtest", "mDataset.get(position):" + mDataset.get(position));
            holder.txtHeader.setTag(pathTag);




            /*for(String val : mDataset)
            {
                Log.i("Log", "val:"+val+", at position "+position);
            }*/

            /*String val = mDataset.get(position);

            Log.i("Log", "val:" + val);

            MediaMetadataRetriever metaRetreiver = new MediaMetadataRetriever();
            metaRetreiver.setDataSource(val);
            byte[] art = metaRetreiver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            //    holder.albumArtImageView.setImageBitmap(songImage);


            holder.txtFooter.setText("Album: " +mDataset.get(position) );
            holder.txtHeader.setText("Title:"+mDataset.get(position));
            holder.txtHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // remove(name);
                }
            });*/


        }
        catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
        }


        String footerStr = "";
        if(mDataset.get(position) == null)
        {
            footerStr="Not found";
        }
        else
        {
            footerStr = mDataset.get(position);
        }





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
