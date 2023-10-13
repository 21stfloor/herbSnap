package com.example.myapplicationawdawdawd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class Ask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        ImageView imageAccount = findViewById(R.id.imageAccount);
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageAccount = new Intent(Ask.this, Mygarden.class);
                startActivity(imageAccount);
            }
        });

        ImageView feedlogo=findViewById(R.id.feedlogo);
        feedlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedlogo= new Intent(Ask.this, Feed.class);
                startActivity(feedlogo);
            }
        });

        TextView feed=findViewById(R.id.feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed= new Intent(Ask.this, Feed.class);
                startActivity(feed);
            }
        });

        ImageView snaplogo=findViewById(R.id.snaplogo);
        snaplogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snaplogo= new Intent(Ask.this, Snap.class);
                startActivity(snaplogo);
            }
        });

        TextView snap=findViewById(R.id.snap);
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snap= new Intent(Ask.this, Snap.class);
                startActivity(snap);
            }
        });
    }
}
