package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.Random;

public class NodeState {
    Board board;
    Counter player;
    int visitCount;
    int winCount;
    int winVisitCount = visitCount * winCount;
    Position position;

    public NodeState(Board board, Counter player) {
        this.board = board;
        this.player = player;
        this.visitCount = 0;
        this.winCount = 0;
        this.position = null;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
    public Counter getPlayer() {
        return player;
    }
    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getWinVisitCount() {
        return winVisitCount;
    }

    public void addWinCount(int winCount){
        this.winCount += winCount;
    }

    public void addVisitCount(){
        this.visitCount++;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Counter getOtherPlayer() {
        return this.player.getOther();
    }

    public ArrayList<Position> getPossibleMovesList(Board board){
        ArrayList<Position> move = new ArrayList<Position>();
        for (int col = 0; col < board.getConfig().getWidth(); col++){
            for (int row = 0; row < board.getConfig().getHeight(); row++){
                if (!board.hasCounterAtPosition(new Position(col,row))){
                    move.add(new Position(col,row));
                    break;
                }
            }
        }
        return move;
    }

    public Position getPossibleMove(Board board) {
        Random rand = new Random();
        Position randomMove = getPossibleMovesList(board).get(rand.nextInt(getPossibleMovesList(board).size()));
        return randomMove;
    }


}
