package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;

public class Score {
    public static int ScoreCalculator(Board board){
        if(win==true){
            int score = 1;
        } else if (lose==true) {
            int score = -1;

        } else {
            int score = 0;
        }
        return score;

    }

}
