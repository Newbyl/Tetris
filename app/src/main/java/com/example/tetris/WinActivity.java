package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
    }

    public void swap(View v){
        startActivity(new Intent(WinActivity.this, Game.class));
    }

    public void swapMenu(View v){
        startActivity(new Intent(WinActivity.this, MainActivity.class));
    }
}