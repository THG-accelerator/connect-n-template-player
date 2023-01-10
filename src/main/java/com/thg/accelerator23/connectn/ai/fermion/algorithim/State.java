package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

public class State {
    private Counter rootCounter;
    private int nodeWins = 0;
    private int nodeVists = 0;
    private int possibleMoves;
    private Board board;

    public State(Counter counter){
        rootCounter = counter;
    }

    public void setRootCounter(Counter rootCounter) {
        this.rootCounter = rootCounter;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getNodeWins() {
        return nodeWins;
    }

    public int getNodeVists() {
        return nodeVists;
    }

    public void addVisit() {
        this.nodeVists += 1;
    }

    public void addWin(int addBy) {
        this.nodeWins += addBy;
    }

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
