package com.example.tetris;



import android.os.Bundle;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

/* Ce tutorial est issu d'un tutorial http://developer.android.com/training/graphics/opengl/index.html :
openGLES.zip HelloOpenGLES20
 */


public class OpenGLES30Activity extends Activity   {

    // le conteneur View pour faire du rendu OpenGL
    private MyGLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Création de View et association à Activity
           MyGLSurfaceView : classe à implémenter et en particulier la partie renderer */

        /* Pour le plein écran */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mGLView = new MyGLSurfaceView(this);
        /* Définition de View pour cette activité */

        setContentView(mGLView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("TEST", "Thread Name 1: " + Thread.currentThread().getName());
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (mGLView) {
                        mGLView.anim();
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }


}
