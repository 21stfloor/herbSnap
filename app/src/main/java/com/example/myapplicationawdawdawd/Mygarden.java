package com.example.myapplicationawdawdawd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Mygarden extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygarden);

        ImageView backArrow = findViewById(R.id.logout);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(Mygarden.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView feedlogo = findViewById(R.id.feedlogo);
        feedlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedlogo = new Intent(Mygarden.this, Feed.class);
                startActivity(feedlogo);
            }
        });

        TextView feed = findViewById(R.id.feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed = new Intent(Mygarden.this, Feed.class);
                startActivity(feed);
            }
        });

        ImageView snaplogo = findViewById(R.id.snaplogo);
        snaplogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snaplogo = new Intent(Mygarden.this, Snap.class);
                startActivity(snaplogo);
            }
        });

        TextView snap = findViewById(R.id.snap);
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snap = new Intent(Mygarden.this, Snap.class);
                startActivity(snap);
            }
        });

        ImageView asklogo = findViewById(R.id.asklogo);
        asklogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asklogo = new Intent(Mygarden.this, Ask.class);
                startActivity(asklogo);
            }
        });

        TextView ask = findViewById(R.id.ask);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ask = new Intent(Mygarden.this, Ask.class);
                startActivity(ask);
            }
        });

    }
}