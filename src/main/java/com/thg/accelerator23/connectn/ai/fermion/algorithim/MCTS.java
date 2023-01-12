package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;
import com.thg.accelerator23.connectn.ai.fermion.renderer.ConsoleRenderer;

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

//        public Node selectHighestUTCNode(Node parentNode) {
//        if(parentNode.getChildren().size() != 0) {
//
//            Node bestNode = parentNode.getChildren().get(0);
//            for (Node child : parentNode.getChildren()) {
//                if (bestNode.getUTCValue(parentNode) < child.getUTCValue(parentNode)){
//                    bestNode = child;
//                }
//            }
////            System.out.println("This is the best node "+bestNode.getUTCValue(parentNode)+" move "+bestNode.getMove());
//            return bestNode;
//        }
////            System.out.println("This is the best  parent node "+parentNode.getUTCValue(parentNode));
//        return parentNode;
//
//        }

//        public Node selectHighestUTCNode(Node parentNode) {
//            if(parentNode.getChildren().size() != 0) {
//                Random r = new Random();
//                Node bestNode = parentNode.getChildren().get(r.nextInt(parentNode.getChildren().size()));
//                for (Node child : parentNode.getChildren()) {
//                    if (bestNode.getUTCValue(parentNode) < child.getUTCValue(parentNode)){
//                        bestNode = child;
//                    }
//                }
////            System.out.println("This is the best node "+bestNode.getUTCValue(parentNode)+" move "+bestNode.getMove());
//            return bestNode;
//        }
////            System.out.println("This is the best  parent node "+parentNode.getUTCValue(parentNode));
//        return parentNode;
//
//        }

        public Node selectHighestUTCNode(Node parentNode) {
            Node node = parentNode;
//            System.out.println("START OF NODE OPTIOSN UCT[[[[[[[[[[[[[[[[[[");
            while (node.getChildren().size() != 0) {
                node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUTCValue(parentNode)));
        }
//            System.out.println("FINI-----------------------");
        return node;
    }

    public Node expand(Node promisingNode, ArrayList<Integer> movesMade) {
//        Node promisingNode = selectHighestUTCNode(n);
        promisingNode.generateChildrenNodes();
        Node newNode = randomChildNode(promisingNode);
        movesMade.add(newNode.getMove());
        return newNode;
    }


    public int actualPlay(Counter counter) {
//        System.out.println(counter.getStringRepresentation());
//        Tree tree = new Tree(rootNode);
        Tree tree = new Tree(counter);
        Node rootNode = tree.getRoot();

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);

        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {
            ArrayList<Integer> movesMade = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
            Node promisingNode = selectHighestUTCNode(rootNode);
//
////
////        if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd() && promisingNode.getChildren().size() == 0) {
////            promisingNode.generateChildrenNodes();
////        }
//
//            promisingNode.generateChildrenNodes();
//
//            ArrayList<Integer> movesMade = new ArrayList<>();
//
//            Node nodeToExplore = randomChildNode(promisingNode);
//
//            movesMade.add(nodeToExplore.getMove());

            Node nodeToExplore = expand(promisingNode,movesMade);


            BoardAnalyser analyser = new BoardAnalyser(nodeToExplore.getState().getBoard().getConfig());
//            System.out.println(analyser.calculateGameState(nodeToExplore.getState().getBoard()).isEnd())
//            ;
            ConsoleRenderer obj = new ConsoleRenderer();


//            while (analyser.calculateGameState(nodeToExplore.getState().getBoard()).isEnd()) {
//                nodeToExplore = randomChildNode(promisingNode, movesMade);
//                movesMade.add(nodeToExplore.getMove());
//                System.out.println("a "+ nodeToExplore.getMove());
//                if (movesMade.size() == 10) {
////                    backpropagation(nodeToExplore, nodeToExplore.getState().getCounterOpposite());
//                    promisingNode.getState().setNodeWins(Integer.MAX_VALUE);
//                    nodeToExplore = expand(promisingNode.getParent(), movesMade);
//                    break;
//                }
//            }

//            System.out.println(analyser.calculateGameState(nodeToExplore.getState().getBoard()).isEnd());

                Counter results = simulateMoves(nodeToExplore, rootNode.getState().getCounter());

                backpropagation(nodeToExplore, results, rootNode.getState().getCounter());
            }
