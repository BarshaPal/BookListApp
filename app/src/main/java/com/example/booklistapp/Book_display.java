package com.example.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Book_display extends AppCompatActivity {
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView books=(ListView)findViewById(R.id.listview);
        mAdapter = new BookAdapter(this, new ArrayList<Books>());
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        books.setEmptyView(mEmptyStateTextView);
        books.setAdapter(mAdapter);
    }

}