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

import com.example.tetris.Formes.Cercle;
import com.example.tetris.Formes.Forme;
import com.example.tetris.Formes.Losange;
import com.example.tetris.Formes.Pentagone;
import com.example.tetris.Formes.Square;
import com.example.tetris.Formes.Star;
import com.example.tetris.Formes.Star7;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* MyGLRenderer implémente l'interface générique GLSurfaceView.Renderer */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private float width;
    private float height;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private final float[] mSquarePosition = {0.0f, 0.0f};

    private Grille grille;

    private final ArrayList<String> listePieces = new ArrayList<>();
    private final ArrayList<String> piecesPossible= new ArrayList<String>(7);

    private String typeForme = "Square";
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    // Variable servant à calculer la latence d'une action
    double totalTime;
    double totalFrames;


    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch2 = new float[16];
        int taille = Integer.min((int) (width/grille.getNbColonne()/2),(int) (height/grille.getNbLigne()/2));

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mViewMatrix,0);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        mModelMatrix = setOrigin(mModelMatrix,taille);

        if (grille.estVide()){
            aforme();
        }

        long start = System.nanoTime();

        drawGrilleOpt(scratch2, grille, taille);

        long stop = System.nanoTime();
        this.totalTime += stop - start;
        this.totalFrames++;

        Log.d("Latence", (this.totalTime / this.totalFrames) / 1000000 + " ms");
    }


    public float[] setOrigin(float[] mModelMatrix, int taille){
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix,0,-(float)width/2+taille,-(float)height / 2+taille,0);

        return mModelMatrix;
    }

    /**
     * Ancienne methode pour dessiner les carré un par un
     */
    public void drawGrille(float[] scratch, Grille grille, int taille) {
        for (int i = 0; i < grille.getNbColonne(); i++) {
            for (int j = 0; j < grille.getNbLigne(); j++) {
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
                mSquarePosition[0] = (i * (taille * 2));
                mSquarePosition[1] = (j * (taille * 2));

                if (grille.getGrilleStatique().get(j).get(i) != 0 || grille.getGrilleDynamique().get(j).get(i) != 0){
                    new Square(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),grille.getGrilleStatique().get(j).get(i))-1, taille);
                }


                mModelMatrix = setOrigin(mModelMatrix,taille);
            }
        }
    }

    /**
     * Methode pour dessiner les carré de maniere optimiser grace au Batching
     */
    public void drawGrilleOpt(float[] scratch, Grille grille, int taille) {
        ArrayList<Forme> listSquare = new ArrayList<>();

        for (int i = 0; i < grille.getNbColonne(); i++) {
            for (int j = 0; j < grille.getNbLigne(); j++) {
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);
                mSquarePosition[0] = (i * (taille * 2));
                mSquarePosition[1] = (j * (taille * 2));

                if (grille.getGrilleStatique().get(j).get(i) != 0 || grille.getGrilleDynamique().get(j).get(i) != 0){
                    switch (typeForme){
                        case "Square":
                            listSquare.add(
                                    new Square(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                        case "Cercle":
                            listSquare.add(
                                    new Cercle(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                        case "Losange":
                            listSquare.add(
                                    new Losange(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                        case "Pentagone":
                            listSquare.add(
                                    new Pentagone(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                        case "Star":
                            listSquare.add(
                                    new Star(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                        case "Star7":
                            listSquare.add(
                                    new Star7(mSquarePosition,Integer.max(grille.getGrilleDynamique().get(j).get(i),
                                            grille.getGrilleStatique().get(j).get(i))-1, taille)
                            );
                            break;
                    }
                }
                mModelMatrix = setOrigin(mModelMatrix,taille);
            }
        }
        if (!listSquare.isEmpty())
            new Batch(listSquare).draw(scratch);
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        Matrix.orthoM(mProjectionMatrix, 0,
                -(float)width / 2, (float)width / 2,
                -(float)height / 2, (float)height / 2,
                -1.0f, 1.0f);

        this.height = height;
        this.width = width;
    }


    public static int loadShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }


    public void setPosition(float x, float y) {
        mSquarePosition[0] = x;
        mSquarePosition[1] = y;
    }


    public void initForme() {
        piecesPossible.add("I");
        piecesPossible.add("O");
        piecesPossible.add("T");
        piecesPossible.add("L");
        piecesPossible.add("J");
        piecesPossible.add("S");
        piecesPossible.add("Z");

        Random random = new Random();
        listePieces.add(0, piecesPossible.get(random.nextInt(piecesPossible.size())));
        listePieces.add(1, piecesPossible.get(random.nextInt(piecesPossible.size())));
    }


    public void aforme(){
        Tetromino tetromino = new Tetromino(listePieces.get(0));
        listePieces.set(0, listePieces.get(1));
        remplissageListePiece();
        grille.addForme(tetromino);
    }


    public void remplissageListePiece() {
        Random random = new Random();
        listePieces.add(1, piecesPossible.get(random.nextInt(piecesPossible.size())));
    }


    public Boolean anima(){
        this.grille.test();
        return this.grille.testLigneComplete();
    }


    public Boolean diffZero(ArrayList<Integer> arr) {
        boolean containsNonZero = false;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != 0) {
                containsNonZero = true;
                break;
            }
        }

        return containsNonZero;
    }


    public Boolean testLose(){
        ArrayList<ArrayList<Integer>> statique = grille.getGrilleStatique();

        return diffZero(statique.get(statique.size() - 1));
    }


    public void setGrille(Grille grille) {
        this.grille = grille;
    }


    public String getPiecePrev() {
        return listePieces.get(0);
    }

    public String getTypeForme() {
        return typeForme;
    }

    public void setTypeForme(String typeForme) {
        this.typeForme = typeForme;
    }
}
