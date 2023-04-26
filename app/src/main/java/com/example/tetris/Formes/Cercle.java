package com.example.tetris.Formes;

public class Cercle implements Forme {
    private float X;
    private int typeT;
    private float[] circleCoords;
    private float[] colors;

    public Cercle(float[] pos, int type, float size) {
        X = size;
        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = pos[0];
        position[1] = pos[1];

        int numPoints = 50;
        circleCoords = new float[3 * (numPoints + 1)];
        colors = new float[4 * (numPoints + 1)];


        circleCoords[0] = position[0];
        circleCoords[1] = position[1];
        circleCoords[2] = 0.0f;


        colors[0] = 1.0f;
        colors[1] = 1.0f;
        colors[2] = 1.0f;
        colors[3] = 1.0f;

        float x1;
        float x2;
        float x3;

        switch (typeT){
            case 0:
            case 10:
                x1 = .0f;
                x2 = 1.0f;
                x3 = 1.0f;
                break;
            case 1:
            case 11:
                x1 = 1.0f;
                x2 = 1.0f;
                x3 = .0f;
                break;

            case 2:
            case 12:
                x1 = .67f;
                x2 = 0.03f;
                x3 = 1.0f;
                break;
            case 3:
            case 13:
                x1 = 1.0f;
                x2 = 0.67f;
                x3 = 0.06f;
                break;

            case 4:
            case 14:
                x1 = .0f;
                x2 = .0f;
                x3 = 1.0f;
                break;

            case 5:
            case 15:
                x1 = 1.0f;
                x2 = .0f;
                x3 = .0f;
                break;

            case 6:
            case 16:
                x1 = .0f;
                x2 = 1.0f;
                x3 = .0f;
                break;

            default:
                x1 = .0f;
                x2 = .0f;
                x3 = .0f;
                break;

        }


        for (int i = 1; i <= numPoints; i++) {
            float angle = (float) (2.0f * Math.PI * i / numPoints);
            float x = X * (float) Math.cos(angle) + position[0];
            float y = X * (float) Math.sin(angle) + position[1];

            circleCoords[3 * i] = x;
            circleCoords[3 * i + 1] = y;
            circleCoords[3 * i + 2] = 0.0f;

            colors[4 * i] = x1;
            colors[4 * i + 1] = x2;
            colors[4 * i + 2] = x3;
            colors[4 * i + 3] = 1.0f;
        }
    }



    @Override
    public float[] getCoords() {
        return circleCoords;
    }

    @Override
    public float[] getCouleur() {
        return colors;
    }

    public short[] getIndices() {
        int numPoints = 50;
        short[] indices = new short[3 * numPoints];

        for (int i = 0; i < numPoints; i++) {
            indices[3 * i] = 0;
            indices[3 * i + 1] = (short) (i + 1);
            indices[3 * i + 2] = (short) (i + 2);
        }


        indices[3 * numPoints - 1] = 1;

        return indices;
    }
}
