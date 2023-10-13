package com.example.myapplicationawdawdawd.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplicationawdawdawd.R;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        // Retrieve the search query from the intent
        String query = getIntent().getStringExtra("query");

        // Now, you can use the 'query' variable to perform your search operation
        // For example, you can use it to filter a list of items
       // List<Item> searchResults = performSearch(query);

        // Once you have the search results, you can display them in your UI
        // For example, you might have a RecyclerView to display the results
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
       // RecyclerView.Adapter adapter = new ItemAdapter(searchResults);
      //  recyclerView.setAdapter(adapter);

        // ... (set up RecyclerView layout manager, item decoration, etc.)
    }

    // private List<Item> performSearch(String query) {
        // Perform your search operation here
        // This could involve querying a database, filtering a list, or any other search logic
        // Return a list of items that match the search query
    }
//}