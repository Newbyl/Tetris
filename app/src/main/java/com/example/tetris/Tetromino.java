package com.example.tetris;

public class Tetromino {
    private int[][] tableauForme;

    public Tetromino(String forme) {
        switch (forme){
            case "I":
                this.tableauForme = new int[][]{{1, 11, 1},
                                                {0, 0, 0}};
                break;
            case "O":
                this.tableauForme = new int[][]{{2, 12, 0},
                                                {2, 2, 0}};
                break;
            case "T":
                this.tableauForme = new int[][]{{3, 13, 3},
                                                {0, 3, 0}};
                break;
            case "L":
                this.tableauForme = new int[][]{{4, 14, 4},
                                                {4, 0, 0}};
                break;
            case "J":
                this.tableauForme = new int[][]{{5, 15, 5},
                                                {0, 0, 5}};
                break;
            case "Z":
                this.tableauForme = new int[][]{{6, 16, 0},
                                                {0, 6, 6}};
                break;
            case "S":
                this.tableauForme = new int[][]{{0, 17, 7},
                                                {7, 7, 0}};
                break;
        }
    }


    public int[][] getTableauForme() {
        return tableauForme;
    }
}
