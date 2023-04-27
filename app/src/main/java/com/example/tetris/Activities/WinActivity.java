package com.example.tetris.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tetris.Game.Game;
import com.example.tetris.R;

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
