package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;
import static org.junit.jupiter.api.Assertions.*;

class MiniMaxAITest {

    MiniMaxAI miniMaxAI;

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
        columnOne[0] = new Counter[]{X, null, null, null, null, null};
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
        Counter counter = X;
        miniMaxAI = new MiniMaxAI(1, O);


        Counter[][] counters = new Counter[height][width];
        counters[1] = new Counter[]{null, X, null, null, null, null};
        counters[0] = new Counter[]{null, X, null, null, X, null};
        counters = rotateBoard(counters);

        Board board = new Board(counters, new GameConfig(width, height, 4));
        miniMaxAI.setBoard(board);
        miniMaxAI.setBoardAnalyser(new BoardAnalyser(board.getConfig()));

        List<Board> children = miniMaxAI.getChildren(counter);

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
