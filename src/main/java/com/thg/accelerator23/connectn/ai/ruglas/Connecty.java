package com.thg.accelerator23.connectn.ai.ruglas;

import analysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.player.*;
import model.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Connecty extends Player {

  GameConfig config;
  BoardAnalyser analyser;

  public Connecty(Counter counter) {

    super(counter, Connecty.class.getName());

    GameConfig config = new GameConfig(10,8, 4);
//    Board board = new Board(config);
    BoardAnalyser analyser = new BoardAnalyser(config);
  }

  //  Function that returns true if there's three in a row
  private boolean checkThreeInARow(Board board, Counter counter) {

    ArrayList<Counter> threeInARowArray = new ArrayList<>();
    int maxInARow = analyser.calculateGameState(board).getMaxInARowByCounter().get(counter);

    return (maxInARow == 3);
  }
//TODO: RusFunction takes in the board as an argument and returns the locations of all 3 in a rows for this.Counter.


  private ArrayList<Position> getAllPositions(Board board) {

    ArrayList<Position> positions = new ArrayList<>();

    for (int x=0; x < board.getConfig().getWidth(); x++) {
      for ( int y=0 ; y< board.getConfig().getHeight(); y++) {
        Position position = new Position(x,y);
        positions.add(position);
      }
    }
    return positions;
  }


  private boolean canWin(Board board) {
    return checkThreeInARow(board, this.getCounter()) && isMoveValid( RusFunction(board, this.getCounter()) );
  }
  private Position getWinPosition(Board board) {
    Position[] positions = RusFunction(board);
    for (Position position : positions) {
      if (board.isWithinBoard(position) && !board.hasCounterAtPosition(position)) {
        return position;
      }
    }
    System.out.println("No 3 in a row with an empty space afterwards.");
    return null;
  }

  @Override
  public int makeMove(Board board) {
    Random rand = new Random();
    int randomNumber = rand.nextInt(9);
    System.out.println("Hello");
    return randomNumber;
  }
}
