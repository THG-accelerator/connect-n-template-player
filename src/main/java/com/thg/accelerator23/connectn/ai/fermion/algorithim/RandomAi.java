package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.fermion.board.LocalBoardAnalyser;

import java.util.Random;


public class RandomAi extends Player {

    int randomColumn;
    public RandomAi(Counter counter) {
        super(counter, RandomAi.class.getName());
    }

    @Override
    public int makeMove(Board board) {

        LocalBoardAnalyser moveChecker = new LocalBoardAnalyser(board);
        Random r = new Random();
        randomColumn = r.nextInt(10);
        while(moveChecker.checkForFullColumn(randomColumn)){
          randomColumn = r.nextInt(10);
        }

        return randomColumn;

        }
}

