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
//        Node bestNode = parentNode;
//        if(parentNode.getChildren().size() != 0) {
//            for(Node node : parentNode.getChildren()) {
//                if(Integer.MIN_VALUE < node.getUTCValue(parentNode))
//                   bestNode = node;
//            }
//        }
//        System.out.println("This is the best node "+bestNode.getUTCValue(parentNode));
//        return bestNode;
//    }
////

        public Node selectHighestUTCNode(Node parentNode) {
        if(parentNode.getChildren().size() !=0){

            Node bestNode = parentNode.getChildren().get(0);
            for (Node child : parentNode.getChildren()) {
                if (bestNode.getUTCValue(parentNode)< child.getUTCValue(parentNode)){
                    bestNode = child;
                }
            }
            System.out.println("This is the best node "+bestNode.getUTCValue(parentNode)+" move "+bestNode.getMove());
            return bestNode;
        }
            System.out.println("This is the best  parent node "+parentNode.getUTCValue(parentNode));
        return parentNode;


        }



//    public Node selectHighestUTCNode(Node parentNode) {
//        if(parentNode.getChildren().size() != 0){
//            Node node = Collections.max(parentNode.getChildren(), Comparator.comparing(c -> c.getUTCValue(parentNode)));
//            System.out.println("This is the best node "+node.getUTCValue(parentNode));
//            return node;
//        }
//        return parentNode;
//    }

//    public Node selectHighestUTCNode(Node parentNode) {
//        Node node = parentNode;
//        while (node.getChildren().size() != 0) {
//            node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUTCValue(parentNode)));
//        }
//        return node;
//    }





    public int actualPlay(Counter counter) {
//        Tree tree = new Tree(rootNode);
        Tree tree = new Tree(counter);
        Node rootNode = tree.getRoot();


        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);



        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {

            Node promisingNode = selectHighestUTCNode(rootNode);
            BoardAnalyser analyser = new BoardAnalyser(promisingNode.getState().getBoard().getConfig());
            if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd()) {
                promisingNode.generateChildrenNodes();
            }

            Node nodeToExplore = randomChildNode(promisingNode);

            Counter results = simulateMoves(nodeToExplore,rootNode.getState().getCounter());
//            Node winnerNode = simulateMoves(nodeToExplore,rootNode.getState().getCounter());

            backpropagation(nodeToExplore,results,counter);
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
        System.out.println(tree.getTrueRoot().getChildren().size());
        for (Node child : tree.getTrueRoot().getChildren()) {
            System.out.println(child.getMove() +" "+ child.getState().getNodeVisits() +" "+ child.getState().getNodeWins());
        }
        for (Node child : tree.getRoot().getChildren()) {
            System.out.println("1: "+child.getMove()+" size of child= "+child.getChildren().size()
            );
            if(child.getChildren().size()!=0) {
                for (Node childChild : child.getChildren()) {
//                    System.out.println("2: parent=>"+child.getMove()+" " +childChild.getMove());
                    if (childChild.getChildren().size() != 0) {
                                            System.out.println("2: parent= "+child.getMove()+" Size of child= "+childChild.getChildren().size());

                        for (Node childChildChild : childChild.getChildren()) {

//                            System.out.println("3: granparent=>"+child.getMove()+"parent=>"+childChild.getMove()+" " +childChildChild.getMove());
                        }
                    }
                }
            }
        }

        Node winnerNode = tree.getTrueRoot().childMostVisits();

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
//                interNode.getState().invertCounter();
//
//                int move = interNode.playRandomMove(interNode.getState().getBoard());
//
//                interNode.getState().setBoard(new Board(interState.getBoard(), move, interState.getCounter()));
//
//                interNode= new Node(interNode,move,interNode.getState().getCounter());
//
//                gameState = boardChecker.calculateGameState(interState.getBoard());

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
        }

//        return interNode;
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
    private int lastMove;
    private int backtracked;
    public void backpropagation(Node endNode, Counter simWinnerNode,Counter rootCounter) {
        Node upNode = endNode;
//        System.out.println("Start of node------------------------------------------------") ;

        backtracked++;

        int tracker =0;

        while (upNode != null) {
            tracker++;
//            System.out.println("game sim finished "+ endNode.getMove());
            upNode.getState().addVisit();
            if (rootCounter == simWinnerNode) {
//                System.out.println("Winner++++++++");
                upNode.getState().addWin();
            } else {
//                System.out.println("LOSER---------");

//                upNode.getState().subWin();
            }
//            System.out.println("Going up a node VVVVV");
            upNode = upNode.getParent();
        }
//
//        if (lastMove != endNode.getMove()) {
//            System.out.println(backtracked);
//            System.out.println("nodecount:  " + tracker + " Move: " + endNode.getMove());
//        }
        lastMove = endNode.getMove();
//        System.out.println("END of node ================================================");

}}


