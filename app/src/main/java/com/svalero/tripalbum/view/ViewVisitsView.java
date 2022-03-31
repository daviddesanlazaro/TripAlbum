package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ViewVisitsContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.ViewVisitsPresenter;

import java.util.ArrayList;
import java.util.List;

public class ViewVisitsView extends AppCompatActivity implements ViewVisitsContract.View, AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {

    private ViewVisitsPresenter presenter;

    public ArrayList<Visit> visitsList;
    private VisitAdapter visitsAdapter;
    private int userId;
    private Place place = new Place (0, null, null, 0, 0, 0);

    private ListView lvVisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);
        presenter = new ViewVisitsPresenter(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 65);
        place.setId(intent.getIntExtra("placeId", 0));

        initializeVisitsList();
    }

    private void initializeVisitsList() {
        visitsList = new ArrayList<>();
        visitsAdapter = new VisitAdapter(this, visitsList);
        lvVisits = (ListView) findViewById(R.id.visit_list_main);
        lvVisits.setAdapter(visitsAdapter);
        lvVisits.setOnItemClickListener(this);
        registerForContextMenu(lvVisits);
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
            presenter.openModifyVisit(visit, place);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, NewPlaceActivityView.class);
        startActivity(intent);
        return true;
    }

    public void openNewVisit(View view) {
        presenter.openNewVisit(place);
    }

    public void openViewPlace(View view) {
        if (place.getId() == 0) {
            Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewPlaceActivityView.class);
            intent.putExtra("place", place);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int itemSeleccionado = info.position;

        if (item.getItemId() == R.id.modify_context) {
            Visit visit = visitsList.get(itemSeleccionado);
            presenter.openModifyVisit(visit, place);
        }
        if (item.getItemId() == R.id.delete_context) {
            deleteVisit(info);
        }
        return true;
    }

    private void deleteVisit(AdapterView.AdapterContextMenuInfo info) {
        Visit visit = visitsList.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm_dialog)
                .setPositiveButton(R.string.confirm_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteVisit(visit);
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
