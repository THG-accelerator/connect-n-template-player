package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

public class MiniMaxScoringAI extends Player {
    public MiniMaxScoringAI(Counter counter) {
        super(counter, MiniMaxScoringAI.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        MiniMaxScoring miniMaxScoring = new MiniMaxScoring(this.getCounter());
        try {
            miniMaxScoring.miniMaxMove(board, true, 4, 0);
            return miniMaxScoring.getBestColumn();

        } catch (InvalidMoveException e) {
        }
        return 5;
    }

}

