package com.thg.accelerator23.connectn.ai.ajtracey.minimax;

public class Tree {
    private Node root;
    private int numberOfRoundsSimulated;

    public Node getRoot() {
        return root;
    }

    public int getNumberOfRoundsSimulated() {
        return numberOfRoundsSimulated;
    }

    public void setNumberOfRoundsSimulated(int numberOfRoundsSimulated) {
        this.numberOfRoundsSimulated = numberOfRoundsSimulated;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "Tree: " + root.getChildren();
    }
}

