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
public class Square implements Forme{
    float X;
    float[] squareCoords;
    private final int typeT;

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

    public float[] getSquareCoords() {
        return squareCoords;
    }
}
