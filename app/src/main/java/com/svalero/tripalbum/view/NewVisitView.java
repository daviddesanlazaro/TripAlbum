package com.svalero.tripalbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.NewVisitPresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewVisitView extends AppCompatActivity implements NewVisitContract.View, CalendarView.OnDateChangeListener, View.OnClickListener {

    private final int SELECT_PICTURE_RESULT = 1;
    private Visit visit = new Visit (0, null, null, null, 0, null);
    private Place place;
    private final User user = new User(65, null, null, null, null, false);
    private boolean modify = false;

    private EditText etDate;
    private EditText etRating;
    private EditText etComment;
    private ImageView ivImage;
    private TextView tvInfo;
    private Calendar cal;

    private NewVisitPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);
        presenter = new NewVisitPresenter(this);

        initializeViews();

        Intent intent = getIntent();
        modify = intent.getBooleanExtra("modify", false);
        place = (Place) intent.getSerializableExtra("place");

        if (modify) { // Modificar visita
            visit = (Visit) intent.getSerializableExtra("visit");
            String text = getString(R.string.modify_visit_title, place.getName());
            displayVisitInfo(visit);
            tvInfo.setText(text);

        } else { // Insertar visita
            visit.setUser(user);
            visit.setPlace(place);
            String text = getString(R.string.add_visit_title, place.getName());
            tvInfo.setText(text);
            changeButton();
        }
    }

    @Override
    public void modifyVisit(View view) {
        String date = etDate.getText().toString();
        String ratingString = etRating.getText().toString();
        String comment = etComment.getText().toString();
        ivImage.getDrawable();

        if ((date.equals("")) || (ratingString.equals("")) || (comment.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.modify_confirm_dialog)
                    .setPositiveButton(R.string.confirm_yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setVisitData(cal.getTime(), ratingString, comment);
                                    visit.setUser(user);
                                    visit.setPlace(place);

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

    private void initializeViews() {
        etDate = findViewById(R.id.modify_visit_date);
        etDate.setOnClickListener(this);
        etRating = findViewById(R.id.modify_visit_rating);
        etComment = findViewById(R.id.modify_visit_commentary);
        ivImage = findViewById(R.id.modify_visit_image);
        tvInfo = findViewById(R.id.modify_visit_info);
        cal = Calendar.getInstance();
    }

    private void displayVisitInfo(Visit visit) {
        etDate.setText(visit.getDate());
        etRating.setText(Float.toString(visit.getRating()));
        etComment.setText(visit.getCommentary());
//        ivImage.setImageBitmap(ImageUtils.getBitmap(visit.getImage()));
    }

    private void setVisitData(Date date, String ratingString, String comment) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        float rating = Float.parseFloat(ratingString);
//        byte[] visitImage = ImageUtils.fromImageViewToByteArray(ivImage);

        visit.setUser(user);
        visit.setDate(dateString);
        visit.setRating(rating);
        visit.setCommentary(comment);
//        visit.setImage(visitImage);
    }

    private void clearFields() {
        etDate.setText("");
        etRating.setText("");
        etComment.setText("");
    }

    private void changeButton() {
        Button modifyButton = findViewById(R.id.update_button);
        modifyButton.setText(R.string.add_button);
    }

    private void showCalendar() {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.modify_visit_date)
            showCalendar();
    }
}