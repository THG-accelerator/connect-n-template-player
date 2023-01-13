package com.thg.accelerator23.connectn.ai.rosseleanor.analysis;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
@Disabled
class BoardAnalyserTest {

    @Test
    void analyseZero() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(0, colO);
        Assertions.assertEquals(0, colX);

    }

    @Test
    void analyseTwoNought() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, O, O, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(1, colO);
        Assertions.assertEquals(-1, colX);

    }

    @Test
    void analyseThreeNought() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, O, O, O, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(100, colO);
        Assertions.assertEquals(-100, colX);

    }

    @Test
    void analyseFourNought() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, O, O, O, O, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(1E6, colO);
        Assertions.assertEquals(-1E6, colX);

    }

    @Test
    void analyseTwoCross() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, X, X, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-1, colO);
        Assertions.assertEquals(1, colX);

    }

    @Test
    void analyseThreeCross() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, X, X, X, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-100, colO);
        Assertions.assertEquals(100, colX);

    }

    @Test
    void analyseFourCross() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, X, X, X, X, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-1E6, colO);
        Assertions.assertEquals(1E6, colX);

    }

    @Test
    void analyseFourThreeMixedHorizontal() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, X, X, X, X, O, O, O, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-999900, colO);
        Assertions.assertEquals(999900, colX);

    }

    @Test
    void analyseFourThreeMixedVertical() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{X, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{X, O, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{X, O, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{X, O, null, null, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-999900, colO);
        Assertions.assertEquals(999900, colX);

    }

    @Test
    void analyseFourThreeMixedDiagonal() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{X, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{X, null, null, O, null, null, null, null, null, null};
        counters[1] = new Counter[]{X, null, O, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{X, O, null, null, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));

        int colO = boardAnalyser.analyse(board, O);
        int colX = boardAnalyser.analyse(board, X);

        Assertions.assertEquals(-999900, colO);
        Assertions.assertEquals(999900, colX);

    }

    @Test
    void boardIsEmptyFalse() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, null, X, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));
        Assertions.assertFalse(boardAnalyser.isBoardEmpty(board));

    }
    @Test
    void boardIsEmptyTrue() {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(width, height, numInARow));
        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));
        Assertions.assertTrue(boardAnalyser.isBoardEmpty(board));

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