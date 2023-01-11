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

        ArrayList<Counter> centreCounters = new ArrayList<Counter>();
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

        ArrayList<Counter> row2Counters = new ArrayList<Counter>();
        row2Counters.add(board.getCounterAtPosition(new Position(0,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(1,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(2,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(3,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(4,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(5,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(6,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(7,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(8,1)));
        row2Counters.add(board.getCounterAtPosition(new Position(9,1)));

        int numberOfRow2CountersForPlayer = Collections.frequency(row2Counters, counter);
        int numberOfRow2CountersForOtherPlayer = Collections.frequency(row2Counters, counter.getOther());
        score+=numberOfRow2CountersForPlayer;
        score-=numberOfRow2CountersForOtherPlayer;

        return score;
    }

}
