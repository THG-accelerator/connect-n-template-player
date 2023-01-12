package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

public class State {
    private Counter rootCounter;
    private int nodeWins = 0;
    private int nodeVisits = 0;
    private int possibleMoves;
    private Board board;

    public State(Counter counter){
        this.rootCounter = counter;
    }
//    public State(Counter counter, Board board){
//        this.rootCounter = counter;
//        this.setBoard(board);
//
//    }

    public void setRootCounter(Counter rootCounter) {
        this.rootCounter = rootCounter;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getNodeWins() {
        return this.nodeWins;
    }

    public int getNodeVisits() {
        return this.nodeVisits;
    }

    public void setNodeWins(int wins) {
        this.nodeWins = wins;
    }

    public void addVisit() {
        this.nodeVisits += 1;
    }

    public void addWin(int addBy) {
        this.nodeWins += addBy;
    }

    public void addWin() { this.nodeWins += 1; }

    public void subWin(int subBy) { this.nodeWins -= subBy; }

    public void subWin() { this.nodeWins -= 1; }

    public Counter getCounter() {
        return rootCounter;
    }
    public Counter getCounterOpposite() {
        return rootCounter.getOther();
    }
    public void invertCounter(){
        rootCounter = getCounterOpposite();
    }

    public Board getBoard() {
        return board;
    }

}
