package com.example.tetris;

import java.util.ArrayList;

public class Grille {
    private ArrayList<ArrayList<Integer>> grilleStatique;
    private ArrayList<ArrayList<Integer>> grilleDynamique;
    private ArrayList<ArrayList<Integer>> grilleVide;

    private int nbLigne;
    private int nbColonne;

    public Grille(int nbLigne, int nbColonne) {
        this.grilleStatique = new ArrayList<>(nbLigne);
        this.grilleDynamique = new ArrayList<>(nbLigne);
        this.grilleVide = new ArrayList<>(nbLigne);


        this.nbLigne = nbLigne;
        this.nbColonne = nbColonne;
        for (int i = 0; i < nbLigne; i++) {
            ArrayList<Integer> ligne = new ArrayList<>(nbColonne);
            ArrayList<Integer> ligne2 = new ArrayList<>(nbColonne);
            ArrayList<Integer> ligne3 = new ArrayList<>(nbColonne);
            for (int j = 0; j < nbColonne; j++) {
                ligne.add(0);
                ligne2.add(0);
                ligne3.add(0);
            }
            this.grilleStatique.add(ligne2);
            this.grilleDynamique.add(ligne);
            this.grilleVide.add(ligne3);
        }
    }

    /**
     * Ajoute le tetromino a la grille dynamique
     * @param tetromino
     */
    public void addForme(Tetromino tetromino){

        if (estVide()) {
            int[][] forme = tetromino.getTableauForme();
            int milieu = nbColonne / 2;

            grilleDynamique.get(nbLigne - 1).set(milieu - 1, forme[0][0]);
            grilleDynamique.get(nbLigne - 1).set(milieu, forme[0][1]);
            grilleDynamique.get(nbLigne - 1).set(milieu + 1, forme[0][2]);

            grilleDynamique.get(nbLigne - 2).set(milieu - 1, forme[1][0]);
            grilleDynamique.get(nbLigne - 2).set(milieu, forme[1][1]);
            grilleDynamique.get(nbLigne - 2).set(milieu + 1, forme[1][2]);
        }

    }

    public int get(int x , int y){
        return this.grilleDynamique.get(y).get(x);
    }

    public void test(){
        ArrayList<ArrayList<Integer>> tmp = clone(grilleVide);
        //System.out.println(tmp);
        boolean end = false;
        for (int i = 0; i < nbColonne; i++) {
            if (grilleDynamique.get(0).get(i) != 0) {
                end = true;
                break;
            }
        }
        if (!end) {
            for (int i = 1; i < nbLigne; i++) {
                for (int j = 0; j < nbColonne; j++) {
                    tmp.get(i - 1).set(j, grilleDynamique.get(i).get(j));
                }
            }

            if (testCollision(tmp)) {
                recopieDynamiqueVersStatique();
            } else {
                this.grilleDynamique = clone(tmp);
            }
        }
        else {
            recopieDynamiqueVersStatique();
        }
    }


    public void droit(){
        ArrayList<ArrayList<Integer>> tmp = clone(grilleVide);
        //System.out.println(tmp);
        boolean end = false;
        for (int i = 0; i < nbLigne; i++) {
            if (grilleDynamique.get(i).get(nbColonne - 1) != 0) {
                end = true;
                break;
            }
        }
        if (!end) {
            for (int i = 0; i < nbLigne; i++) {
                for (int j = nbColonne-2; j >= 0; j--) {
                    tmp.get(i).set(j+1, grilleDynamique.get(i).get(j));
                }
            }

            if (!testCollision(tmp)) {
                this.grilleDynamique = clone(tmp);
            }
        }
    }


    public void gauche(){
        ArrayList<ArrayList<Integer>> tmp = clone(grilleVide);
        boolean end = false;
        for (int i = 0; i < nbLigne; i++) {
            if (grilleDynamique.get(i).get(0)!=0){
                end = true;
            }
        }
        if (!end) {
            for (int i = 0; i < nbLigne; i++) {
                for (int j = 1; j < nbColonne; j++) {
                    tmp.get(i).set(j-1, grilleDynamique.get(i).get(j));
                }
            }

            if (!testCollision(tmp)) {
                this.grilleDynamique = clone(tmp);
            }
        }


    }

    public ArrayList<ArrayList<Integer>> getGrilleVide() {
        return grilleVide;
    }

    public void recopieDynamiqueVersStatique() {
        for (int i = 0; i < nbLigne; i++)
            for (int j = 0 ; j < nbColonne; j++)
                if (grilleDynamique.get(i).get(j) != 0) {
                    grilleStatique.get(i).set(j, grilleDynamique.get(i).get(j));
                }
        grilleDynamique = clone(grilleVide);
    }

    public Boolean testCollision(ArrayList<ArrayList<Integer>> tmp) {
        for (int i = 0; i < nbLigne; i++)
            for (int j = 0 ; j < nbColonne; j++){
                if (tmp.get(i).get(j) != 0 && grilleStatique.get(i).get(j) >= 1) {
                    return true;
                }
            }

        return false;
    }

    public Boolean testLigneComplete() {
        int cpt = 0;
        for (int i = 0; i < nbLigne; i++)
            for (int j = 0 ; j < nbColonne; j++){
                if (grilleStatique.get(i).get(j) != 0) {
                    cpt++;
                    if (cpt == nbColonne)
                    {
                        grilleStatique.remove(i);
                        ArrayList<Integer> l = this.grilleDynamique.get(nbLigne - 4);
                        grilleStatique.add(nbLigne - 1, l);

                        return true;
                    }
                }
                else {
                    cpt = 0;
                    break;
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


    public ArrayList<ArrayList<Integer>> clone(ArrayList<ArrayList<Integer>> source){
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>(nbLigne);
        for (int i = 0; i < nbLigne; i++) {
            ArrayList<Integer> ligne = new ArrayList<>(nbColonne);
            for (int j = 0; j < nbColonne; j++) {
                ligne.add(source.get(i).get(j));
            }
            tmp.add(ligne);
        }
        return tmp;
    }


    public Boolean estVide(){
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                if (grilleDynamique.get(i).get(j)!=0){
                    return false;
                }
            }
        }
        return true;
    }
    public int getNbLigne() {
        return nbLigne;
    }



    public int getNbColonne() {
        return nbColonne;
    }


}
