package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;

import java.util.ArrayList;
import java.util.Collections;

public class Score {
    public static int ScoreCalculator(Board board, Counter counter){
        int score = 0;

        ArrayList<Counter> centreCounters = new ArrayList<>();
        centreCounters.add(board.getCounterAtPosition(new Position(4,0)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,1)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,2)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,3)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,4)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,5)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,6)));
        centreCounters.add(board.getCounterAtPosition(new Position(4,7)));

        int numberOfCentreCountersForPlayer = Collections.frequency(centreCounters, counter);
        int numberOfCentreCountersForOtherPlayer = Collections.frequency(centreCounters, counter.getOther());
        score+=numberOfCentreCountersForPlayer*5;
        score-=numberOfCentreCountersForOtherPlayer*5;

        ArrayList<Counter> row1Counters = getRowOfBoard(board, 0);
        score = getScoreForXPlayer(counter, score, row1Counters);

        ArrayList<Counter> row2Counters = getRowOfBoard(board, 1);
        score = getScoreForOPlayer(counter, score, row2Counters);

        ArrayList<Counter> row3Counters = getRowOfBoard(board, 2);
        score = getScoreForXPlayer(counter, score, row3Counters);

        ArrayList<Counter> row4Counters = getRowOfBoard(board, 3);
        score = getScoreForOPlayer(counter, score, row4Counters);

        ArrayList<Counter> row5Counters = getRowOfBoard(board, 4);
        score = getScoreForXPlayer(counter, score, row5Counters);

        ArrayList<Counter> row6Counters = getRowOfBoard(board, 5);
        score = getScoreForOPlayer(counter, score, row6Counters);

        ArrayList<Counter> row7Counters = getRowOfBoard(board, 6);
        score = getScoreForXPlayer(counter, score, row7Counters);

        ArrayList<Counter> row8Counters = getRowOfBoard(board, 7);
        score = getScoreForOPlayer(counter, score, row8Counters);

        return score;
    }

    private static int getScoreForXPlayer(Counter counter, int score, ArrayList<Counter> row1Counters) {
        int numberOfRow1CountersForPlayer = Collections.frequency(row1Counters, counter);
        int numberOfRow1CountersForOtherPlayer = Collections.frequency(row1Counters, counter.getOther());
        if(counter == Counter.X) {
            score += numberOfRow1CountersForPlayer;
            score -= numberOfRow1CountersForOtherPlayer;
        }else{
            score -= numberOfRow1CountersForPlayer;
            score += numberOfRow1CountersForOtherPlayer;
        }
        return score;
    }

    private static int getScoreForOPlayer(Counter counter, int score, ArrayList<Counter> rowCounters) {
        int numberOfRowCountersForPlayer = Collections.frequency(rowCounters, counter);
        int numberOfRowCountersForOtherPlayer = Collections.frequency(rowCounters, counter.getOther());
        if(counter == Counter.O) {
            score += numberOfRowCountersForPlayer;
            score -= numberOfRowCountersForOtherPlayer;
        }else{
            score -= numberOfRowCountersForPlayer;
            score += numberOfRowCountersForOtherPlayer;
        }
        return score;
    }

    public static ArrayList<Counter> getRowOfBoard(Board board, int rowIndex){
        ArrayList<Counter> row6Counters = new ArrayList<Counter>();
        row6Counters.add(board.getCounterAtPosition(new Position(0,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(1,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(2,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(3,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(4,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(5,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(6,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(7,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(8,rowIndex)));
        row6Counters.add(board.getCounterAtPosition(new Position(9,rowIndex)));
        return row6Counters;
    }

}
