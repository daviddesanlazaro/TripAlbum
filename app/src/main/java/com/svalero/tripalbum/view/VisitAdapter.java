package com.svalero.tripalbum.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.svalero.tripalbum.api.Constants.Action.USER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.api.Constants.Action;
import com.svalero.tripalbum.domain.Visit;

import java.util.ArrayList;

public class VisitAdapter extends BaseAdapter {

    private final ViewVisitsView view;
    private final Context context;
    private final ArrayList<Visit> listaVisits;
    private final LayoutInflater inflater;
    private String ratingText;
    private Button modifyButton;
    private Button deleteButton;
    private boolean detailView, modify;
    private Action action;

    public VisitAdapter(Activity context, ArrayList<Visit> listaVisits, ViewVisitsView view, boolean detailView, boolean modify, Action action) {
        this.context = context;
        this.listaVisits = listaVisits;
        inflater = LayoutInflater.from(context);
        this.view = view;
        this.detailView = detailView;
        this.modify = modify;
        this.action = action;
    }

    static class ViewHolder {
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
            holder.rating = convertView.findViewById(R.id.visit_rating);
            holder.date = convertView.findViewById(R.id.visit_date);
            holder.commentary = convertView.findViewById(R.id.visit_commentary);

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
        modifyButton = convertView.findViewById(R.id.visit_modify);
        deleteButton = convertView.findViewById(R.id.visit_delete);
        modifyButton.setOnClickListener(modifyClickListener);
        deleteButton.setOnClickListener(deleteClickListener);

        if ((action == USER) && modify) {
            modifyButton.setVisibility(VISIBLE);
            deleteButton.setVisibility(VISIBLE);
        } else {
            modifyButton.setVisibility(GONE);
            deleteButton.setVisibility(GONE);
        }
    }
}
