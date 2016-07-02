package com.example.alberto.proyectofinal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alberto on 6/24/2016.
 */
public class customAdapter extends BaseAdapter {
    String[] result;
    Context context;
    Bitmap[] imageId;
    needs[] needs;
    private static LayoutInflater inflater = null;

    public customAdapter(Activity mainActivity, needs[] needs) {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        this.needs = needs;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return needs.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        TextView quant;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.need_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.needText);
        holder.quant = (TextView) rowView.findViewById(R.id.needTextQuant);
        holder.img = (ImageView) rowView.findViewById(R.id.needImage);

        holder.tv.setText(needs[position].name);

        Log.d("cantidad",Integer.toString(needs[position].quantity));
        holder.quant.setText(Integer.toString(needs[position].quantity));

        byte[] decodedBytes = Base64.decode(needs[position].photo, 0);
        Bitmap imageG = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        holder.img.setImageBitmap(imageG);

        return rowView;
    }
}