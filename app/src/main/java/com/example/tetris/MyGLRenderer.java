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

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* MyGLRenderer implémente l'interface générique GLSurfaceView.Renderer */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Square mSquare;
    private Square mSquare2;
    private int frame = 0;
    private float width;
    private float height;
    // Les matrices habituelles Model/View/Projection

    private float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];

    private float[] mSquarePosition = {0.0f, 0.0f};

    private Grille grille;

    /* Première méthode équivalente à la fonction init en OpenGLSL */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // la couleur du fond d'écran
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        /* on va définir une classe Square pour dessiner des carrés */
        //mSquare = new Square(mSquarePosition, 0, 50);

    }


    double totalTime;
    double totalFrames;


    /* Deuxième méthode équivalente à la fonction Display */
    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16]; // pour stocker une matrice
        float[] scratch2 = new float[16]; // pour stocker une matrice
        int taille = Integer.min((int) (width/grille.getNbColonne()/2),(int) (height/grille.getNbLigne()/2));
        // glClear rien de nouveau on vide le buffer d,e couleur et de profondeur */
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        /* on utilise une classe Matrix (similaire à glm) pour définir nos matrices P, V et M*/

        /*Si on souhaite positionner une caméra mais ici on n'en a pas besoin*/
       // Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
         /* Pour le moment on va utiliser une projection orthographique
           donc View = Identity
         */
        Matrix.setIdentityM(mViewMatrix,0);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        mModelMatrix = setOrigin(mModelMatrix,taille);


        /* Pour définir une translation on donne les paramètres de la translation
        et la matrice (ici mModelMatrix) est multipliée par la translation correspondante
         */
        //Matrix.translateM(mModelMatrix, 0, mSquarePosition[0], mSquarePosition[1], 0);
        /* scratch est la matrice PxVxM finale */
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);


        /* on appelle la méthode dessin du carré élémentaire */
        //mSquare.draw(scratch);



        if (grille.estVide()){
            aforme();
        }

        long start = System.nanoTime();
        //drawGrille(scratch2, grille, taille);
        drawGrilleOpt(scratch2, grille, taille);

        long stop = System.nanoTime();

        this.totalTime += stop - start;
        this.totalFrames++;

        Log.d("TEST", (this.totalTime / this.totalFrames) / 1000000 + " ms");
    }



    public float[] setOrigin(float[] mModelMatrix, int taille){
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix,0,-(float)width/2+taille,-(float)height / 2+taille,0);

        return mModelMatrix;
    }

    public void drawGrille(float[] scratch, Grille grille, int taille) {
        for (int i = 0; i < grille.getNbColonne(); i++) {
            for (int j = 0; j < grille.getNbLigne(); j++) {
                //Matrix.translateM(mModelMatrix, 0, (i * (taille * 2)),  (j * (taille * 2)) , 0);
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
                mSquarePosition[0] = (i * (taille * 2));
                mSquarePosition[1] = (j * (taille * 2));

                if (grille.getGrilleStatique().get(j).get(i) != 0 || grille.getGrilleDynamique().get(j).get(i) != 0){
                    new Square(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),grille.getGrilleStatique().get(j).get(i))-1, taille);
                }
                else{

                    //new Square(mSquarePosition,7, taille).draw(scratch);
                }

                mModelMatrix = setOrigin(mModelMatrix,taille);
            }
        }
    }


    public void drawGrilleOpt(float[] scratch, Grille grille, int taille) {
        ArrayList<Square> listSquare = new ArrayList<>();

        for (int i = 0; i < grille.getNbColonne(); i++) {
            for (int j = 0; j < grille.getNbLigne(); j++) {
                //Matrix.translateM(mModelMatrix, 0, (i * (taille * 2)),  (j * (taille * 2)) , 0);
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
                mSquarePosition[0] = (i * (taille * 2));
                mSquarePosition[1] = (j * (taille * 2));

                if (grille.getGrilleStatique().get(j).get(i) != 0 || grille.getGrilleDynamique().get(j).get(i) != 0){
                    listSquare.add(
                        new Square(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),grille.getGrilleStatique().get(j).get(i))-1, taille)
                    );
                }
                else {
                    listSquare.add(
                            new Square(mSquarePosition,7, taille)
                    );
                }


                mModelMatrix = setOrigin(mModelMatrix,taille);
            }
        }

        if (!listSquare.isEmpty())
            new Batch(listSquare).draw(scratch);
    }


    /* équivalent au Reshape en OpenGLSL */
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        /* ici on aurait pu se passer de cette méthode et déclarer
        la projection qu'à la création de la surface !!
         */
        GLES30.glViewport(0, 0, width, height);
        Matrix.orthoM(mProjectionMatrix, 0, -(float)width / 2, (float)width / 2, -(float)height / 2, (float)height / 2, -1.0f, 1.0f);
        this.height=height;
        this.width = width;
    }

    /* La gestion des shaders ... */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }


    /* Les méthodes nécessaires à la manipulation de la position finale du carré */
    public void setPosition(float x, float y) {
        /*mSquarePosition[0] += x;
        mSquarePosition[1] += y;*/
        mSquarePosition[0] = x;
        mSquarePosition[1] = y;

    }

    public void aforme(){
        ArrayList<String> tt = new ArrayList<String>();
        tt.add("I");
        tt.add("O");
        tt.add("T");
        tt.add("L");
        tt.add("J");
        Random rand = new Random();

        grille.addForme(new Tetromino(tt.get(rand.nextInt(tt.size()))));
    }

    public void anima(){
        this.grille.test();
        this.grille.testLigneComplete();
    }

    public float[] getPosition() {
        return mSquarePosition;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

}
