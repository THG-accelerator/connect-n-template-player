package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;


public class ConnectFourTheWin extends Player {
    Counter myCounter;
    MiniMaxAI2 miniMaxAI2;

    public ConnectFourTheWin(Counter myCounter) {
        super(myCounter, ConnectFourTheWin.class.getName());
        this.myCounter = myCounter;
        miniMaxAI2 = new MiniMaxAI2(1, myCounter);
    }

    @Override
    public int makeMove(Board board) {
        miniMaxAI2.setBoard(board);
        miniMaxAI2.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        try {
            return miniMaxAI2.getMove();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }


}

