package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;
import com.thg.accelerator23.connectn.ai.funconcerto.movetree.MoveTree;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class FunConcertoAi extends Player {

  //MoveTree tree = readInitialTreeFromFile("src/main/java/com/thg/accelerator23/connectn/ai/funconcerto/movetree/initialMoveTree.txt");
  MoveTree tree = new MoveTree();
  MinMax mM;
  Board previousBoard = new Board(new GameConfig(10,8,4));

  public FunConcertoAi(Counter counter) {

    super(counter, FunConcertoAi.class.getName());
    this.mM = new MinMax(getCounter());
  }

  public static int indexOf(ArrayList<Integer> arr, int val) {
    return IntStream.range(0, arr.size()).filter(i -> arr.get(i) == val).findFirst().orElse(-1);
  }

  @Override
  public int makeMove(Board board) {
    if(!isBlankBoard(board)) {
      int previousMove = getPreviousMove(board);
      System.out.println(previousMove);
      ArrayList<Integer> validMovesForPreviousMove = getValidLocations(previousBoard);
      //if(isBlankBoard(previousBoard)){
        for (int i = 0; i < validMovesForPreviousMove.size(); i++) {
          try {
            tree.addNode(validMovesForPreviousMove.get(i));
          } catch (InvalidMoveException e) {
            System.out.println("This shouldn't happen");
          }
        //}
      }
      tree = tree.getChildAtPosition(indexOf(validMovesForPreviousMove, previousMove));
    }

    ArrayList<Integer> validMoves = getValidLocations(board);
    for (int i = 0; i < validMoves.size(); i++) {
      try {
        System.out.println("Adding");
        tree.addNode(validMoves.get(i));
      } catch (InvalidMoveException e) {
        System.out.println("This shouldn't happen");
      }
    }
    int[] newMove = mM.miniMax(tree,true);
    tree = tree.getChildAtPosition(indexOf(validMoves, newMove[1]));
    try {
      previousBoard = new Board(tree.getState(), newMove[1],getCounter());
    } catch (InvalidMoveException e) {
      System.out.println("This shouldn't happen");
    }
    return newMove[1];
  }

  public ArrayList<Integer> getValidLocations(Board board){
    ArrayList list = new ArrayList();
    for(int i = 0; i < 10; i++){
      if(!board.hasCounterAtPosition(new Position(i,7))){
        list.add(i);
      }
    }
    return list;
  }

//  public MoveTree readInitialTreeFromFile(String filename){
//    try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
//
//    }catch(IOException e){
//      System.out.println("An error occurred while reading");
//    };
//    return ;
//  }

    public int getPreviousMove(Board newBoard) {
      for(int i = 0; i < 10; i++){
        try {
          Board newBoard1 = new Board(previousBoard, i, getCounter().getOther());
          Counter counter = getTopCounterOnColumn(newBoard1, i);
          Counter counter1 = getTopCounterOnColumn(newBoard, i);
          if(counter == counter1 && counter!=null) {
            return i;
          }
        }catch(InvalidMoveException e){

        }
      }
      return -1;
    };

  public Counter getTopCounterOnColumn(Board board, int column) {
    for(int i = 7; i >= 0; i--){
      if(board.hasCounterAtPosition(new Position(column, i))) {
        return board.getCounterAtPosition(new Position(column, i));
      }
    }
    return null;
  }

  public boolean isBlankBoard(Board board) {
    if(board.getCounterAtPosition(new Position(0,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(1,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(2,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(3,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(4,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(5,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(6,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(7,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(8,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(9,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(10,0)) != null) return false;
    return true;
  }

}