package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {

    Board board;
    Counter rootCounter;

    public MCTS(Board board) {
        this.board = board;
    }
    public Node selectHighestUTCNode(Node parentNode) {

        Node node = parentNode;
        while (node.getChildren().size() != 0) {
            node = Collections.max(parentNode.getChildren(), Comparator.comparing(Node::getUTCValue));
        }
        return node;
    }

    public void expandNode(Node node) {
        node.addChild();
    }

    public int actualPlay(Counter counter) {
//        this.board = board;
        Node rootNode = new Node(counter);
        Tree tree = new Tree(rootNode);

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);
        System.out.println(rootNode.getState().getBoard()+"Roort");
        System.out.println(System.currentTimeMillis());
        long currentTime = System.currentTimeMillis();



        while (System.currentTimeMillis()-currentTime < 8500) {
            Node promisingNode = selectHighestUTCNode(rootNode);
            System.out.println(promisingNode.getState().getBoard()+"proom]");

            promisingNode.addChild();

            Node nodeToExplore = randomChildNode(promisingNode);

            int results = simulateMoves(nodeToExplore);
            System.out.println("Results"+results);
            backpropagation(nodeToExplore,results);

        }
        System.out.println("After the while loop");
        Node winnerNode = rootNode.childMostVisits();
        tree.setRoot(winnerNode);
        return winnerNode.getMove();
    }

    private int simulateMoves(Node nodeToExplore) {
        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
        GameState gameState = boardChecker.calculateGameState(this.board);
        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState();
        System.out.println("Simulating games");
        while(!gameState.isEnd()){
            System.out.println("Has the game ended:"+gameState.isEnd());
            try {
                interState.setBoard(new Board(new Board(new GameConfig(10,8,4)), interNode.playRandomMove(interState.getBoard()), interState.getCounter()));
                boardChecker.calculateGameState(interState.getBoard());
                interState.invertCounter();

            }catch(InvalidMoveException e){
                throw new RuntimeException(e);
            }
        }
        System.out.println("Out of the while loop");
        if(gameState.getWinner() == nodeToExplore.getState().getCounter()){
            return 1;
        } else if (gameState.getWinner() == nodeToExplore.getState().getCounterOpposite()) {
            return -1;
        } else {
            return 0;
        }
    }


    public Node randomChildNode(Node rootNode) {

        if(rootNode.getChildren().size() != 0) {
            Random r = new Random();
            int nextNode = r.nextInt(rootNode.getChildren().size());
            Node n = rootNode.getChildren().get(nextNode);
            n.getState().addVisit();
            return n;
        }
        return null;
    }

    public void backpropagation(Node endNode, int counter) {
        Node upNode = endNode;

        while (upNode != null) {
            upNode.getState().addVisit();
            upNode.getState().addWin(counter);
            upNode = upNode.getParent();
            }
        }
}


//
//    public int play(){
//        root = firstNode;
//        root.generateChildrenNodes();
//
//        for (int i = 0; i < 10; i++) {
//
//            root.simulate();
//            root.chooseHighValueNode();
//            root.simulation();
//            root.backpropagate();
//        }
//        root.chooseHighValueNode();
//        return root.getMove();
//        root.playRandomMove();
//        roo
//
//    }
//
//
//
//
//
//}

