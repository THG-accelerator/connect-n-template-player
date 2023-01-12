package com.thg.accelerator23.connectn.ai.ruglas.MCTS;

import com.thehutgroup.accelerator.connectn.player.Board;

import java.util.ArrayList;
import java.util.List;

public class Node {

    Board board;
    int score;
    List<Node> children = new ArrayList<>();
    Node parent;

    public Node(Board board) {
        this.board = board;
    }

    public Node getChildWithBestScore() {
        Node bestChild = children.get(0);
        for (int nodeIndex = 0; nodeIndex<children.size(); nodeIndex++){
            if (children.get(nodeIndex).getScore() > bestChild.getScore()) {
                bestChild = children.get(nodeIndex);
            }

        }
        return bestChild;
    }

    public void addChild(Node node){
        children.add(node);
    }

    private int getScore(){return this.score;}
}
