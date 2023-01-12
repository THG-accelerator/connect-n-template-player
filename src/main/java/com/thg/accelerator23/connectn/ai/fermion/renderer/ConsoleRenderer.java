package com.thg.accelerator23.connectn.ai.fermion.renderer;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;

public class ConsoleRenderer implements Renderer {
  @Override
  public void render(Player activePlayer, Board board) {
    printLine(board);
    for (int y = board.getConfig().getHeight() - 1; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth(); x++) {
        System.out.print(" | ");
        Counter counterAtPosition = board.getCounterAtPosition(new Position(x, y));
        String counterString =
            counterAtPosition != null ? counterAtPosition.getStringRepresentation() : " ";
        System.out.print(counterString);
      }
      System.out.print(" | ");
      System.out.println();
      printLine(board);
    }
    if (activePlayer != null) {
      System.out.println(activePlayer.getName() + " to play " +
          activePlayer.getCounter().getStringRepresentation());
    }
  }

  private void printLine(Board board) {
    for (int x = 0; x < board.getConfig().getWidth(); x++) {
      System.out.print("----");
    }
    System.out.print("---");
    System.out.println();
  }

  @Override
  public void renderGameOver(Player winner, GameState gameState, String message) {
    if (gameState.isWin()) {
      if (!message.equals("")) {
        System.out.println(
            "The winner is (" + winner.getCounter().getStringRepresentation() + ") " +
                winner.getName() + " due to a rule violation from the other player: " + message);
      } else {
        System.out.println(
            "The winner is (" + winner.getCounter().getStringRepresentation() + ") " +
                winner.getName() + ". " + message);
      }
    } else {
      System.out.println("The game is a draw.");
    }
  }
}
