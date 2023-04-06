package com.example.tetris;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PreviewRenderer implements GLSurfaceView.Renderer{
    private float width;
    private float height;
    // Les matrices habituelles Model/View/Projection

    private float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];

    private Grille prev;

    private float[] mSquarePosition = {0.0f, 0.0f};

    private Tetromino tetromino;

    public float[] setOrigin(float[] mModelMatrix, int taille){
        Matrix.setIdentityM(mModelMatrix,0);
        Matrix.translateM(mModelMatrix,0,-(float)width/2+taille,-(float)height / 2+taille,0);

        return mModelMatrix;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // la couleur du fond d'écran
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // la couleur du fond d'écran
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glViewport(0, 0, width, height);
        Matrix.orthoM(mProjectionMatrix, 0, -(float)width / 2, (float)width / 2, -(float)height / 2, (float)height / 2, -1.0f, 1.0f);
        this.height = height;
        this.width = width;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch2 = new float[16]; // pour stocker une matrice
        int taille = Integer.min((int) (width/ prev.getNbColonne()/2),(int) (height/ prev.getNbLigne()/2));
        // glClear rien de nouveau on vide le buffer d,e couleur et de profondeur */
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        /* on utilise une classe Matrix (similaire à glm) pour définir nos matrices P, V et M*/
        Matrix.setIdentityM(mViewMatrix,0);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        mModelMatrix = setOrigin(mModelMatrix,taille);


        if (tetromino != null){
            prev.viderGrille();
            prev.addForme(tetromino);
        }




        drawGrilleOpt(scratch2, prev, taille);
        //Log.d("GRILLE", prev.toString());
    }

    public void setPrev(Grille prev) {
        this.prev = prev;
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
                    //listSquare.add(new Square(mSquarePosition,7, taille));
                }


                mModelMatrix = setOrigin(mModelMatrix,taille);
            }
        }

        if (!listSquare.isEmpty())
            new Batch(listSquare).draw(scratch);
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

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }


}
