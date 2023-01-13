package com.thg.accelerator23.connectn.ai.rosseleanor.model;

public class MinimaxMove {

    int column;
    int score;

    public MinimaxMove(Integer column, Integer score){
        this.column = column;
        this.score = score;
    }

    public int getColumn() {
        return column;
    }

    public int getScore() {
        return score;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
