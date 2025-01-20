package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator25.connectn.ai.onleave.OnLeaveSlowResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

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
  public void testGetLegalMoves_EmptyBoard() {
    OnLeaveSlowResponse mockResponse = new OnLeaveSlowResponse(Counter.X);
    GameConfig config = new GameConfig(7, 6, 4); // 7 columns, 6 rows
    Board board = new Board(config);
    List<Integer> legalMoves = mockResponse.getLegalMoves(board);
    assertEquals(config.getWidth(), legalMoves.size());
    for (int i = 0; i < config.getWidth(); i++) {
      assertTrue(legalMoves.contains(i));
    }
  }


  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }
}