//        }


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
            System.out.println("UCT Score: "+ child.getUTCValue(tree.getTrueRoot()));
        }



        for (Node child : tree.getRoot().getChildren()) {
            System.out.println("1: "+child.getMove()+" size of child= "+child.getChildren().size()  +" UCT "+ child.getUTCValue(tree.getTrueRoot())  );
            if(child.getChildren().size()!=0) {
                for (Node childChild : child.getChildren()) {
                    System.out.println("2: parent=>"+child.getMove()+" " +childChild.getMove()+"UCT "+ childChild.getUTCValue(tree.getTrueRoot()));
                    if (childChild.getChildren().size() != 0) {
//                                            System.out.println("2: parent= "+child.getMove()+" Size of child= "+childChild.getChildren().size());

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

        ConsoleRenderer obj = new ConsoleRenderer();
        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState();
        int movesMade = 0;

        if (rootCounter.getOther() == gameState.getWinner()) {
            nodeToExplore.getState().setNodeWins(Integer.MIN_VALUE);
            return gameState.getWinner();
        }


//        System.out.println("START+===============START+=======================START+===============================");
//        System.out.println("Cointer  ="+nodeToExplore.getState().getCounter());
//        System.out.println("Move made " + nodeToExplore.getMove() +"  Parents amount: " +nodeToExplore.getNumberOfParents());

        while(gameState.isEnd() == false){
            try {

//                movesMade++;
//
//                interNode.getState().invertCounter();
//
//                int move = interNode.playRandomMove(interNode.getState().getBoard());
//
//                interNode.getState().setBoard(new Board(interState.getBoard(), move, interState.getCounter()));
//
//                interNode= new Node(interNode,move,interNode.getState().getCounter());
//
//                gameState = boardChecker.calculateGameState(interState.getBoard());



                int move = interNode.playRandomMove(interState.getBoard());

                interState.setBoard(new Board(interState.getBoard(), move, interState.getCounter()));

                gameState = boardChecker.calculateGameState(interState.getBoard());

//                obj.render(null,interState.getBoard());
                interState.invertCounter();
            } catch(InvalidMoveException e) {
                System.out.println("Sim fail");
                throw new RuntimeException(e);
            }
        }
//        System.out.println(gameState.getWinner());
//        System.out.println("FINISHED----------FINISHED-------------------------FINISHED----------------------------------------");

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
//            n.getState().addVisit();
            return n;
        }
        return rootNode;
    }
    public Node randomChildNode(Node rootNode, ArrayList<Integer> movesMode) {
        int childrenSize = rootNode.getChildren().size();
        Random r = new Random();
        int move = r.nextInt(childrenSize);
        if(childrenSize != 0) {
            while( movesMode.contains(move)) {
                move = r.nextInt(childrenSize);
            }
            return rootNode.getChildren().get(move);
        }
        return rootNode;
    }




    private int lastMove;
    private int backtracked;

    public void backpropagation(Node endNode, Counter simWinnerNode, Counter rootCounter) {
        Node upNode = endNode;
        backtracked++;
        int tracker =0;

//        if (rootCounter.getOther() == simWinnerNode) {
//            upNode.getState().setNodeWins(Integer.MIN_VALUE);
//        }

        while (upNode != null) {
            tracker++;
//            System.out.println("game sim finished "+ endNode.getMove());
            upNode.getState().addVisit();
            if (upNode.getState().getCounterOpposite() == simWinnerNode) {
                upNode.getState().addWin();
            } else {
//                upNode.getState().subWin();
            }
            upNode = upNode.getParent();
        }
//
//        if (lastMove != endNode.getMove()) {
//            System.out.println(backtracked);
//            System.out.println("nodecount:  " + tracker + " Move: " + endNode.getMove());
//        }
        lastMove = endNode.getMove();
//        System.out.println("END of node ================================================");

    }
}



