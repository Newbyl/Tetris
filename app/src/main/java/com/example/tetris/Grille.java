package com.example.tetris;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Grille {
    private ArrayList< ArrayList<Integer>> grille;



    public Grille(int x , int y) {
        grille = new ArrayList<>(y);
        for (int i = 0; i < y; i++) {
            ArrayList<Integer> l = new ArrayList<>(x);
            for (int j = 0; j < x; j++) {
                l.add(0);
            }
            this.grille.add(l);
        }
        ArrayList<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(1);
        l.add(0);
        l.add(1);
        l.add(0);
        l.add(1);
        l.add(1);
        grille.set(5,l);
        System.out.println(grille);
    }


    public int get(int x , int y){
        return this.grille.get(y).get(x);
    }

    public void test(){
        ArrayList<Integer> l;
        l = this.grille.get(6);
        this.grille.remove(6);
        this.grille.add(0,l);
    }

    public int getTaille(){
        return this.grille.get(0).size();
    }
}
