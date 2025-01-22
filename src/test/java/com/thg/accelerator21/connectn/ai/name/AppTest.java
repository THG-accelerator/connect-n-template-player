package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ForcesGrid.ForcesGrid;
import com.thg.accelerator23.connectn.ai.ForcesGrid.forPrinting;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }


  private Counter[][] rotateBoard(Counter[][] board) {
    Counter[][] newBoard = new Counter[board[0].length][board.length];
    for (int i = 0; i < board[0].length; i++) {
      for (int j = board.length - 1; j >= 0; j--) {
        newBoard[i][j] = board[j][i];
      }
    }
    return newBoard;
  }

  @Test
  public void shouldReturnMove() {
    int width = 10;
    int height = 8;
    var players = new ArrayList<Function<Counter, Player>>();
    players.add(ForcesGrid::new);
    var home = players.get(0);
    Player homePlayer = home.apply(Counter.O);
    Counter[][] counters = new Counter[height][width];
    counters[7] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[6] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[5] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[4] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[3] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[2] = new Counter[] {null, null, null, null, null, null, null, null, null, null};
    counters[1] = new Counter[] {null, O, null, O, null, null, null, null, null, null};
    counters[0] = new Counter[] {null, X, O, X, null, null, null, O, O, null};
    counters = rotateBoard(counters);

    try {
      //Board blankBoard = new Board(new GameConfig(width,height,4));
      Board board = new Board(counters, new GameConfig(width, height, 4));
      Board board1 = new Board(board, homePlayer.makeMove(board), O);
      forPrinting.printBoard(board1);
    } catch (InvalidMoveException e) {
      System.out.println("invalid move");
    }
  }
}
