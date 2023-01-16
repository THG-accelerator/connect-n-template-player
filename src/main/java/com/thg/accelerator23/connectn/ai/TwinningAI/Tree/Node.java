package com.thg.accelerator23.connectn.ai.TwinningAI.Tree;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo.NodeState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {
    NodeState state;
    Node parent;
    List<Node> childList;

    public Node(Board board, Counter player) {
        this.state = new NodeState(board, player);
        this.childList = new ArrayList<>();
        this.parent = null;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void addChild(Node newNode) {
        this.childList.add(newNode);
    }

    public NodeState getState() {
        return state;
    }

    public Node getChildWithMaxWinCount() {
        return Collections.max(this.childList, Comparator.comparing(child -> child.getState().getWinCount()));
    }

}
