package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.ArrayList;
import java.util.List;

public class GetScore {
    Board board;
    BoardAnalyser boardAnalyser;
    int totalScore;
    int parity;

    public GetScore(Board board, Counter counter) {
        this.board = board;
        this.boardAnalyser = new BoardAnalyser(this.board.getConfig());
        switch(counter) {
            case O -> this.parity = 0;
            case X -> this.parity = 1;
        }
    }

    public int getOpponentScore(Position positionToCheck, Counter counter) throws InvalidMoveException {
        return getScoreFromAdjPositions(positionToCheck, counter, true);
    }


    public int getTotalScore(Position positionToCheck, Counter counter) throws InvalidMoveException {
        GameState gameState = boardAnalyser.calculateGameState(this.board);
        totalScore = 0;
//        if(gameState.isDraw()){return 0;} else if (gameState.isWin()) { return 1000000;
//
//        }
//        else{
            totalScore += getScoreFromAdjPositions(positionToCheck, counter, false);

            if (positionToCheck.getX() == 4 || positionToCheck.getX() == 5){
                totalScore += 15;
                System.out.println("Central column bonus points + 15");
            }
            else if (positionToCheck.getX() == 3 || positionToCheck.getX() == 6){
                totalScore += 5;
                System.out.println("near-central column bonus points + 5 ");
            }
            return totalScore;
//        }
    }

    public ArrayList<ArrayList<Position>> getAdjacentNPositions(Position position, int n) {
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

    public int getScoreFromAdjPositions(Position positionToPlay, Counter counter, boolean isOpponent) throws InvalidMoveException {
        ArrayList<ArrayList<Position>> positionsArray = getAdjacentNPositions(positionToPlay, 4);
        int score = 0;
        for (ArrayList<Position> positions : positionsArray) {
            for (int positionIndex = 0; positionIndex<positions.size()-4; positionIndex++) {
                List<Counter> counterList = new ArrayList<>();
                if(this.board.getCounterAtPosition(positions.get(positionIndex)) == counter.getOther()){}
                else {
                    for (int counterIndex = 0; counterIndex < 4; counterIndex++) {
                        counterList.add(this.board.getCounterAtPosition(positions.get(positionIndex + counterIndex)));
                    }
                    if (!isOpponent){
                    score += findScore(counterList, counter);}
                    else {score += findScoreOpponent(counterList, counter);}
                }

            }
        }
        return score;
    }

    private int findScore(List<Counter> counterList, Counter playerCounter) {
        if(counterList.stream().filter(counter -> counter== playerCounter.getOther()).count() > 0) {return 0;}
        else{
            long counterCount = counterList.stream().filter(counter -> counter== playerCounter).count();

            if(counterCount == 2){
                System.out.println("Two in a row bonus points + 20 ");
                return 20;}
            else if(counterCount == 3) {
                System.out.println("Three in a row bonus points + 40");
                return 40;}
            else return 5;
        }}

        private int findScoreOpponent(List<Counter> counterList, Counter playerCounter) {
            if(counterList.stream().filter(counter -> counter== playerCounter.getOther()).count() > 0) {return 0;}
            else{
                long counterCount = counterList.stream().filter(counter -> counter== playerCounter).count();
                if(counterCount == 2){return 5;}
                else if(counterCount == 3) {return 50;}
                else return 0;
            }
        }
   public int getHeightOfWinPositionFromLine(List<Position> positionList) {
       System.out.println("getHeightOfWinPosition method call");

       int height = 100;

       for (Position position : positionList) {
           System.out.println(this.board.getCounterAtPosition(position));

           if (this.board.getCounterAtPosition(position) == null) {
               height = position.getY();
           }
       }
       return height;
   }

   public int getHeightScore(List<Position> positionList) {

       int score = 0;
       System.out.println("getHeightScore method call");

           int winPositionHeight = getHeightOfWinPositionFromLine(positionList);

           score = score + 40 - winPositionHeight * 5;

           if (winPositionHeight % 2 == this.parity) {
               score = score + 10;
               System.out.println("This Line of three is at a height matching the parity of the player :).");
           }
        return score;
   }


}
