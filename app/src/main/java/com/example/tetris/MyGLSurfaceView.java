/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tetris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    public Grille grille;

    private float mPreviousX;


    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 3.0
        setEGLContextClientVersion(3);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MyGLRenderer();

        mRenderer.setGrille(grille);
        mRenderer.initForme();
        setRenderer(mRenderer);



        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }


    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setEGLContextClientVersion(3);

        mRenderer = new MyGLRenderer();
        mRenderer.setGrille(grille);
        mRenderer.initForme();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /**
     * Methode qui permet de faire la communication avec la preview
     */
    public String cascade() {
        return mRenderer.getPiecePrev();
    }

    public String cascadeForme(){
        return mRenderer.getTypeForme();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Les coordonnées du point touché sur l'écran
        float x = e.getX();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousX = x;
                break;
            case MotionEvent.ACTION_UP:
                mRenderer.setPosition(0.0f,-9.0f);

                if ((mPreviousX < e.getX()) && ((mPreviousX - e.getX() > 50) || (mPreviousX - e.getX() < -50)))
                {
                    grille.droit();
                    requestRender();
                    return true;
                }

                // Borne de 50 pixel pour un deplacement vers la gauche / droite
                else if ((mPreviousX > e.getX()) && ((mPreviousX - e.getX() > 50) || (mPreviousX - e.getX() < -50)))
                {
                    grille.gauche();
                    requestRender();
                    return true;
                }

                else {
                    grille.rotation();
                }
        }
        return true;
    }


    public Boolean lose() {
        return mRenderer.testLose();
    }


    public Boolean anim(){
        Boolean test = mRenderer.anima();
        requestRender();
        return test;
    }


    public void setGrille(int x , int y,String forme){
        this.grille = new Grille(y,x,0);
        mRenderer.setTypeForme(forme);
        mRenderer.setGrille(this.grille);
        mRenderer.initForme();

    }
}
