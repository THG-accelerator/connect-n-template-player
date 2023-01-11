package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ConnectBotTest {

    Board board = new Board(new GameConfig(10,8,4));
    Board spyBoard = spy(board);
    ConnectBot connectBot = new ConnectBot(Counter.O);


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void makeValidMove() throws InvalidMoveException {
        MiniMaxAI miniMaxAI = mock(MiniMaxAI.class);


        when(miniMaxAI.getMove()).thenReturn(4);


        Assertions.assertEquals(4, connectBot.makeMove(board));

    }
    @Test void makeInvalidMove() throws InvalidMoveException {
        MiniMaxAI miniMaxAI = mock(MiniMaxAI.class);


        when(miniMaxAI.getMove()).thenThrow(InvalidMoveException.class);

        Assertions.assertThrows(InvalidMoveException.class, () -> connectBot.makeMove(board));
        }

}