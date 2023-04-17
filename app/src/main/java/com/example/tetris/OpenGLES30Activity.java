package com.example.tetris;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class OpenGLES30Activity extends Activity   {

    // le conteneur View pour faire du rendu OpenGL
    private MyGLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Pour le plein écran */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mGLView = new MyGLSurfaceView(this);

        setContentView(mGLView);

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

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
