package com.example.tetris;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class Batch {
    private final String vertexShaderCode =
            "#version 300 es\n"+
                    "uniform mat4 uMVPMatrix;\n"+
                    "in vec3 vPosition;\n" +
                    "in vec4 vCouleur;\n"+
                    "out vec4 Couleur;\n"+
                    "out vec3 Position;\n"+
                    "void main() {\n" +
                    "Position = vPosition;\n"+
                    "gl_Position = uMVPMatrix * vec4(vPosition,1.0);\n" +
                    "Couleur = vCouleur;\n"+
                    "}\n";

    private final String fragmentShaderCode =
            "#version 300 es\n"+
                    "precision mediump float;\n" + // pour définir la taille d'un float
                    "in vec4 Couleur;\n"+
                    "in vec3 Position;\n"+
                    "out vec4 fragColor;\n"+
                    "void main() {\n" +
                    "float x = Position.x;\n"+
                    "float y = Position.y;\n"+
                    "float test = x*x+y*y;\n"+
                    "fragColor = Couleur;\n" +
                    "}\n";

    /* les déclarations pour l'équivalent des VBO */

    private final FloatBuffer vertexBuffer; // Pour le buffer des coordonnées des sommets du carré
    private final ShortBuffer indiceBuffer; // Pour le buffer des indices
    private final FloatBuffer colorBuffer; // Pour le buffer des couleurs des sommets

    /* les déclarations pour les shaders
    Identifiant du programme et pour les variables attribute ou uniform
     */
    private final int IdProgram; // identifiant du programme pour lier les shaders
    private int IdPosition; // idendifiant (location) pour transmettre les coordonnées au vertex shader
    private int IdCouleur; // identifiant (location) pour transmettre les couleurs
    private int IdMVPMatrix; // identifiant (location) pour transmettre la matrice PxVxM

    static final int COORDS_PER_VERTEX = 3; // nombre de coordonnées par vertex
    static final int COULEURS_PER_VERTEX = 4; // nombre de composantes couleur par vertex

    // Le tableau des couleurs
    static float[] tetraminoI = {
            .0f,  1.0f, 1.0f, 1.0f,
            .0f,  1.0f, 1.0f, 1.0f,
            .0f,  1.0f, 1.0f, 1.0f,
            .0f,  1.0f, 1.0f, 1.0f };

    static float[] tetraminoO = {
            1.0f,  1.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 0.0f, 1.0f };

    static float[] tetraminoT = {
            .67f,  .03f, 1.0f, 1.0f,
            .67f,  .03f, 1.0f, 1.0f,
            .67f,  .03f, 1.0f, 1.0f,
            .67f,  .03f, 1.0f, 1.0f };

    static float[] tetraminoL = {
            1.0f,  .67f, .06f, 1.0f,
            1.0f,  .67f, .06f, 1.0f,
            1.0f,  .67f, .06f, 1.0f,
            1.0f,  .67f, .06f, 1.0f };

    static float[] tetraminoJ = {
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f,  0.0f, 1.0f, 1.0f };

    static float[] tetraminoZ = {
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f,  0.0f, 0.0f, 1.0f };

    static float[] tetraminoS = {
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f,  1.0f, 0.0f, 1.0f };

    static float[] squareColors = {
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 1.0f, 1.0f,
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f,  0.0f, 1.0f, 1.0f };

    private final short[] Indices = { 0, 1, 2, 0, 2, 3 };

    int[] linkStatus = {0};

    private final int vertexStride = COORDS_PER_VERTEX * 4; // le pas entre 2 sommets : 4 bytes per vertex

    private final int couleurStride = COULEURS_PER_VERTEX * 4; // le pas entre 2 couleurs

    private int nbCarre;


    public Batch(ArrayList<Square> listeCarre) {
        nbCarre = listeCarre.size();

        // initialisation du buffer pour les vertex (4 bytes par float)
        ByteBuffer bb = ByteBuffer.allocateDirect(listeCarre.size() * listeCarre.get(0).getSquareCoords().length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        // initialisation du buffer pour les couleurs (4 bytes par float)
        ByteBuffer bc = ByteBuffer.allocateDirect(listeCarre.size() * tetraminoI.length * 4);
        bc.order(ByteOrder.nativeOrder());
        colorBuffer = bc.asFloatBuffer();

        // initialisation du buffer des indices
        ByteBuffer dlb = ByteBuffer.allocateDirect(listeCarre.size() * Indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indiceBuffer = dlb.asShortBuffer();

        int posVer = 0;
        int posCol = 0;
        int posInd = 0;

        for (Square s : listeCarre) {
            vertexBuffer.put(s.getSquareCoords());
            vertexBuffer.position(0);

            switch (s.getTypeT()){
                case 0:
                    colorBuffer.put(tetraminoI);
                    break;
                case 1:
                    colorBuffer.put(tetraminoO);
                    break;
                case 2:
                    colorBuffer.put(tetraminoT);
                    break;
                case 3:
                    colorBuffer.put(tetraminoL);
                    break;
                case 4:
                    colorBuffer.put(tetraminoJ);
                    break;
                case 5:
                    colorBuffer.put(tetraminoZ);
                    break;
                case 6:
                    colorBuffer.put(tetraminoS);
                    break;
                case 7:
                    colorBuffer.put(squareColors);
                    break;
            }

            colorBuffer.position(0);

            indiceBuffer.put(Indices);
            indiceBuffer.position(0);

            posVer = posVer + s.getSquareCoords().length * 4;
            posCol = posCol + tetraminoI.length * 4;
            posInd = posInd + Indices.length * 2;


        }

        /* Chargement des shaders */
        int vertexShader = MyGLRenderer.loadShader(
                GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        IdProgram = GLES30.glCreateProgram();             // create empty OpenGL Program
        GLES30.glAttachShader(IdProgram, vertexShader);   // add the vertex shader to program
        GLES30.glAttachShader(IdProgram, fragmentShader); // add the fragment shader to program
        GLES30.glLinkProgram(IdProgram);                  // create OpenGL program executables
        GLES30.glGetProgramiv(IdProgram, GLES30.GL_LINK_STATUS,linkStatus,0);
    }


    /* La fonction Display */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES30.glUseProgram(IdProgram);

        // get handle to shape's transformation matrix
        IdMVPMatrix = GLES30.glGetUniformLocation(IdProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(IdMVPMatrix, 1, false, mvpMatrix, 0);


        // get handle to vertex shader's vPosition member et vCouleur member
        IdPosition = GLES30.glGetAttribLocation(IdProgram, "vPosition");
        IdCouleur = GLES30.glGetAttribLocation(IdProgram, "vCouleur");

        /* Activation des Buffers */
        GLES30.glEnableVertexAttribArray(IdPosition);
        GLES30.glEnableVertexAttribArray(IdCouleur);

        /* Lecture des Buffers */
        GLES30.glVertexAttribPointer(
                IdPosition, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES30.glVertexAttribPointer(
                IdCouleur, COULEURS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                couleurStride, colorBuffer);


        // Draw the square
        GLES30.glDrawElements(
                GLES30.GL_TRIANGLES, Indices.length * nbCarre,
                GLES30.GL_UNSIGNED_SHORT, indiceBuffer);


        // Disable vertex array
        GLES30.glDisableVertexAttribArray(IdPosition);
        GLES30.glDisableVertexAttribArray(IdCouleur);

    }
}