package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

class MiniMaxAITest {
    MiniMaxAI miniMaxAI;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getMoveWin(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{O, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{O, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{O, X, X, null, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(0, move);

    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getMoveBlocking(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{O, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{O, X, X, X, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(4, move);
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

}