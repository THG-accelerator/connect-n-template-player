package com.thg.accelerator23.connectn.ai.funconcerto.movetree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;

public class MoveTree {
    private Counter counter;
    private final int depth;
    private final Board state;
    private int mostRecentPosition;
    private final LinkedList<MoveTree> moveList = new LinkedList<MoveTree>();

    public MoveTree() {
        this.counter = Counter.X;
        this.depth = 1;
        GameConfig config = new GameConfig(10, 8, 4);
        this.state = new Board(config);
    }

    public MoveTree(Counter counter, int depth, Board state, int position){
        this.counter = counter;
        this.depth = depth;
        this.state = state;
        this.mostRecentPosition = position;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }


    public Counter getCounter(){
        return this.counter;
    }
    public int getDepth(){
        return this.depth;
    }

    public Board getState(){
        return this.state;
    }

    public int getPosition(){
        return this.mostRecentPosition;
    }

    public void addNode(int position) throws InvalidMoveException {
        Counter newCounter = counter.getOther();
        int newDepth = depth + 1;
        Board newState = new Board(state, position, newCounter);
        MoveTree newNode = new MoveTree(counter.getOther(), depth + 1, newState, position);
        moveList.add(newNode);
    }
    public void removeNode(List<Integer> moveListToGetToNode){
        System.out.println("sjhviw");
    }
    public List<MoveTree> getLeaves(){
        List<MoveTree> leafList = new ArrayList<>();
        return leafList;
    }
    public LinkedList<MoveTree> getChildren(){

        if(moveList != null)return moveList;
        else{
            return new LinkedList<>();
        }
    }

    public MoveTree getChildAtPosition(int index){
        return moveList.get(index);
    }

    public int getTotalNodesBelow(){
        int count = 1;
        for(int i = 0; i < moveList.size(); i++){
            count += moveList.get(i).getTotalNodesBelow();
        }
        return count;
    }

}
