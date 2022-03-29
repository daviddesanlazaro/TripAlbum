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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.tripalbum.R;
import com.svalero.tripalbum.contract.ModifyVisitContract;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.presenter.ModifyVisitPresenter;
import com.svalero.tripalbum.util.ImageUtils;

import java.time.LocalDate;

public class ModifyVisitActivityView extends AppCompatActivity implements ModifyVisitContract.View {

    private final int SELECT_PICTURE_RESULT = 1;
    private Visit visit = new Visit (0, null, 0, null, 0, null);
    private boolean modify;

    EditText etDate;
    EditText etRating;
    EditText etComment;
    ImageView ivImage;
    TextView tvInfo;

    private ModifyVisitPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_visit);
        presenter = new ModifyVisitPresenter(this);

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
    }

    public void modifyVisit(View view) {
        String dateString = etDate.getText().toString();
        String ratingString = etRating.getText().toString();
        String comment = etComment.getText().toString();
        ivImage.getDrawable();

        if ((dateString.equals("")) || (ratingString.equals("")) || (comment.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.modify_confirm_dialog)
                    .setPositiveButton(R.string.confirm_yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setVisitData(dateString, ratingString, comment);
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
        etDate = findViewById(R.id.modify_visit_date);
        etRating = findViewById(R.id.modify_visit_rating);
        etComment = findViewById(R.id.modify_visit_comment);
        ivImage = findViewById(R.id.modify_visit_image);
        tvInfo = findViewById(R.id.modify_visit_info);
    }

    private void displayVisitInfo(Visit visit) {
        etDate.setText(visit.getDate().toString());
        etRating.setText(Float.toString(visit.getRating()));
        etComment.setText(visit.getCommentary());
        ivImage.setImageBitmap(ImageUtils.getBitmap(visit.getImage()));
    }

    private void setVisitData(String dateString, String ratingString, String comment) {
        LocalDate date = LocalDate.parse(dateString);
        float rating = Float.parseFloat(ratingString);
        byte[] visitImage = ImageUtils.fromImageViewToByteArray(ivImage);

        visit.setDate(date);
        visit.setRating(rating);
        visit.setCommentary(comment);
        visit.setImage(visitImage);
    }

    private void clearFields() {
        etDate.setText("");
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
}