package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.fermion.board.LocalBoardAnalyser;

import java.util.*;

public class Node {

    private State state;
    Node parent;
    private int move;
    private final List<Node> children = new ArrayList<>();

    public Node(Counter counter){
        this.state = new State(counter);
    }
public Node(){
        this.state= new State(Counter.O);
        this.state.setBoard(new Board(new GameConfig(10,8,4)));
        this.state.setBoard(new Board(new GameConfig(10,8,4)));
}
    public Node(Node tempNode){
        this.state = tempNode.getState();
        this.parent = tempNode.getParent();
        this.move = tempNode.getMove();
    }

    public Node(Node parent, int move,Counter counter){
        this.state = new State(counter);
        this.state.setBoard(parent.state.getBoard());
        this.parent = parent;
        this.move = move;
    }

    public State getState() {
        return state;
    }

    public int getMove() {
        return move;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() { return this.parent; };

    public Node childMostVisits() {
        Node mostNode = new Node();
        //Node mostNode = this.getChildren().get(0);
        for(Node node : this.getChildren()) {
            if(node.getState().getNodeVisits() > mostNode.getState().getNodeVisits()) {
                mostNode = node;
            }
        }
        return mostNode;
    }
//
//    public Node childMostVisits() {
//        return Collections.max(this.getChildren(), Comparator.comparing(c-> c.getState().getNodeVisits()));
//    }

    public Node averageChildWins() {
        return Collections.max(this.getChildren(), Comparator.comparing(c-> c.getState().getNodeWins()/c.getState().getNodeVisits()));
    }

//    public void addChild(List<Node> children){
//     for (Node child : children) {
//            children.add(child.getMove(),child);
//        }
//    }
//    public void addChild(Node child){
//        children.add(child.getMove(),child);
//    }

    public void addChild(Node node){
        Node interNode = node;
        children.add(interNode);
    }


    public void generateChildrenNodes(){
        for (int i = 0; i < 10; i++) {
            addChild(new Node(this, i, this.getState().getCounterOpposite()));
        }
    }


    public double getUTCValue(Node rootNode) {
        if(this.state.getNodeVisits()==0) {
            return Integer.MAX_VALUE;
        } else {
            return ((double) this.state.getNodeWins()/(double) this.state.getNodeVisits())+1.41*Math.sqrt(Math.log(rootNode.state.getNodeVisits())/(double) this.state.getNodeVisits());
        }
    }

    public int playRandomMove(Board board) {

        LocalBoardAnalyser lba = new LocalBoardAnalyser(board);
        boolean fullColumns[] = lba.fullColumns();
        Random r = new Random();
        int random = -1;
        // infinite loop if no available moves
        // might be a bad idea using while true
        ArrayList<Integer> possible = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        boolean hasFound = false;
        while(!hasFound) {
//            System.out.println("In play move");
            random = (r.nextInt(10));
//            if(possible.contains(random)){
//                possible.remove(random);
//            }
//            if (possible.size() ==0){
//                System.out.println("Explored all ");
//                return random;
//            }
//            System.out.println("____________________");
//            for (int i = 0; i < fullColumns.length; i++) {
//
//                System.out.println("Move: "+i+" "+fullColumns[i]);
//            }
//            System.out.println("Random "+ random);
//            System.out.println("____________________");

            if(fullColumns[random] == false)
                hasFound = true;

        }
        return random;
    }

    public void playSequencedMove(){ //Used to create the 10 children nodes

    }
}
