package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

public class MiniMaxWinLoseAI extends Player {
    public MiniMaxWinLoseAI(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        MiniMaxWinLose miniMaxWinLose = new MiniMaxWinLose(this.getCounter());
        try {
            miniMaxWinLose.miniMaxWinLoseMove(board, true, 4);
            return miniMaxWinLose.getBestColumn();
        } catch (InvalidMoveException e) {
        }
        System.out.println("No position found");
        return 0;
    }

}

