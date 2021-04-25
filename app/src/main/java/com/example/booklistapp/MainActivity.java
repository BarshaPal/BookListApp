package com.example.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {
    private static final int BOOK_LOADER_ID = 1;

    // Request url to send search request to google books API
    private static String google_books_request_url;

    // Adapter for the list of book
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        ListView bookListView = (ListView) findViewById(R.id.listview);
        Button searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formRequestUrl(getUserSearchText());
                loadingIndicator.setVisibility(View.VISIBLE);
                // Find a reference to the {@link ListView} in the layout


                // Create a new adapter that takes an empty list of books as input
                mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Books>());

                // Set the adapter on the {@link ListView}
                // so the list can be populated in the user interface
                bookListView.setAdapter(mAdapter);

                // Get a reference to the ConnectivityManager to check state of network connectivity

                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);

                    mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                    bookListView.setEmptyView(mEmptyStateTextView);
                        restartLoader();

            }
        });
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               TextView buybook=(TextView)findViewById(R.id.buy);
                Books book_caurrent=mAdapter.getItem(position);
                Uri bookUri = Uri.parse(book_caurrent.getBuy());

                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                try {
                    startActivity(websiteIntent);
                    Log.v("URI2", book_caurrent.getBuy());
                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "NO Book Available", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public android.content.Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BooksLoader(this, google_books_request_url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Books>> loader, List<Books> bookList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous book data
        mAdapter.clear();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            mEmptyStateTextView.setText("No Books Found!");

        } else {

            mEmptyStateTextView.setText("No Internet Connection!");

        }

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookList != null && !bookList.isEmpty()) {
            mAdapter.addAll(bookList);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Books>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
    public void restartLoader() {
        mEmptyStateTextView.setVisibility(View.GONE);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }

    /**
     * Get the text the user has entered for the search
     *
     * @return the search terms
     */
    private String getUserSearchText() {
        EditText searchText = (EditText) findViewById(R.id.search_bar);
        // read the name entered and store it in var search
        String search = searchText.getText().toString();
        return search;
    }


    private void formRequestUrl(String search){
        google_books_request_url = "https://www.googleapis.com/books/v1/volumes?q=" + search
                + "&maxResults=40";
    }

    }

