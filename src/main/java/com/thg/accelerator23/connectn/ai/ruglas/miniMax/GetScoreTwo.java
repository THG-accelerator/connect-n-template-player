package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.ArrayList;
import java.util.List;

public class GetScoreTwo {
    static GameConfig config = new GameConfig(10,8,4);
    static BoardAnalyser boardAnalyser = new BoardAnalyser(config);


    public static int getTotalScore(Board board, Position positionToCheck, Counter counter) throws InvalidMoveException {
        GameState gameState = boardAnalyser.calculateGameState(board);
        int totalScore = 0;

        totalScore  = totalScore + getScoreFromCenter(positionToCheck, counter);
        totalScore = totalScore + getScoreFromTwoOrThreeInALine(board, positionToCheck, counter);

        return totalScore;
    }

    private static int getScoreFromCenter(Position positionToCheck, Counter counter) {

        int centralScore = 0;

        if (positionToCheck.getX() == 4 || positionToCheck.getX() == 5){
            centralScore += Scores.centralScore;
            System.out.println("Central column bonus points + " + Scores.centralScore);
        }
        else if (positionToCheck.getX() == 3 || positionToCheck.getX() == 6){
            centralScore += Scores.nearCentralScore;
            System.out.println("near-central column bonus points + " + Scores.nearCentralScore);
        }
        return centralScore;
    }

    static int getScoreFromTwoOrThreeInALine(Board board, Position positionToCheck, Counter counter) {
        int lineScore = 0;

        ArrayList<ArrayList<Position>> positionsArray = getAdjacentNPositions(board, positionToCheck, 4);

        for (ArrayList<Position> positions : positionsArray) {

            for (int positionIndex = 0; positionIndex < positions.size() - 4; positionIndex++) {

                List<Counter> counterList = new ArrayList<>();

                if (board.getCounterAtPosition(positions.get(positionIndex)) != counter.getOther()) {
                    for (int counterIndex = 0; counterIndex < 4; counterIndex++) {

                        counterList.add(board.getCounterAtPosition(positions.get(positionIndex + counterIndex)));
                        lineScore = lineScore + getScoreFromLineOfFour(counterList, counter);
                    }
                }
            }
        }
        return lineScore;
    }

    private static int getScoreFromLineOfFour(List<Counter> counterList, Counter playerCounter) {

        if(counterList.stream().filter(counter -> counter == playerCounter.getOther()).count() > 0) {return 0;}

        else{
            long counterCount = counterList.stream().filter(counter -> counter== playerCounter).count();

            if(counterCount == 2) {
                System.out.println("Two in a row bonus points + 20 ");
                return Scores.lineOfTwoScore;}
            else if(counterCount == 3) {
                System.out.println("Three in a row bonus points + 40");
                return Scores.lineOfThreeScore;}

            else {
                System.out.println("bonus points for no surrounding enemy counters + 5");
                return Scores.noSurroundingCountersScore;
            }
        }
    }

    private static ArrayList<ArrayList<Position>> getAdjacentNPositions(Board board, Position position, int n) {
        ArrayList<ArrayList<Position>> positions = new ArrayList<>();

        ArrayList<Position> horizontal = new ArrayList<>();
        ArrayList<Position> vertical = new ArrayList<>();
        ArrayList<Position> upwardsDiagonal = new ArrayList<>();
        ArrayList<Position> downwardsDiagonal = new ArrayList<>();

        int x = position.getX();
        int y = position.getY();

        for ( int i=1-n; i < n ; i++) {

            Position horizontalPosition = new Position(x+i, y);
            Position verticalPosition = new Position(x,y+i);
            Position upwardsDiagonalPosition = new Position(x+i,y+i);
            Position downwardsDiagonalPosition = new Position(x+i,y-i);

            if (board.isWithinBoard(horizontalPosition)) {
                horizontal.add(horizontalPosition);
            }
            if (board.isWithinBoard(verticalPosition)) {
                vertical.add(verticalPosition);
            }
            if (board.isWithinBoard(upwardsDiagonalPosition)) {
                upwardsDiagonal.add(upwardsDiagonalPosition);
            }

            if (board.isWithinBoard(downwardsDiagonalPosition)) {
                downwardsDiagonal.add(downwardsDiagonalPosition);
            }

        }
        positions.add(horizontal);
        positions.add(vertical);
        positions.add(upwardsDiagonal);
        positions.add(downwardsDiagonal);

        return positions;
    }



}
