package com.svalero.tripalbum.view;

import static com.svalero.tripalbum.api.Constants.Action.FRIEND;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.api.Constants.Action;
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
    private String userId;
    private Place place;
    private SharedPreferences preferences;
    private Action action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);
        presenter = new ViewVisitsPresenter(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        place = (Place) intent.getSerializableExtra("place");
        action = Action.valueOf(getIntent().getStringExtra("ACTION"));

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        FloatingActionButton newVisit = findViewById(R.id.contextual_add_visit);
        if (action == FRIEND)
            newVisit.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeVisitsList();
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        presenter.loadVisits(userId, place.getId());
    }

    private void initializeVisitsList() {
        boolean detailView = preferences.getBoolean("preferences_detail", false);
        boolean modify = preferences.getBoolean("preferences_modify", false);
        visitsList = new ArrayList<>();
        visitsAdapter = new VisitAdapter(this, visitsList, this, detailView, modify, action);
        ListView lvVisits = findViewById(R.id.visit_list_main);
        lvVisits.setAdapter(visitsAdapter);
    }

    @Override
    public void listVisits(List<Visit> visits) {
        visitsList.clear();
        visitsList.addAll(visits);
        visitsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "" + visitsList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.visit_list_main) {
            Visit visit = visitsList.get(position);
            presenter.openNewVisit(place, visit, "PUT");
        }
    }

    public void openNewVisit(View view) {
        presenter.openNewVisit(place, null, "POST");
    }

    public void openModifyVisit(Visit visit) {
        presenter.openNewVisit(place, visit, "PUT");
    }

    public void deleteVisit(String visitId) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_view_visits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, PreferencesView.class);
        startActivity(intent);
        return true;
    }
}
