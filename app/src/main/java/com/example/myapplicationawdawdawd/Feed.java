package com.example.myapplicationawdawdawd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class Feed extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // Intent searchIntent = new Intent(Feed.this, SearchResultActivity.class);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                return false;
            }
        });

        ImageView imageAccount = findViewById(R.id.imageAccount);
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageAccount = new Intent(Feed.this, Mygarden.class);
                startActivity(imageAccount);
            }
        });

        ImageView snaplogo=findViewById(R.id.snaplogo);
        snaplogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snaplogo= new Intent(Feed.this, Snap.class);
                startActivity(snaplogo);
            }
        });

        TextView snap=findViewById(R.id.snap);
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snap= new Intent(Feed.this, Snap.class);
                startActivity(snap);
            }
        });

        ImageView asklogo=findViewById(R.id.asklogo);
        asklogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asklogo= new Intent(Feed.this, Ask.class);
                startActivity(asklogo);
            }
        });

        TextView ask=findViewById(R.id.ask);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ask= new Intent(Feed.this, Ask.class);
                startActivity(ask);
            }
        });
    }
}