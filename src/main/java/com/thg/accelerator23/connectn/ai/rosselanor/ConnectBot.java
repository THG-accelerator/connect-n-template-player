package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;


public class ConnectBot extends Player {
    Counter myCounter;

    BoardAnalyser boardAnalyser;
    MiniMaxAI miniMaxAI;


    public ConnectBot(Counter myCounter) {
        super(myCounter, ConnectBot.class.getName());
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