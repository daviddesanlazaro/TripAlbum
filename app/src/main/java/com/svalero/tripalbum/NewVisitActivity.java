package com.svalero.tripalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.tripalbum.database.AppDatabase;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.util.ImageUtils;

import java.time.LocalDate;

public class NewVisitActivity extends AppCompatActivity {

    private int SELECT_PICTURE_RESULT = 1;
    private Visit visit = new Visit(0, null, 0, null, 0, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_visit);

        Intent intent = getIntent();
        visit.setPlaceId(intent.getIntExtra("placeId", 0));
        String placeName = intent.getStringExtra("placeName");

        TextView tvInfo = findViewById(R.id.new_visit_info);
        tvInfo.setText("Registra tu visita a " + placeName);
    }

    public void addVisit(View view) {
        EditText etDate = findViewById(R.id.visit_date);
        EditText etRating = findViewById(R.id.visit_rating);
        EditText etComment = findViewById(R.id.visit_comment);
        ImageView ivImage = findViewById(R.id.visit_image);

        String dateString = etDate.getText().toString();
        String ratingString = etRating.getText().toString();
        String comment = etComment.getText().toString();

        if ((dateString.equals("")) || (ratingString.equals("")) || (comment.equals(""))) {
            Toast.makeText(this, getString(R.string.add_missing_data), Toast.LENGTH_SHORT).show();
        } else {
            LocalDate date = LocalDate.parse(dateString);
            float rating = Float.parseFloat(ratingString);
            byte[] visitImage = ImageUtils.fromImageViewToByteArray(ivImage);

            visit.setDate(date);
            visit.setRating(rating);
            visit.setCommentary(comment);
            visit.setImage(visitImage);

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "visits").allowMainThreadQueries().build();
            db.visitDao().insert(visit);

            Toast.makeText(this, getString(R.string.visit_added), Toast.LENGTH_SHORT).show();
            etDate.setText("");
            etRating.setText("");
            etComment.setText("");
        }
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
                    .into((ImageView) findViewById(R.id.visit_image));

        }
    }
}