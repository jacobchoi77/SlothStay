package com.moffcomm.slothstay.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.moffcomm.slothstay.R;
import com.moffcomm.slothstay.model.Book;


public class BookActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        book = (Book) (getIntent().getParcelableExtra("book"));
    }
}
