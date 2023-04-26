package com.example.tetris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tetris.Activities.LoseActivity;
import com.example.tetris.Activities.WinActivity;

public class Game extends AppCompatActivity {

    MyGLSurfaceView mGLView;
    PreviewView GLPrev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGLView = findViewById(R.id.myGLSurfaceView4);
        GLPrev = findViewById(R.id.previewView);
        Intent intent = getIntent();
        int x = intent.getIntExtra("XX",0);
        int y = intent.getIntExtra("YY",0);
        String forme = intent.getStringExtra("FF");
        mGLView.setGrille(x,y,forme);

        TextView score = findViewById(R.id.score);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        if (mGLView.anim())
                        {
                            startActivity(new Intent(Game.this, WinActivity.class));
                        }

                        if (mGLView.lose()){
                            startActivity(new Intent(Game.this, LoseActivity.class));
                            break;
                        }
                }

            }
        };
        Runnable runnable2 = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        Tetromino tetromino = new Tetromino(mGLView.cascade());
                        String typeForme = mGLView.cascadeForme();
                        GLPrev.anim(tetromino,typeForme);

                        score.setText("SCORE\n" +
                                "" + mGLView.grille.getScore());
                }

            }
        };

        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);

        thread.start();
        thread2.start();
    }

}