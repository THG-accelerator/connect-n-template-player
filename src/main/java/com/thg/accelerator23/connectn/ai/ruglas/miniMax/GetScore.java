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
    public GetScore(Board board) {
        this.board = board;
        this.boardAnalyser = new BoardAnalyser(this.board.getConfig());
    }

    public int getTotalScore(Position positionToCheck, Board boardToCheck, Counter counter) throws InvalidMoveException {
        GameState gameState = boardAnalyser.calculateGameState(boardToCheck);
        totalScore = 0;
        System.out.println("Zero depth Total score");
        if(gameState.isDraw()){return 0;} else if (gameState.isWin()) { return 10000;

        }
        else{
            totalScore += getScoreFromAgjPositions(positionToCheck, boardToCheck, counter);
            if (positionToCheck.getX() == 4 || positionToCheck.getX() == 5){
                totalScore += 10;
            }
            else if (positionToCheck.getX() == 3 || positionToCheck.getX() == 6){
                totalScore += 5;
            }
            System.out.println("Total Score" + totalScore);
            return totalScore;
        }


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

    public int getScoreFromAgjPositions(Position positionToPlay, Board board, Counter counter) throws InvalidMoveException {
        ArrayList<ArrayList<Position>> positionsArray = getAdjacentNPositions(positionToPlay, 4);
        int score = 0;
        for (ArrayList<Position> positions : positionsArray) {
            for (int positionIndex = 0; positionIndex<positions.size()-4; positionIndex++) {
                List<Counter> counterList = new ArrayList<>();
                if(board.getCounterAtPosition(positions.get(positionIndex)) == counter.getOther()){}
                else {
                    for (int counterIndex = 0; counterIndex < 4; counterIndex++) {
                        counterList.add(board.getCounterAtPosition(positions.get(positionIndex + counterIndex)));
                    }
                    score += findScore(counterList, counter);
                }

            }
        }
        System.out.println("Score " + score);
        return score;
    }

    private int findScore(List<Counter> counterList, Counter playerCounter) {
        if(counterList.stream().filter(counter -> counter== playerCounter.getOther()).count() > 0) {return 0;}
        else{
            long counterCount = counterList.stream().filter(counter -> counter== playerCounter).count();
            if(counterCount == 2){return 10;}
            else if(counterCount == 3) {return 20;}
            else return 5;
        }

    }
}
