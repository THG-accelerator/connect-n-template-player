package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {
    private ArrayList<Integer> moveCounter = new ArrayList<>();
    private ArrayList<Integer> winCounter = new ArrayList<>();
    Board board;

    public MCTS(Board board) {
        this.board = board;
    }

//    public Node selectHighestUTCNode(Node parentNode) {
//        Node bestNode = new Node();
//        if(parentNode.getChildren().size() != 0) {
//            for(Node node : parentNode.getChildren()) {
//                if(Integer.MIN_VALUE < node.getUTCValue(parentNode))
//                   bestNode = node;
//            }
//        }
//        return bestNode;
//    }
//
    public Node selectHighestUTCNode(Node parentNode) {
        Node node = parentNode;
        while (node.getChildren().size() != 0) {
            node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUTCValue(parentNode)));
        }
        return node;
    }


    public int actualPlay(Counter counter) {
        Node rootNode = new Node(counter);
        Tree tree = new Tree(rootNode);

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);

        BoardAnalyser analyser = new BoardAnalyser(rootNode.getState().getBoard().getConfig());

        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {
            Node promisingNode = selectHighestUTCNode(rootNode);
            
            if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd()) {
                promisingNode.generateChildrenNodes();
            }

            Node nodeToExplore = randomChildNode(promisingNode);

            Counter results = simulateMoves(nodeToExplore,rootNode.getState().getCounter());

            backpropagation(nodeToExplore,results);

        }
        System.out.println("_________________TOP___Total moves "+ moveCounter.size()+" Wins "+winCounter.size());
        System.out.println("0: Total-"+Collections.frequency(moveCounter,0)+" Wins-"+Collections.frequency(winCounter,0));
        System.out.println("1: Total-"+Collections.frequency(moveCounter,1)+" Wins-"+Collections.frequency(winCounter,1));
        System.out.println("2: Total-"+Collections.frequency(moveCounter,2)+" Wins-"+Collections.frequency(winCounter,2));
        System.out.println("3: Total-"+Collections.frequency(moveCounter,3)+" Wins-"+Collections.frequency(winCounter,3));
        System.out.println("4: Total-"+Collections.frequency(moveCounter,4)+" Wins-"+Collections.frequency(winCounter,4));
        System.out.println("5: Total-"+Collections.frequency(moveCounter,5)+" Wins-"+Collections.frequency(winCounter,5));
        System.out.println("6: Total-"+Collections.frequency(moveCounter,6)+" Wins-"+Collections.frequency(winCounter,6));
        System.out.println("7: Total-"+Collections.frequency(moveCounter,7)+" Wins-"+Collections.frequency(winCounter,7));
        System.out.println("8: Total-"+Collections.frequency(moveCounter,8)+" Wins-"+Collections.frequency(winCounter,8));
        System.out.println("9: Total-"+Collections.frequency(moveCounter,9)+" Wins-"+Collections.frequency(winCounter,9));



        System.out.println("_________________MIDDDLE");

        for (Node child : rootNode.getChildren()) {
            System.out.println(child.getMove() +" "+ child.getState().getNodeVisits() +" "+ child.getState().getNodeWins());
        }

        Node winnerNode = rootNode.childMostVisits();

        tree.setRoot(winnerNode);
        return winnerNode.getMove();
    }


    private Counter simulateMoves(Node nodeToExplore, Counter rootCounter) {
        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
        GameState gameState = boardChecker.calculateGameState(nodeToExplore.getState().getBoard());

        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState();


        while(gameState.isEnd() == false){
            try {
                interState.invertCounter();

                int move = interNode.playRandomMove(interState.getBoard());

                interState.setBoard(new Board(interState.getBoard(), move, interState.getCounter()));

                gameState = boardChecker.calculateGameState(interState.getBoard());

            } catch(InvalidMoveException e) {
                System.out.println("Sim fail");
                throw new RuntimeException(e);
            }
        }
        moveCounter.add(nodeToExplore.getMove());
        if(gameState.getWinner().getStringRepresentation().equals(rootCounter.getStringRepresentation()) ){
            winCounter.add(nodeToExplore.getMove());
        }else{
        }



        return gameState.getWinner();

    }


    public Node randomChildNode(Node rootNode) {
        if(rootNode.getChildren().size() != 0) {
            Random r = new Random();
            int nextNode = r.nextInt(rootNode.getChildren().size());
            Node n = rootNode.getChildren().get(nextNode);
            n.getState().addVisit();
            return n;
        }
        return rootNode;
    }

    public void backpropagation(Node endNode, Counter resultCounter) {
        Node upNode = endNode;
        while (upNode != null) {
            upNode.getState().addVisit();
            if(upNode.getState().getCounter() == resultCounter){
                upNode.getState().addWin();
            }else {

//                upNode.getState().subWin();
            }
            upNode = upNode.getParent();

        }
    }
}


