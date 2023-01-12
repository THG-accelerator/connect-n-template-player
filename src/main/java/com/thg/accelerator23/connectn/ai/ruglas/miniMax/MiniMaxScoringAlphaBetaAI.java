package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

public class MiniMaxScoringAlphaBetaAI extends Player {
    public MiniMaxScoringAlphaBetaAI(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        int column;
        MiniMaxScoringAlphaBeta miniMaxScoringAlphaBeta = new MiniMaxScoringAlphaBeta(this.getCounter());
        try {
            miniMaxScoringAlphaBeta.miniMaxMoveAlphaBeta(board, true, 6, 0, -1000000, 1000000);
        } catch (InvalidMoveException e) {
        }
        System.out.println("MiniMaxAlphaBeta");
        return miniMaxScoringAlphaBeta.getBestColumn();
    }

}

