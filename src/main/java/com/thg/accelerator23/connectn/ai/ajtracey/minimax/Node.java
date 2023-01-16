package com.thg.accelerator23.connectn.ai.ajtracey.minimax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int roundsAhead;
    private int totalRounds;
    private int move;
    private boolean isMaxPlayer;
    private Counter counter;
    private int score;
    private Board board;
    private List<Node> children;

    private Node parent;

    public Node(int roundsAhead, int totalRounds, boolean isMaxPlayer, Board board, Counter counter){
        this.roundsAhead = roundsAhead;
        this.isMaxPlayer = isMaxPlayer;
        this.board = board;
        this.counter = counter;
        this.totalRounds = totalRounds;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public Board getBoard() {
        return board;
    }

    public int getRoundsAhead() {
        return roundsAhead;
    }

    public int getScore() {
        return score;
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean getIsMaxPlayer(){
        return isMaxPlayer;
    }

    public int getMove(){
        return move;
    }

    public Counter getCounter() {
        return counter;
    }

    public Node getParent() {
        return parent;
    }


    public void addChild(Node child) {
        List<Node> listOfChildren;
        if (this.children == null){
            listOfChildren = new ArrayList<>();
        }else{
            listOfChildren = children;
        }
        listOfChildren.add(child);
        this.children = listOfChildren;

    }

    public void setMaxPlayer(boolean maxPlayer) {
        isMaxPlayer = maxPlayer;
    }

    public void setRoundsAhead(int move) {
        this.roundsAhead = move;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }


    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isMaxPlayer() {
        return isMaxPlayer;
    }

    public void initialScore(){
        if(isMaxPlayer){
            setScore(Integer.MIN_VALUE);
        }
        setScore(Integer.MAX_VALUE);
    }
}
