package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.ChooseMove;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.RandomAI;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.TestMove;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.MiniMaxScoringAlphaBetaAI;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
  static GameConfig config = new GameConfig(10,8,4);
  static Board emptyBoard = new Board(config);

  MiniMaxScoringAlphaBetaAI AbAI = new MiniMaxScoringAlphaBetaAI(Counter.O);
  RandomAI randyO = new RandomAI(Counter.O);
  RandomAI randyX = new RandomAI(Counter.X);

  public static Board placeSeveralCounters(Board board, Counter counter, int[] columnList) throws InvalidMoveException {

    ArrayList<Board> boards = new ArrayList<>();

    boards.add(board);

    for (int i=0; i < columnList.length; i++) {
      board = new Board(boards.get(i), columnList[i],  counter);
      boards.add(board);
    }
    return board;
  }

//  @Disabled
  @Test
  @DisplayName("AI plays in one of the middle squares first")
  public void AIPlaysInAMiddleColumnFirst() {
    assertTrue( 3 < randyO.makeMove(emptyBoard) && randyO.makeMove(emptyBoard) < 6);
    assertTrue( 3 < randyO.makeMove(emptyBoard) && randyO.makeMove(emptyBoard) < 6);
    assertTrue( 3 < randyO.makeMove(emptyBoard) && randyO.makeMove(emptyBoard) < 6);

    // duplicate code because it might accidentally play in the middle once.
  }
//  @Disabled
  @Test
  @DisplayName("AI Wins if it can")
  public void AIWinsIfPossible() throws InvalidMoveException {
    Board board1 = new Board(emptyBoard, 4, Counter.O);
    Board board2 = new Board(board1, 4, Counter.O);
    Board board3 = new Board(board2, 4, Counter.O);

    assertEquals(randyO.makeMove(board3), 4);
  }

//  @Disabled
  @Test
  @DisplayName("AI does not place a counter in a full column")
  public void AIDoesNotDoInvalidMove() throws InvalidMoveException {
    int[] columnZero = {0,0,0,0,0,0,0,0};
    Board testBoard = placeSeveralCounters(emptyBoard, Counter.O, columnZero);
    randyO.makeMove(testBoard);
  }

  @Test
  @DisplayName("AI does not place a counter in a full column even when opponent has 3 in a row")
  public void AIDoesNotDoInvalidMove2() throws InvalidMoveException {
    int[] columnZero = {0,0,0,0,0,0,0,0};
    Board testBoard = placeSeveralCounters(emptyBoard, Counter.X, columnZero);
//    System.out.println(testBoard.getCounterAtPosition(new Position(0,7)));
    randyO.makeMove(testBoard);
  }

//  @Disabled
  @Test
  @DisplayName("AI Sets up two wins for itself")
  public void AISetsUpTwoWins1() throws InvalidMoveException {
    int[] setUpO = {2,3};
    int[] setUpX = {2};
    Board setUpBoard1 = placeSeveralCounters(emptyBoard, Counter.O, setUpO);
    Board setUpBoard2 = placeSeveralCounters(setUpBoard1, Counter.X, setUpX);
//    int[] correctMoves = {3,6};
    assertTrue(randyO.makeMove(setUpBoard2) == 1 || randyO.makeMove(setUpBoard2) == 4 );
  }
  @Disabled
  @Test
  @DisplayName("AI sets up two wins for itself.")
  public void AISetsUpTwoWins2() throws InvalidMoveException {

    int[] setUpX = {2,4,5};
    int[] setUpO = {2,3,4,6};
    int[] setUpXTwo = {6,6};
    int[] setUpOTwo = {6};

    Board setUpBoard1 = placeSeveralCounters(emptyBoard, Counter.X, setUpX);
    Board setUpBoard2 = placeSeveralCounters(setUpBoard1, Counter.O, setUpO);
    Board setUpBoard3 = placeSeveralCounters(setUpBoard2, Counter.X, setUpXTwo);
    Board setUpBoard4 = placeSeveralCounters(setUpBoard3, Counter.O, setUpOTwo);

    assertEquals(5, randyO.makeMove(setUpBoard4));
  }
  @Test
  @DisplayName("AI Prioritises Blocking Over setting up two wins for itself")
  public void AIBlocksOverSetUp() throws InvalidMoveException {

    int[] setUpX = {2,4,5,6};
    int[] setUpO = {2,3,4};
    int[] setUpXTwo = {6,6};
    int[] setUpOTwo = {6};

    Board setUpBoard1 = placeSeveralCounters(emptyBoard, Counter.X, setUpX);
    Board setUpBoard2 = placeSeveralCounters(setUpBoard1, Counter.O, setUpO);
    Board setUpBoard3 = placeSeveralCounters(setUpBoard2, Counter.X, setUpXTwo);
    Board setUpBoard4 = placeSeveralCounters(setUpBoard3, Counter.O, setUpOTwo);

    assertEquals(7, randyO.makeMove(setUpBoard4));
  }

}
