package com.example.tetris.Preview;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.tetris.Batch;
import com.example.tetris.Formes.Cercle;
import com.example.tetris.Formes.Forme;
import com.example.tetris.Formes.Losange;
import com.example.tetris.Formes.Pentagone;
import com.example.tetris.Formes.Square;
import com.example.tetris.Formes.Star;
import com.example.tetris.Formes.Star7;
import com.example.tetris.Logique.Grille;
import com.example.tetris.Logique.Tetromino;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PreviewRenderer implements GLSurfaceView.Renderer{

    private float width;
    private float height;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];

    private Grille prev;
    private final float[] mSquarePosition = {0.0f, 0.0f};
    private Tetromino tetromino;
    private String typeForme = "Square";

    public float[] setOrigin(float[] mModelMatrix, int taille){
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix,0,-(float)width/2+taille,-(float)height / 2+taille,0);

        return mModelMatrix;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }


    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glViewport(0, 0, width, height);
        Matrix.orthoM(mProjectionMatrix, 0, -(float)width / 2,
                (float)width / 2,
                -(float)height / 2,
                (float)height / 2, -1.0f, 1.0f);

        this.height = height;
        this.width = width;
    }


    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch2 = new float[16];
        int taille = Integer.min((int) (width/ prev.getNbColonne()/2),(int) (height/ prev.getNbLigne()/2));

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        Matrix.setIdentityM(mViewMatrix,0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        mModelMatrix = setOrigin(mModelMatrix,taille);

        if (tetromino != null){
            prev.viderGrille();
            prev.addForme(tetromino);
        }

        drawGrilleOpt(scratch2, prev, taille);
    }


    public void setPrev(Grille prev) {
        this.prev = prev;
    }


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


    /* La gestion des shaders ... */
    public static int loadShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }

    public void setTypeForme(String typeForme) {
        this.typeForme = typeForme;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }
}
