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

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


/* La classe MyGLSurfaceView avec en particulier la gestion des événements
  et la création de l'objet renderer

*/


/* On va dessiner un carré qui peut se déplacer grâce à une translation via l'écran tactile */

public class MyGLSurfaceView extends GLSurfaceView {

    /* Un attribut : le renderer (GLSurfaceView.Renderer est une interface générique disponible) */
    /* MyGLRenderer va implémenter les méthodes de cette interface */

    private final MyGLRenderer mRenderer;
    public Grille grille = new Grille(20,10);

    private Boolean mutex = true;
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 3.0
        setEGLContextClientVersion(3);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MyGLRenderer();

        Tetromino tetromino = new Tetromino("O");

        grille.addForme(tetromino);


        mRenderer.setGrille(grille);
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 3.0
        setEGLContextClientVersion(3);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MyGLRenderer();

        Tetromino tetromino = new Tetromino("O");

        grille.addForme(tetromino);


        mRenderer.setGrille(grille);
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    /* pour gérer la translation */
    private float mPreviousX;
    private float mPreviousY;
    private boolean condition = false;






    /* Comment interpréter les événements sur l'écran tactile */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Les coordonnées du point touché sur l'écran
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreviousX = x;
                mPreviousY = y;
                break;
            case MotionEvent.ACTION_UP:
                mRenderer.setPosition(0.0f,-9.0f);
                if ((mPreviousX < e.getX()) && ((mPreviousX - e.getX() > 50) || (mPreviousX - e.getX() < -50)))
                {
                    grille.droit();

                    if (mutex) {
                        mutex = false;
                        requestRender();
                        mutex = true;
                        return true;
                    }

                    return true;
                }
                // J'ai mis les borne pour eviter de deplacer si on drag pas assez
                else if ((mPreviousX > e.getX()) && ((mPreviousX - e.getX() > 50) || (mPreviousX - e.getX() < -50)))
                {
                    grille.gauche();

                    if (mutex) {
                        mutex = false;
                        requestRender();
                        mutex = true;
                        return true;
                    }
                    return true;
                }
        }
        return true;
    }


    public void anim(){
        mRenderer.anima();
        if (mutex) {
            mutex = false;
            requestRender();
            mutex = true;
        }
    }



}
