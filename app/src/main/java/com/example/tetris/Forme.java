package com.example.tetris;

/**
 * Interface pour gerer plus facilement plusieurs formes
 */
public interface Forme {
 public float[] getCoords();

 public float[] getCouleur();

 public short[] getIndices();

}
