package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

import java.util.Arrays;

public class State implements Cloneable{
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


    public State(Counter rootCounter, int nodeWins, int nodeVisits, Board board) {
        this.rootCounter = rootCounter;
        this.nodeWins = nodeWins;
        this.nodeVisits = nodeVisits;
        this.board = board;
    }


    @Override
    public State clone(){
        State s = null;

        try{
            s = (State)super.clone();
        }catch (CloneNotSupportedException e){
            s = new State(this.getCounter(),this.getNodeWins(),this.getNodeVisits(),this.getBoard());
        }
        return s;

    }



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

    public void addWin() { this.nodeWins += 1; }

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
