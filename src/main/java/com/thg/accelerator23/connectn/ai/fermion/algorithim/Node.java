package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;
import com.thg.accelerator23.connectn.ai.fermion.board.localBoardAnalyser;

import java.util.*;

public class Node {

    private State state;
    Node parent;
    private int move;
    private List<Node> children = new ArrayList<>();

    public Node(Counter counter){
        this.state = new State(counter);
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
        return Collections.max(this.getChildren(), Comparator.comparing(c-> c.getState().getNodeVists()));
    }

//    public void addChild(List<Node> children){
//     for (Node child : children) {
//            children.add(child.getMove(),child);
//        }
//    }
//    public void addChild(Node child){
//        children.add(child.getMove(),child);
//    }

    public void addChild(){
        Node interNode =new Node(this,playRandomMove(this.getState().getBoard()),this.getState().getCounterOpposite());
        children.add(interNode);
    }


//    public void generateChildrenNodes(){
//        for (int i = 0; i < 10; i++) {
//            //create node then play random move then add to the list
//            addChild(new Node(this, i));
//        }
//    }


    public double getUTCValue() {
        if(state.getNodeVists()==0) {
            return Integer.MAX_VALUE;
        } else {
            return (state.getNodeWins()/state.getNodeVists())+1.41*Math.sqrt(Math.log(state.getNodeWins())/state.getNodeVists());
        }
    }

    public int playRandomMove(Board board) {

        localBoardAnalyser lba = new localBoardAnalyser(board);
        boolean freeColumns[] = lba.freeColumns();
        Random r = new Random();
        // infinite loop if no available moves
        // might be a bad idea using while true
        while(true) {
            int random = (r.nextInt(10)+1);
            if(freeColumns[random] == true) {
                return random;
            }
        }
    }

    public void playSequencedMove(){ //Used to create the 10 children nodes

    }


//    public void simulate(Board board){
//        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
//        GameState gameState = boardChecker.calculateGameState(board);        //plays a random move, opponent plays random move.
//        for (Node child : children) {
//            while (!gameState.isEnd()) {
//                try {
//                    child.state.addVist();
//                    board = new Board(board, child.playRandomMove(), child.getRootCounter());
//                    boardChecker.calculateGameState(board);
//
//                    board = new Board(board, child.playRandomMove(), child.getRootCounter().getOther());
//                } catch (InvalidMoveException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (gameState.isWin()){
//
//            }
//        }
//
//    }



//    public Counter getRootCounter() {
//        return rootCounter;
//    }

}
