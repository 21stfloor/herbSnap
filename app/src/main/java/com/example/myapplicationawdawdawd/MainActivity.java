package com.example.myapplicationawdawdawd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAccount = findViewById(R.id.imageAccount);
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageAccount = new Intent(MainActivity.this, Mygarden.class);
                startActivity(imageAccount);
            }
        });

        ImageView feedlogo = findViewById(R.id.feedlogo);
        feedlogo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent feedlogo = new Intent(MainActivity.this, Feed.class);
               startActivity(feedlogo);
           }
        });

        TextView feed = findViewById(R.id.feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed = new Intent(MainActivity.this, Feed.class);
                startActivity(feed);
            }
        });

        ImageView snaplogo = findViewById(R.id.snaplogo);
        snaplogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snaplogo = new Intent(MainActivity.this, Snap.class);
                startActivity(snaplogo);
            }
        });

        TextView snap = findViewById(R.id.snap);
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snap = new Intent(MainActivity.this, Snap.class);
                startActivity(snap);
            }
        });

        ImageView asklogo = findViewById(R.id.asklogo);
        asklogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asklogo = new Intent(MainActivity.this, Ask.class);
                startActivity(asklogo);
            }
        });

        TextView ask = findViewById(R.id.ask);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ask = new Intent(MainActivity.this, Ask.class);
                startActivity(ask);
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(MainActivity.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

}