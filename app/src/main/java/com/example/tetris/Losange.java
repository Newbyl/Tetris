package com.example.tetris;

public class Losange implements Forme {
    float X;
    float[] losangeCoords;
    private final int typeT;

    public Losange(float[] Pos, int type, float taille) {
        X = taille;

        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = Pos[0];
        position[1] = Pos[1];

        losangeCoords = new float[]{
                -X + position[0], X + position[1], 0.0f,
                -X + position[0], -X + position[1], 0.0f,
                X + position[0], -X + position[1], 0.0f,
                X + position[0], X + position[1], 0.0f};
    }

    public int getTypeT() {
        return typeT;
    }

    public float[] getLosangeCoords() {
        return losangeCoords;
    }
}
