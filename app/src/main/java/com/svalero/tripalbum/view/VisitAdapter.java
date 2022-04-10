package com.svalero.tripalbum.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.util.ImageUtils;

import java.util.ArrayList;

public class VisitAdapter extends BaseAdapter {

    private final ViewVisitsView view;
    private final Context context;
    private final ArrayList<Visit> listaVisits;
    private final LayoutInflater inflater;
    private String ratingText;
    private Button modifyButton;
    private Button deleteButton;
    private final String userId;
    private boolean detailView, modify;

    public VisitAdapter(Activity context, ArrayList<Visit> listaVisits, ViewVisitsView view, String userId, boolean detailView, boolean modify) {
        this.context = context;
        this.listaVisits = listaVisits;
        inflater = LayoutInflater.from(context);
        this.view = view;
        this.userId = userId;
        this.detailView = detailView;
        this.modify = modify;
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
//
//        byte[] bytes = visit.getImage().getBytes(StandardCharsets.UTF_8);
//        holder.foto.setImageBitmap(ImageUtils.getBitmap(bytes));
        holder.commentary.setText(visit.getCommentary());
        if (detailView) {
            ratingToString(visit.getRating());
            holder.rating.setText(ratingText);
            holder.date.setText(visit.getDate());
        }

        initializeButtons(convertView);

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

    private View.OnClickListener modifyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent().getParent().getParent().getParent();
            final int position = listView.getPositionForView(parentRow);
            modifyButton.setTag(position);
            Visit visit = listaVisits.get(position);
            view.openModifyVisit(visit);
        }
    };

    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent().getParent().getParent().getParent();
            final int position = listView.getPositionForView(parentRow);
            deleteButton.setTag(position);
            Visit visit = listaVisits.get(position);
            view.deleteVisit(visit.getId());
        }
    };

    private void initializeButtons(View convertView) {
        modifyButton = (Button) convertView.findViewById(R.id.visit_modify);
        deleteButton = (Button) convertView.findViewById(R.id.visit_delete);
        modifyButton.setOnClickListener(modifyClickListener);
        deleteButton.setOnClickListener(deleteClickListener);

        if (userId.equals("624c4ba4e6a95b2e80b77bed") && modify) { // Solución hasta hacer control de sesión
            modifyButton.setVisibility(VISIBLE);
            deleteButton.setVisibility(VISIBLE);
        } else {
            modifyButton.setVisibility(GONE);
            deleteButton.setVisibility(GONE);
        }
    }
}
