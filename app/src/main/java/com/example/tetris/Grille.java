package com.example.tetris;

import java.util.ArrayList;

public class Grille {
    private ArrayList<ArrayList<Integer>> grilleStatique;
    private ArrayList<ArrayList<Integer>> grilleDynamique;

    private int nbLigne;
    private int nbColonne;

    public Grille(int x , int y) {
        this.grilleStatique = new ArrayList<>(y);
        this.grilleDynamique = new ArrayList<>(y);

        this.nbLigne = x;
        this.nbColonne = y;
        for (int i = 0; i < y; i++) {
            ArrayList<Integer> ligne = new ArrayList<>(x);
            for (int j = 0; j < x; j++) {
                ligne.add(0);
            }
            this.grilleStatique.add(ligne);
            this.grilleDynamique.add(ligne);
        }
    }

    /**
     * Ajoute le tetromino a la grille dynamique
     * @param tetromino
     */
    public void addForme(Tetromino tetromino){
        int[][] forme = tetromino.getTableauForme();
        int milieu = nbColonne / 2;

        grilleDynamique.get(nbLigne - 1).set(milieu - 1, forme[0][0]);
        grilleDynamique.get(nbLigne - 1).set(milieu, forme[0][1]);
        grilleDynamique.get(nbLigne - 1).set(milieu + 1, forme[0][2]);

        grilleDynamique.get(nbLigne - 2).set(milieu - 1, forme[1][0]);
        grilleDynamique.get(nbLigne - 2).set(milieu, forme[1][1]);
        grilleDynamique.get(nbLigne - 2).set(milieu + 1, forme[1][2]);

    }

    public int get(int x , int y){
        return this.grilleDynamique.get(y).get(x);
    }

    public void test(){
        ArrayList<Integer> l;
        l = this.grilleStatique.get(6);
        this.grilleStatique.remove(6);
        this.grilleStatique.add(0,l);
    }

    public int getTaille(){
        return this.grilleStatique.get(0).size();
    }

    public ArrayList<ArrayList<Integer>> getGrilleStatique() {
        return grilleStatique;
    }

    public ArrayList<ArrayList<Integer>> getGrilleDynamique() {
        return grilleDynamique;
    }
}
