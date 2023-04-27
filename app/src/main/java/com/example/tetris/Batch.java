package com.example.tetris;

import android.opengl.GLES30;
import android.util.Log;

import com.example.tetris.Formes.Forme;
import com.example.tetris.Game.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

/**
 * Classe permettant de faire du "Batching"
 * Technique qui met tout les triangles à dessiner dans un "Batch"
 * qui permet de tout dessiner d'un coup sans faire plusieurs appels
 * à la carte graphique et ainsi gagner en performance
 */
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

    // nombre de coordonnées par vertex
     // nombre de composantes couleur par vertex




    private int COORDS_PER_VERTEX;
    private int COULEURS_PER_VERTEX;
    private int IND_PER_VERTEX;
    private int lastIndice = 0;

    int[] linkStatus = {0};

    private final int nbCarre;


    public Batch(ArrayList<Forme> listeCarre) {
        nbCarre = listeCarre.size();
        COORDS_PER_VERTEX = 3;
        Log.d("NAB", "COORD:  "+ COORDS_PER_VERTEX);
        COULEURS_PER_VERTEX = 4;
        Log.d("NAB", "COL:  "+ COULEURS_PER_VERTEX);
        IND_PER_VERTEX = listeCarre.get(0).getIndices().length;
        // initialisation du buffer pour les vertex (4 bytes par float)
        ByteBuffer bb = ByteBuffer.allocateDirect(listeCarre.size() * listeCarre.get(0).getCoords().length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        // initialisation du buffer pour les couleurs (4 bytes par float)
        ByteBuffer bc = ByteBuffer.allocateDirect(listeCarre.size() * listeCarre.get(0).getCouleur().length * 4);
        bc.order(ByteOrder.nativeOrder());
        colorBuffer = bc.asFloatBuffer();

        // initialisation du buffer des indices
        ByteBuffer dlb = ByteBuffer.allocateDirect(listeCarre.size() * listeCarre.get(0).getIndices().length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indiceBuffer = dlb.asShortBuffer();

        for (Forme s : listeCarre) {
            vertexBuffer.put(s.getCoords());
            colorBuffer.put(s.getCouleur());
            short [] indices = s.getIndices();
            indices = indiceplus(indices,lastIndice);
            lastIndice += max(s.getIndices())+1;
            indiceBuffer.put(indices);

        }

        indiceBuffer.position(0);
        vertexBuffer.position(0);
        colorBuffer.position(0);

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
        // identifiant (location) pour transmettre la matrice PxVxM
        int idMVPMatrix = GLES30.glGetUniformLocation(IdProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(idMVPMatrix, 1, false, mvpMatrix, 0);


        // get handle to vertex shader's vPosition member et vCouleur member
        // idendifiant (location) pour transmettre les coordonnées au vertex shader
        int idPosition = GLES30.glGetAttribLocation(IdProgram, "vPosition");
        // identifiant (location) pour transmettre les couleurs
        int idCouleur = GLES30.glGetAttribLocation(IdProgram, "vCouleur");

        /* Activation des Buffers */
        GLES30.glEnableVertexAttribArray(idPosition);
        GLES30.glEnableVertexAttribArray(idCouleur);

        /* Lecture des Buffers */
        // le pas entre 2 sommets : 4 bytes per vertex
        int vertexStride = COORDS_PER_VERTEX * 4;
        GLES30.glVertexAttribPointer(
                idPosition, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // le pas entre 2 couleurs
        int couleurStride = COULEURS_PER_VERTEX * 4;
        GLES30.glVertexAttribPointer(
                idCouleur, COULEURS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                couleurStride, colorBuffer);


        // Draw the square
        GLES30.glDrawElements(
                GLES30.GL_TRIANGLES, IND_PER_VERTEX * nbCarre,
                GLES30.GL_UNSIGNED_SHORT, indiceBuffer);


        // Disable vertex array
        GLES30.glDisableVertexAttribArray(idPosition);
        GLES30.glDisableVertexAttribArray(idCouleur);

    }


    public short[] indiceplus(short[] indices , int lastIndice){
        for (int i = 0; i < indices.length; i++) {
            indices[i] +=lastIndice;
        }
        return indices;
    }

    public short max(short[] in){
        short m = in[0];
        for (short s:
             in) {
            if (s > m){
                m = s;
            }
        }
        return m;
    }
}
