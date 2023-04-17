package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void swap(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 10);
        intent.putExtra("YY", 20);
        startActivity(intent);
    }


    public void swap2(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 20);
        intent.putExtra("YY", 40);
        startActivity(intent);
    }


    public void swap3(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 40);
        intent.putExtra("YY", 80);
        startActivity(intent);
    }



}