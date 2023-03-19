package com.example.tetris;

import java.util.ArrayList;

public class Grille {
    private ArrayList<ArrayList<Integer>> grilleStatique;
    private ArrayList<ArrayList<Integer>> grilleDynamique;

    private int nbLigne;
    private int nbColonne;

    public Grille(int nbLigne, int nbColonne) {
        this.grilleStatique = new ArrayList<>(nbLigne);
        this.grilleDynamique = new ArrayList<>(nbLigne);

        this.nbLigne = nbLigne;
        this.nbColonne = nbColonne;
        for (int i = 0; i < nbLigne; i++) {
            ArrayList<Integer> ligne = new ArrayList<>(nbColonne);
            ArrayList<Integer> ligne2 = new ArrayList<>(nbColonne);
            for (int j = 0; j < nbColonne; j++) {
                ligne.add(0);
                ligne2.add(0);
            }
            this.grilleStatique.add(ligne2);
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
        l = this.grilleDynamique.get(0);
        this.grilleDynamique.remove(0);
        this.grilleDynamique.add(nbLigne-1,l);
    }


    public void recopieDynamiqueVersStatique() {
        for (int i = 0; i < nbLigne; i++)
            for (int j = 0 ; j < nbColonne; j++)
                if (grilleDynamique.get(i).get(j) != 0)
                    grilleStatique.get(i).set(j, grilleDynamique.get(i).get(j));
    }

    public Boolean testCollision() {
        for (int i = 0; i < nbLigne-1; i++)
            for (int j = 0 ; j < nbColonne; j++){
                if ((grilleDynamique.get(i).get(j) != 0 && grilleStatique.get(i+1).get(j) >= 1)
                        || (grilleDynamique.get(i).get(j) != 0 && i == 0)) {
                    recopieDynamiqueVersStatique();
                    return true;
                }
            }
        return false;
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

    public int getNbLigne() {
        return nbLigne;
    }



    public int getNbColonne() {
        return nbColonne;
    }


}
