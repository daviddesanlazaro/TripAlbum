package com.svalero.tripalbum;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.util.ImageUtils;

import java.util.ArrayList;

public class VisitAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Visit> listaVisits;
    private LayoutInflater inflater;

    public VisitAdapter(Activity context, ArrayList<Visit> listaVisits) {
        this.context = context;
        this.listaVisits = listaVisits;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView foto;
        TextView rating;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.visit, null);

            holder = new ViewHolder();
            holder.foto = (ImageView) convertView.findViewById(R.id.visit_image);
            holder.rating = (TextView) convertView.findViewById(R.id.visit_rating);
            holder.date = (TextView) convertView.findViewById(R.id.visit_date);

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
        holder.foto.setImageBitmap(ImageUtils.getBitmap(visit.getImage()));
        holder.rating.setText(R.string.rating + ": " + visit.getRating());
        holder.date.setText(visit.getDate().toString());

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

}