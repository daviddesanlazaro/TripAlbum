package com.svalero.tripalbum.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.tripalbum.contract.ModifyVisitContract;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Visit;

public class ModifyVisitModel implements ModifyVisitContract.Model {

    @Override
    public void addVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().insert(visit);
    }

    @Override
    public void modifyVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().update(visit);
    }

    @Override
    public void deleteVisit(Context context, Visit visit) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "visits").allowMainThreadQueries().build();
        db.visitDao().delete(visit);
    }
}
