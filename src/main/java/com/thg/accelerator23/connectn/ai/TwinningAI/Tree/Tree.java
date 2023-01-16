package com.thg.accelerator23.connectn.ai.TwinningAI.Tree;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

public class Tree {
    Node root;
    public Tree(Board board, Counter player){
        root = new Node(board, player);
    }

    public Tree(Node root){
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

}
