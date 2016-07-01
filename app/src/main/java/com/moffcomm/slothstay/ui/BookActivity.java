package com.moffcomm.slothstay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Book;


public class BookActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        book = (Book) (getIntent().getParcelableExtra("book"));
        setupContent();
    }

    private void setupContent() {
        ((TextView) findViewById(R.id.hotelNameTextView)).setText(book.getHotel().getName());
        ((TextView) findViewById(R.id.address1TextView)).setText(book.getHotel().getAddress1());
        ((TextView) findViewById(R.id.address2TextView)).setText(book.getHotel().getAddress2());
        ((TextView) findViewById(R.id.roomNameTextView)).setText(book.getRoom().getName());
        ((TextView) findViewById(R.id.priceTextView)).setText(book.getRoom().getPrice());
        Glide.with(this).load(book.getHotel().getPictures().get(0).getImageUrl()).
                into((ImageView) findViewById(R.id.imageView));
    }

    public void onCheckClick(View view) {
        Intent intent = new Intent(this, CheckActivity.class);
        startActivity(intent);
    }
}
