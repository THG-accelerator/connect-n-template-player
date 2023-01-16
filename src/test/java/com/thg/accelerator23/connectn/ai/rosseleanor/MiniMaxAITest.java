package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
@Disabled
public class MiniMaxAITest {
    MiniMaxAI miniMaxAI;

    private static Stream<Integer> depths() {
        return Stream.of(1, 2, 3, 4);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWin(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{O, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{O, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{O, X, X, null, null, X, null, null, null, null};

        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));


        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(0, move);
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWin2(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, O, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, O, null, null, null, null, null, null};
        counters[0] = new Counter[]{null, X, X, null, O, X, null, null, null, null};

        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));


        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(3, move);
    }


    @ParameterizedTest
    @MethodSource("depths")
    void getMoveBlocking(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[0] = new Counter[]{O, X, X, X, null, null, null, null, O, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(4, move);
    }


    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWinTwo(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null, null, X, X, X, null};
        counters[2] = new Counter[]{null, null, X, null, null, O, O, O, X, null};
        counters[1] = new Counter[]{null, null, X, null, null, O, O, X, O, null};
        counters[0] = new Counter[]{null, null, X, O, O, O, X, X, X, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(5, move);
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWinThree(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 5;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null};
        counters[2] = new Counter[]{null, O, O, X, O};
        counters[1] = new Counter[]{null, O, X, O, O};
        counters[0] = new Counter[]{null, X, O, X, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertTrue(move == 1 | move == 4);
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWinFour(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 5;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, O, O, O};
        counters[3] = new Counter[]{null, null, X, X, X};
        counters[2] = new Counter[]{null, X, O, O, O};
        counters[1] = new Counter[]{null, O, X, O, O};
        counters[0] = new Counter[]{null, X, O, X, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(1, move);
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveWinFive(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 10;
        int height = 8;

        Counter[][] counters = new Counter[height][width];
        counters[7] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[6] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[5] = new Counter[]{null, null, null, null, null, null, null, null, null, null};
        counters[4] = new Counter[]{null, null, O, O, O, null, null, null, null, null};
        counters[3] = new Counter[]{null, null, X, X, X, null, null, null, null, null};
        counters[2] = new Counter[]{null, X, O, O, O, null, null, null, null, null};
        counters[1] = new Counter[]{null, O, X, O, O, null, null, null, null, null};
        counters[0] = new Counter[]{null, X, O, X, O, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(1, move);
    }

    @ParameterizedTest
    @MethodSource("depths")
    void getMoveBlockingTwo(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 5;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null};
        counters[3] = new Counter[]{null, X, null, X, X};
        counters[2] = new Counter[]{null, X, O, O, O};
        counters[1] = new Counter[]{null, O, X, O, O};
        counters[0] = new Counter[]{null, X, O, X, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(2, move);
    }

    @Test
    void fullBoard() throws InvalidMoveException {
        int numInARow = 4;
        int width = 5;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{O, X, O, O, O};
        counters[3] = new Counter[]{X, O, X, X, X};
        counters[2] = new Counter[]{X, X, X, O, O};
        counters[1] = new Counter[]{X, O, X, O, O};
        counters[0] = new Counter[]{X, X, O, X, O};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(5, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));


        Assertions.assertThrows(RuntimeException.class, ()-> miniMaxAI.getMove());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void middlePlacementForEmptyBoard(int depth) throws InvalidMoveException {
        int numInARow = 4;
        int width = 5;
        int height = 5;

        Counter[][] counters = new Counter[height][width];
        counters[4] = new Counter[]{null, null, null, null, null};
        counters[3] = new Counter[]{null, null, null, null, null};
        counters[2] = new Counter[]{null, null, null, null, null};
        counters[1] = new Counter[]{null, null, null, null, null};
        counters[0] = new Counter[]{null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, numInARow));

        miniMaxAI = new MiniMaxAI(depth, O);
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        int move = miniMaxAI.getMove();

        Assertions.assertEquals(2, move);
    }


    @Test
    void getChildrenTest() throws InvalidMoveException {
        int width = 6;
        int height = 1;
        Counter counter = X;
        miniMaxAI = new MiniMaxAI(1, O);


        Counter[][] counters = new Counter[height][width];
        counters[0] = new Counter[]{null, null, null, null, null, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        List<Board> children = miniMaxAI.getChildren(counter);
        List<Board> expectedList = new ArrayList<>();

        Counter[][] columnOne = new Counter[height][width];
        columnOne[0] = new Counter[]{counter, null, null, null, null, null};
        columnOne = rotateBoard(columnOne);

        Board board1 = new Board(columnOne, new GameConfig(width, height, 4));
        expectedList.add(board1);

        Position position0 = new Position(0, 0);
        int numberChildren = children.size();

        Assertions.assertEquals(expectedList.get(0).getConfig(), children.get(0).getConfig());
        Assertions.assertEquals(expectedList.get(0).getCounterAtPosition(position0), children.get(0).getCounterAtPosition(position0));
        Assertions.assertEquals(6, numberChildren);
    }

    @Test
    void getChildrenDontCountFullColumn() throws InvalidMoveException {
        int width = 6;
        int height = 2;
        miniMaxAI = new MiniMaxAI(1, O);

        Counter[][] counters = new Counter[height][width];
        counters[1] = new Counter[]{null, X, null, null, null, null};
        counters[0] = new Counter[]{null, X, null, null, X, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        List<Board> children = miniMaxAI.getChildren(Counter.X);

        int numberChildren = children.size();
        Assertions.assertEquals(5, numberChildren);
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

;

