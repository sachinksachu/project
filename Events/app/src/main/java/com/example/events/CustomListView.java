package com.example.events;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.InputStream;

public class CustomListView extends ArrayAdapter<String> {

    Bitmap bitmap;
    private final Activity context;
    private String[] eventname,imagepath;
    private String[] photo;


    public CustomListView(Activity context, String[] strings, String[] eventname, String[] photo) {
        super(context, R.layout.cardview_inflator);

        this.context = context;
        this.eventname = eventname;
        this.photo = photo;
    }

    //private String[] eventname;
    //private String[] eventname;
    public View getView(int position, View converView, ViewGroup parent) {
        View r = converView;
        ViewHolder viewHolder =null;
        if(r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.cardview_inflator,null,true);
            viewHolder= new ViewHolder(r);
            r.setTag(viewHolder);


        }
        else
        {
            viewHolder =(ViewHolder)r.getTag();

        }

        viewHolder.tv.setText(eventname[position]);
        viewHolder.tx.setText(photo[position]);
        new GetUrl(viewHolder.ivw).execute(imagepath[position]);
        return r;

    }

    class ViewHolder{

        TextView tv,tx,tz;
        ImageView ivw;

        ViewHolder(View view)
        {
            tv =(TextView) view.findViewById(R.id.info_text);
            ivw =(ImageView) view.findViewById(R.id.imageView);

        }

    }
    public class GetUrl extends AsyncTask<String,Void, Bitmap>
    {

        ImageView imgvw;
        public GetUrl(ImageView imgv)
        {
            this.imgvw=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try{
                InputStream ist = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(ist);


            }catch (Exception ex)
            {

            }
            return bitmap;
        }
        protected void onPosExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            imgvw.setImageBitmap(bitmap);


        }

    }


}

