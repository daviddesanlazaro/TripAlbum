package com.svalero.tripalbum.view;

import static com.svalero.tripalbum.api.Constants.Action.PUT;

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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.api.Constants.Action;
import com.svalero.tripalbum.contract.NewVisitContract;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.NewVisitPresenter;
import com.svalero.tripalbum.util.ImageUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewVisitView extends AppCompatActivity implements NewVisitContract.View, CalendarView.OnDateChangeListener, View.OnClickListener {

    private final int SELECT_PICTURE_RESULT = 1;
    private Visit visit = new Visit (null, null, null, null, 0, null);
    private Place place;
    private final User user = new User("624c4ba4e6a95b2e80b77bed", null, null, null, null);
    private Action action;

    private EditText etDate;
    private RatingBar ratingBar;
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
        place = (Place) intent.getSerializableExtra("place");
        action = Action.valueOf(getIntent().getStringExtra("ACTION"));

        if (action == PUT) { // Modificar
            visit = (Visit) getIntent().getSerializableExtra("visit");
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
        String comment = etComment.getText().toString();
        ivImage.getDrawable();

        if ((date.equals("")) || (comment.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.modify_confirm_dialog)
                    .setPositiveButton(R.string.confirm_yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setVisitData(cal.getTime(), comment);
                                    visit.setUser(user);
                                    visit.setPlace(place);

                                    presenter.addVisit(visit, action);
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
        ratingBar = findViewById(R.id.ratingBar);
        etComment = findViewById(R.id.modify_visit_commentary);
        ivImage = findViewById(R.id.modify_visit_image);
        tvInfo = findViewById(R.id.modify_visit_info);
        cal = Calendar.getInstance();
    }

    private void displayVisitInfo(Visit visit) {
        etDate.setText(visit.getDate());
        ratingBar.setRating(visit.getRating());
        etComment.setText(visit.getCommentary());
//        if (visit.getImage() != null) {
//            byte[] image = visit.getImage().getBytes(StandardCharsets.UTF_8);
//            ivImage.setImageBitmap(ImageUtils.getBitmap(image));
//        }
    }

    private void setVisitData(Date date, String comment) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        float rating = ratingBar.getRating();
//        byte[] visitImage = ImageUtils.fromImageViewToByteArray(ivImage);
//        String image = new String(visitImage, StandardCharsets.UTF_8);

        visit.setUser(user);
        visit.setDate(dateString);
        visit.setRating(rating);
        visit.setCommentary(comment);
//        visit.setImage(image);
    }

    private void clearFields() {
        etDate.setText("");
        ratingBar.setRating(0);
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