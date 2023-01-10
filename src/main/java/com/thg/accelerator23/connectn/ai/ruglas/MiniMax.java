package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;

public class MiniMax {
    private int score = 0;
    public MiniMax(Board board) {

    }

    public void clearScore() {
        this.score=0;
    }
    public void addPoints(int points) {
        score = score + points;
    }

}
