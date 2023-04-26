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

/**
 * Crée un carré
 */
public class Square implements Forme {
    float X;
    float[] squareCoords;
    private final int typeT;

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







    public Square(float[] Pos, int type, float taille) {
        X = taille;

        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = Pos[0];
        position[1] = Pos[1];

        squareCoords = new float[]{
                -X + position[0], X + position[1], 0.0f,
                -X + position[0], -X + position[1], 0.0f,
                X + position[0], -X + position[1], 0.0f,
                X + position[0], X + position[1], 0.0f};
    }

    public int getTypeT() {
        return typeT;
    }



    public float[] getCoords() {
        return squareCoords ;
    }

    @Override
    public float[] getCouleur() {
        switch (typeT){
            case 0:
            case 10:
                return tetraminoI;
            case 1:
            case 11:
                return tetraminoO;

            case 2:
            case 12:
                return tetraminoT;

            case 3:
            case 13:
                return tetraminoL;

            case 4:
            case 14:
                return tetraminoJ;

            case 5:
            case 15:
                return tetraminoZ;

            case 6:
            case 16:
                return tetraminoS;

            default:
                return squareColors;

        }
    }

    public short[] getIndices() {
        return new short[]{0, 1, 2, 0, 2, 3};
    }

}
