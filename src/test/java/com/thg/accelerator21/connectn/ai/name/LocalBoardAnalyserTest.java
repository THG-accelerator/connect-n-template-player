package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.LocalBoardAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalBoardAnalyserTest {

    Board board;

    @BeforeEach
    public void start() {
        board = new Board(new GameConfig(10,8,4));
    }

    @Test
    public void checkForFullColumn() {

        try {
            for (int i = 0; i < 8; i++) {
                board = new Board(board,3, Counter.O);
            }
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        LocalBoardAnalyser localBoardAnalyser = new LocalBoardAnalyser(board);
        boolean freeColumn3 = localBoardAnalyser.checkForFullColumn(3);
        boolean freeColumn0 = localBoardAnalyser.checkForFullColumn(0);

        Assertions.assertTrue(freeColumn3);
        Assertions.assertFalse(freeColumn0);
    }

    @Test
    public void fullColumns() {

        try {
            for (int i = 0; i < 8; i++) {
                board = new Board(board,3, Counter.O);
            }
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        LocalBoardAnalyser localBoardAnalyser = new LocalBoardAnalyser(board);
        boolean[] full = localBoardAnalyser.fullColumns();

        Assertions.assertEquals(full.length, 10);
        Assertions.assertTrue(full[3]);
        Assertions.assertFalse(full[0]);

    }
}