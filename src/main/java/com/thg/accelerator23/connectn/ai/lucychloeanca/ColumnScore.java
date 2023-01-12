package com.thg.accelerator23.connectn.ai.lucychloeanca;

public class ColumnScore {
    int Column;
    int Score;

    public ColumnScore(Integer column, int score) {
        Column = column;
        Score = score;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int column) {
        Column = column;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "ColumnScore{" +
                "Column=" + Column +
                ", Score=" + Score +
                '}';
    }
}
