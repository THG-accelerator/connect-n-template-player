package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;


public class ConnectFourTheWin extends Player {
    Counter myCounter;
    MiniMaxAI miniMaxAI;

    public ConnectFourTheWin(Counter myCounter) {
        super(myCounter, ConnectFourTheWin.class.getName());
        this.myCounter = myCounter;
        miniMaxAI = new MiniMaxAI(5, myCounter);
    }

    @Override
    public int makeMove(Board board) {
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        try {
            return miniMaxAI.getMove();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }


}

