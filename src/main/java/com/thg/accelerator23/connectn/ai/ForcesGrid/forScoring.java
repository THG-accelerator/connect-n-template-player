package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.pow;

public class forScoring {
    /*
    public methods:
    - scorePosition(Board, Position, Counter) -> int
        returns a 'score' of a potential position on the board

    private methods:
    - getRunsInLine(ArrayList<Line>) -> HashMap<Counter, Integer>
        same function as getBestRunByColour in BoardAnalyser.java
    - calculateScore(List<Hashmap<Counter,Integer>>, Counter) -> int
        weighing of different scenarios
    */

    public static int scorePosition(Board board, Position pos, Counter counter) {
        List<HashMap<Counter, Integer>> scores = new ArrayList<>();

        Counter[][] grid = board.getCounterPlacements();
        ArrayList<Counter> cline = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Position newPos = new Position(pos.getX(), pos.getY() - i);
            if (!board.isWithinBoard(newPos)) {
                continue;
            }
            cline.add(grid[newPos.getX()][newPos.getY()]);
        }

        ArrayList<Counter> hline = new ArrayList<>();
        for (int i = -3; i < 4; i++) {
            Position newPos = new Position(pos.getX() + i, pos.getY());
            if (!board.isWithinBoard(newPos)) {
                continue;
            }
            if (i != 0) {
                hline.add(grid[newPos.getX()][newPos.getY()]);
            }
        }
        ArrayList<Counter> diagLline = new ArrayList<>();
        for (int i = -3; i < 4; i++) {
            Position newPos = new Position(pos.getX() + i, pos.getY() + i);
            if (!board.isWithinBoard(newPos)) {
                continue;
            }
            if (i != 0) {
                diagLline.add(grid[newPos.getX()][newPos.getY()]);
            }
        }
        ArrayList<Counter> diagRline = new ArrayList<>();
        for (int i = -3; i < 4; i++) {
            Position newPos = new Position(pos.getX() + i, pos.getY() - i);
            if (!board.isWithinBoard(newPos)) {
                continue;
            }
            if (i != 0) {
                diagRline.add(grid[newPos.getX()][newPos.getY()]);
            }
        }
        if (!cline.isEmpty()) {
            scores.add(getRunsInLine(cline));
        }
        scores.add(getRunsInLine(hline));
        scores.add(getRunsInLine(diagLline));
        scores.add(getRunsInLine(diagRline));
        return calculateScore(scores, counter);
    }

    private static HashMap<Counter, Integer> getRunsInLine(ArrayList<Counter> line) {
        Iterator<Counter> iterator = line.iterator();
        HashMap<Counter, Integer> inARow = new HashMap<>();
        for (Counter c : Counter.values()) {
            inARow.put(c, 0);
        }
        Counter current = null;
        int currentRunLength = 0;
        while (iterator.hasNext()) {
            Counter next = iterator.next();
            if (current != next) {
                if (current != null) {
                    if (Math.max(currentRunLength, 1) > inARow.get(current)) {
                        inARow.put(current, Math.max(currentRunLength, 1));
                    }
                }
                currentRunLength = 1;
                current = next;
            } else {
                currentRunLength++;
            }
        }
        if (current != null && Math.max(currentRunLength, 1) > inARow.get(current)) {
            inARow.put(current, Math.max(currentRunLength, 1));
        }
        return inARow;
    }

    private static int calculateScore(List<HashMap<Counter, Integer>> runs, Counter counter) {
        int score = 0;
        int forcesG = 0;
        int opp = 0;
        for (HashMap<Counter, Integer> scores : runs) {
            if (scores.get(counter) == 3) {
                score += 200;   // 1st priority to place winning move
            } else if (scores.get(counter.getOther()) == 3) {
                score += 100;   // 2nd priority to block opponent winning move
            } else {
                forcesG += scores.get(counter);
                opp += scores.get(counter.getOther());
            }
        }
        score += (int) (pow(forcesG,3) - pow(opp,3));
        return  score;
    }
}
