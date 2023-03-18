package com.example.tetris;

public class Tetromino {
    private int[][] tableauForme;

    public Tetromino(String forme) {
        switch (forme){
            case "I":
                this.tableauForme = new int[][]{{1, 1, 1},
                                                {0, 0, 0}};
                break;
            case "O":
                this.tableauForme = new int[][]{{2,2,0},
                                                {2,2,0}};
                break;
            case "T":
                this.tableauForme = new int[][]{{3, 3, 3},
                                                {0, 3, 0}};
                break;
            case "L":
                this.tableauForme = new int[][]{{4, 4, 4},
                                                {4, 0, 0}};
                break;
            case "J":
                this.tableauForme = new int[][]{{5, 5, 5},
                                                {0, 5, 0}};
                break;
            case "Z":
                this.tableauForme = new int[][]{{6, 6, 0},
                                                {0, 6, 6}};
                break;
            case "S":
                this.tableauForme = new int[][]{{0, 7, 7},
                                                {7, 7, 0}};
                break;
        }
    }


    public int[][] getTableauForme() {
        return tableauForme;
    }
}
