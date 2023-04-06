package com.example.tetris;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    MyGLSurfaceView mGLView;
    PreviewView GLPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGLView = findViewById(R.id.myGLSurfaceView4);
        GLPrev = findViewById(R.id.previewView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                        mGLView.anim();

                }

            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                while (true) {

                        Tetromino tetromino = new Tetromino(mGLView.cascade());
                        GLPrev.anim(tetromino);
                }

            }
        };

        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);

        thread.start();
        thread2.start();
    }

}