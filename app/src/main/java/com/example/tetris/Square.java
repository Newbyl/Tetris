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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


//Dessiner un carré

public class Square {
/* Le vertex shader avec la définition de gl_Position et les variables utiles au fragment shader
 */
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

 // Pour le buffer des coordonnées des sommets du carré
 // Pour le buffer des indices
// Pour le buffer des couleurs des sommets

    /* les déclarations pour les shaders
    Identifiant du programme et pour les variables attribute ou uniform
     */
// identifiant du programme pour lier les shaders
    private int IdPosition; // idendifiant (location) pour transmettre les coordonnées au vertex shader
    private int IdCouleur; // identifiant (location) pour transmettre les couleurs
    private int IdMVPMatrix; // identifiant (location) pour transmettre la matrice PxVxM

    static final int COORDS_PER_VERTEX = 3; // nombre de coordonnées par vertex
    static final int COULEURS_PER_VERTEX = 4; // nombre de composantes couleur par vertex

    int[] linkStatus = {0};

    float X;

    /* Attention au repère au niveau écran (x est inversé)
     Le tableau des coordonnées des sommets
     Oui ce n'est pas joli avec 1.0 en dur ....
     */



    public float[] getSquareCoords() {
        return squareCoords;
    }

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


    // Le carré est dessiné avec 2 triangles
    private final short Indices[] = { 0, 1, 2, 0, 2, 3 };

    private final int vertexStride = COORDS_PER_VERTEX * 4; // le pas entre 2 sommets : 4 bytes per vertex

    private final int couleurStride = COULEURS_PER_VERTEX * 4; // le pas entre 2 couleurs

    private final float Position[] = {0.0f,0.0f};

    float squareCoords[];

    private int typeT;

    public int getTypeT() {
        return typeT;
    }

    public Square(float[] Pos, int type, float taille) {

        X = taille;

        typeT = type;

        Position[0] = Pos[0];
        Position[1] = Pos[1];

        squareCoords = new float[]{
                -X + Position[0], X + Position[1], 0.0f,
                -X + Position[0], -X + Position[1], 0.0f,
                X + Position[0], -X + Position[1], 0.0f,
                X + Position[0], X + Position[1], 0.0f};
    }





    public void set_position(float[] pos) {
        Position[0]=pos[0];
        Position[1]=pos[1];
    }





}
