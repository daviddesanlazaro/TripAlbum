package com.svalero.tripalbum.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.util.ImageUtils;

import java.util.ArrayList;

public class VisitAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Visit> listaVisits;
    private LayoutInflater inflater;
    private String ratingText;

    public VisitAdapter(Activity context, ArrayList<Visit> listaVisits) {
        this.context = context;
        this.listaVisits = listaVisits;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
//        ImageView foto;
        TextView rating;
        TextView date;
        TextView commentary;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.visit, parent, false);

            holder = new ViewHolder();
//            holder.foto = (ImageView) convertView.findViewById(R.id.visit_image);
            holder.rating = (TextView) convertView.findViewById(R.id.visit_rating);
            holder.date = (TextView) convertView.findViewById(R.id.visit_date);
            holder.commentary = (TextView) convertView.findViewById(R.id.visit_commentary);

            convertView.setTag(holder);
        }
        /*
         * En caso de que la View no sea null se reutilizará con los
         * nuevos valores
         */
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Visit visit = listaVisits.get(position);
//        holder.foto.setImageBitmap(null);
        ratingToString(visit.getRating());
        holder.commentary.setText(visit.getCommentary());
        holder.rating.setText(ratingText);
        holder.date.setText(visit.getDate());

        return convertView;
    }

    @Override
    public int getCount() {
        return listaVisits.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listaVisits.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @SuppressLint("DefaultLocale")
    public void ratingToString(double d) {
        if(d == (long) d)
             ratingText = String.format("%d",(long) d);
        else
            ratingText = String.format("%s",d);
    }

}