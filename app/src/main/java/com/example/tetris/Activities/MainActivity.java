package com.example.tetris.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tetris.Game.Game;
import com.example.tetris.R;

public class MainActivity extends AppCompatActivity {
    private String selectedItem = "Square";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Spinner spinner1 = findViewById(R.id.spinner1);
        String[] items = new String[]{"Cercle", "Losange", "Square","Pentagone","Star","Star7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedItem = "Square";
            }
        });


    }


    public void swap(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 10);
        intent.putExtra("YY", 20);
        intent.putExtra("FF",selectedItem);
        startActivity(intent);
    }


    public void swap2(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 20);
        intent.putExtra("YY", 40);
        intent.putExtra("FF",selectedItem);
        startActivity(intent);
    }


    public void swap3(View v){
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("XX", 40);
        intent.putExtra("YY", 80);
        intent.putExtra("FF",selectedItem);
        startActivity(intent);
    }





}