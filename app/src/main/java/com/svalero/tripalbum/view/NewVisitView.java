package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.NewVisitPresenter;
import com.svalero.tripalbum.util.ImageUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NewVisitView extends AppCompatActivity implements NewVisitContract.View, CalendarView.OnDateChangeListener {

    private final int SELECT_PICTURE_RESULT = 1;
    private Visit visit = new Visit (0, 0, null, 0, null);
    private boolean modify = false;

    EditText etRating;
    EditText etComment;
    ImageView ivImage;
    TextView tvInfo;
    CalendarView calendar;
    Calendar cal;

    private NewVisitPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);
        presenter = new NewVisitPresenter(this);

        initializeViews();

        Intent intent = getIntent();
        modify = intent.getBooleanExtra("modify", false);
        String placeName = intent.getStringExtra("placeName");

        if (modify) { // Modificar visita
            visit = (Visit)intent.getSerializableExtra("Visit");
            String text = getString(R.string.modify_visit_title, placeName);
            displayVisitInfo(visit);
            tvInfo.setText(text);

        } else { // Insertar visita
            visit.setPlaceId(intent.getIntExtra("placeId", 0));
            String text = getString(R.string.add_visit_title, placeName);
            tvInfo.setText(text);
            changeButton();
        }

        calendar = (CalendarView) findViewById(R.id.calendarView);
        cal = new Calendar() {

            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                TimeZone tz = TimeZone.getTimeZone("UTC");
                cal = Calendar.getInstance(tz);
                cal.set(year, month, dayOfMonth);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(cal.getTime());
                Toast.makeText(getApplicationContext(), dateString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void modifyVisit(View view) {
        String ratingString = etRating.getText().toString();
        String comment = etComment.getText().toString();
        ivImage.getDrawable();

//        if ((dateString.equals("")) || (ratingString.equals("")) || (comment.equals(""))) {
        if ((ratingString.equals("")) || (comment.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.modify_confirm_dialog)
                    .setPositiveButton(R.string.confirm_yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setVisitData(cal.getTime(), ratingString, comment);
                                    presenter.addVisit(visit, modify);
                                    clearFields();
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

    public void deleteVisit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm_dialog)
                .setPositiveButton(R.string.confirm_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteVisit(visit);
                            }})
                .setNegativeButton(R.string.confirm_no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }

    private void initializeViews() {
        etRating = findViewById(R.id.modify_visit_rating);
        etComment = findViewById(R.id.modify_visit_comment);
        ivImage = findViewById(R.id.modify_visit_image);
        tvInfo = findViewById(R.id.modify_visit_info);
    }

    private void displayVisitInfo(Visit visit) {
        etRating.setText(Float.toString(visit.getRating()));
        etComment.setText(visit.getCommentary());
//        ivImage.setImageBitmap(ImageUtils.getBitmap(visit.getImage()));
    }

    private void setVisitData(Date date, String ratingString, String comment) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        float rating = Float.parseFloat(ratingString);
        byte[] visitImage = ImageUtils.fromImageViewToByteArray(ivImage);

        visit.setUserId(65);
        visit.setPlaceId(visit.getPlaceId());
        visit.setDate(dateString);
        visit.setRating(rating);
        visit.setCommentary(comment);
//        visit.setImage(visitImage);
    }

    private void clearFields() {
        etRating.setText("");
        etComment.setText("");
    }

    private void changeButton() {
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setVisibility(View.GONE);
        Button modifyButton = findViewById(R.id.update_button);
        modifyButton.setText(R.string.add_button);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_visit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        clearFields();
        ivImage.setImageResource(android.R.drawable.ic_menu_add);
        return true;
    }

    public void selectPicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == SELECT_PICTURE_RESULT) && (resultCode == RESULT_OK)
                && (data != null)) {
            Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit()
                    .into((ImageView) findViewById(R.id.modify_visit_image));
        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
    }
}