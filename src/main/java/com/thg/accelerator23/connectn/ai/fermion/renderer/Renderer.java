package com.thg.accelerator23.connectn.ai.fermion.renderer;

//import com.thehutgroup.accelerator.connectn.analysis.GameState;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;

public interface Renderer {
  void render(Player activePlayer, Board board);

  void renderGameOver(Player winner, GameState gameState, String message);
}
