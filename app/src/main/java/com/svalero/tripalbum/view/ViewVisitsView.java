package com.svalero.tripalbum.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.ViewVisitsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ViewVisitsView extends AppCompatActivity implements ViewVisitsContract.View, AdapterView.OnItemClickListener {

    private ViewVisitsPresenter presenter;

    public ArrayList<Visit> visitsList;
    private VisitAdapter visitsAdapter;
    private long userId;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);
        presenter = new ViewVisitsPresenter(this);

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 65);
        place = (Place) intent.getSerializableExtra("place");

        initializeVisitsList();

        FloatingActionButton newVisit = findViewById(R.id.contextual_add_visit);
        if (userId != 65)   // Solución hasta hacer control de sesión
            newVisit.setVisibility(View.GONE);
    }

    private void initializeVisitsList() {
        visitsList = new ArrayList<>();
        visitsAdapter = new VisitAdapter(this, visitsList, this, userId);
        ListView lvVisits = findViewById(R.id.visit_list_main);
        lvVisits.setAdapter(visitsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadVisits(userId, place.getId());
    }


    @Override
    public void listVisits(List<Visit> visits) {
        visitsList.clear();
        visitsList.addAll(visits);
        visitsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.visit_list_main) {
            Visit visit = visitsList.get(position);
            presenter.openNewVisit(place, visit, true);
        }
    }

    public void openNewVisit(View view) {
        presenter.openNewVisit(place, null, false);
    }

    public void openModifyVisit(Visit visit) {
        presenter.openNewVisit(place, visit, true);
    }

    public void deleteVisit(long visitId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_visit_dialog)
                .setPositiveButton(R.string.confirm_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteVisit(visitId);
                                presenter.loadVisits(userId, place.getId());
                            }})
                .setNegativeButton(R.string.confirm_no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }
}
