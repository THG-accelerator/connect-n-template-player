package com.thg.accelerator23.connectn.ai.fermion;

import java.util.List;

public class Node {
    Node parent;
    int move;
    List<Node> children;

    int nodeWins = 0;
    int nodeVists = 0;

    public Node(Node parent, int move){
        this.parent = parent;
        this.move = move;
    }
    public int getMove() {
        return move;
    }

    public void addChild(List<Node> children1){
     for (Node child : children1) {
            children.add(child.getMove(),child);
        }
    }

    public double getValue() {
        if(nodeVists==0){
            return Integer.MAX_VALUE;
        }else{
            return nodeWins/nodeVists+1.41*Math.sqrt(Math.log(nodeVists)/nodeVists);
        }
    }

    public void playRandomMove(){ // exploration

    }
    public Node chooseHighValueNode(){//exploitation


    }

    public Node exploreNode(){

    }
    public Node backpropagate(){

    }




}
