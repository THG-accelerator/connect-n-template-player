package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.MiniMaxScoringAlphaBetaAI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @Test
  @DisplayName("AI plays in one of the middle squares first")
  public void AIPlaysInAMiddleColumnFirst() {
    GameConfig config = new GameConfig(10,8,4);
    Board emptyBoard = new Board(config);
    MiniMaxScoringAlphaBetaAI AI = new MiniMaxScoringAlphaBetaAI(Counter.O);

    assertTrue( 3 < AI.makeMove(emptyBoard) && AI.makeMove(emptyBoard) < 6);

  }
}
