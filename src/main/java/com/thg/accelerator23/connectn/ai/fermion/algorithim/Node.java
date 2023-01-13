package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.LocalBoardAnalyser;

import java.util.*;

public class Node {

    private State state;
    private Node parent;
    private int move;
    private final List<Node> children = new ArrayList<>();

    public Node(Counter counter){
        this.state = new State(counter);
    }

    public Node(State state){
        this.state = state;
    }
//
//    public Node() {
//        this.state= new State(Counter.O);
//        this.state.setBoard(new Board(new GameConfig(10,8,4)));
//    }

    public Node(Node tempNode) {
        this.state = tempNode.getState();
        this.parent = tempNode.getParent();
        this.move = tempNode.getMove();
    }

    public Node(Node parent, int move, Counter counter) {
        this.state = new State(counter);
        Board b = parent.state.getBoard();
        this.state.setBoard(b);
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

    public boolean hasChildren() {
        if(this.children.size() == 0) {
            return false;
        }
        return true;
    }

    public Node getParent() { return this.parent; };

    public int getNumberOfParents() {
        Node node = this;
        int counter = 0;
        while(node.getParent() !=null){
            counter++;
            node = node.getParent();
        }
        return counter;
    }

    public int getNumberOfChildrenLevels(){
        Node node = this;
        int counter = 0;
        while(node.hasChildren()) {
            counter++;
            node = node.getChildren().get(2);
        }
        return counter;
    }

    public Node childMostVisits() {
        if (this.hasChildren()) {
            Node mostNode = this.getChildren().get(0);
            for (Node node : this.getChildren()) {
                if (node.getState().getNodeVisits() > mostNode.getState().getNodeVisits()) {
                    mostNode = node;
                }
            }
            return mostNode;
        }
        return this;
    }

    public Node averageChildWins() {
        return Collections.max(this.getChildren(), Comparator.comparing(c-> c.getState().getNodeWins()/c.getState().getNodeVisits()));
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void generateChildrenNodes() {
        for (int i = 0; i < 10; i++) {
            LocalBoardAnalyser lba = new LocalBoardAnalyser(this.state.getBoard());
            boolean fullColumns[] = lba.fullColumns();

            try {
                if(fullColumns[i] ==false) {
                    Board board = new Board(this.getState().getBoard(), i, this.getState().getCounter());
//                Node node = new Node(this.getState());
                    Node node = new Node(this, i, this.getState().getCounterOpposite());
                    node.getState().setBoard(board);
                    addChild(node);
                }
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public double getUCTValue(Node rootNode) {
        if(this.state.getNodeVisits()==0) {
//            System.out.println("node hasn't been visited");
            return Integer.MAX_VALUE;
        } else {
//            System.out.println (((double) this.state.getNodeWins()/(double) this.state.getNodeVisits())+1.41*Math.sqrt(Math.log(rootNode.state.getNodeVisits())/(double) this.state.getNodeVisits())+" "+this.getMove());
            return (((double) this.state.getNodeWins()/(double) this.state.getNodeVisits())+1.41*Math.sqrt(Math.log(rootNode.state.getNodeVisits())/(double) this.state.getNodeVisits()));
        }
    }

    public int playRandomMove(Board board) {

        LocalBoardAnalyser lba = new LocalBoardAnalyser(board);
        boolean fullColumns[] = lba.fullColumns();
        Random r = new Random();
        int random = -1;
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
