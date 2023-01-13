package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.ChooseMove;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.MiniMaxScoringAlphaBetaAI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

  static GameConfig config = new GameConfig(10,8,4);
  static Board emptyBoard = new Board(config);
  MiniMaxScoringAlphaBetaAI AI = new MiniMaxScoringAlphaBetaAI(Counter.O);

  public static Board placeSeveralCounters(Board board, Counter counter, int[] columnList) throws InvalidMoveException {

    ArrayList<Board> boards = new ArrayList<>();

    boards.add(board);

    for (int i=0; i < columnList.length; i++) {
      board = new Board(boards.get(i), columnList[i],  counter);
      boards.add(board);
    }
    return board;
  }

  @Test
  @DisplayName("AI plays in one of the middle squares first")
  public void AIPlaysInAMiddleColumnFirst() {
    assertTrue( 3 < AI.makeMove(emptyBoard) && AI.makeMove(emptyBoard) < 6);
  }
  @Test
  @DisplayName("AI Wins if it can")
  public void AIWinsIfPossible() throws InvalidMoveException {
    Board board1 = new Board(emptyBoard, 4, Counter.O);
    Board board2 = new Board(board1, 4, Counter.O);
    Board board3 = new Board(board2, 4, Counter.O);

    assertEquals(AI.makeMove(board3), 4);
  }

  @Test
  @DisplayName("AI does not place a counter in a full column")
  public void AIDoesNotDoInvalidMove() throws InvalidMoveException {
    int[] columnZero = {0,0,0,0,0,0,0,0};
    Board testBoard = ChooseMove.placeSeveralCounters(Counter.O, columnZero);
    AI.makeMove(testBoard);
  }

  @Test
  @DisplayName("AI Sets up two wins for itself")
  public void AISetsUpTwoWins() throws InvalidMoveException {
    int[] setUpO = {4,5};
    int[] setUpX = {4};
    Board setUpBoard1 = placeSeveralCounters(emptyBoard, Counter.O, setUpO);
    Board setUpBoard2 = placeSeveralCounters(setUpBoard1, Counter.X, setUpX);

//    int[] correctMoves = {3,6};

    assertTrue(AI.makeMove(setUpBoard2) == 3 || AI.makeMove(setUpBoard2) == 6 );
  }


}
