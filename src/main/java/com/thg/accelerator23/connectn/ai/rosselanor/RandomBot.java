package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;

import java.util.concurrent.ThreadLocalRandom;


public class RandomBot extends Player {
    Counter myCounter;

    BoardAnalyser boardAnalyser;
    MiniMaxAI miniMaxAI;


    public RandomBot(Counter myCounter) {
        super(myCounter, RandomBot.class.getName());
        this.myCounter = myCounter;
        miniMaxAI = new MiniMaxAI(5, myCounter);
    }


    @Override
    public int makeMove(Board board) {

        BoardAnalyser boardanalyser = new BoardAnalyser(board.getConfig());
        int position = ThreadLocalRandom.current().nextInt(0, 10);

        if (boardanalyser.isColumnFull(board, position)){
            return position + 1;
        }
        else {
        return position;}
    }


}

