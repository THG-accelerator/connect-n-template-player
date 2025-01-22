package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

import java.util.Arrays;
import java.util.Collections;

public class forPrinting {
    // print function that can be deleted after
    public static void printBoard(Board board) {
        System.out.println();
        Counter[][] cBoard = deepCopy(board.getCounterPlacements());
        Counter[][] tempBoard = new Counter[cBoard[0].length][cBoard.length];
        for (int i = 0; i < cBoard[0].length; i++) {
            for (int j = cBoard.length - 1; j >= 0; j--) {
                tempBoard[i][j] = cBoard[j][i];
            }
        }
        Collections.reverse(Arrays.asList(tempBoard));
        Arrays.asList(tempBoard).forEach(counter -> {
            System.out.print("| ");
            Arrays.stream(counter).forEach(c -> {
                String character = c == null? " " : c.getStringRepresentation();
                System.out.print(character + " | ");
            });
            System.out.println();
        });
    }
    private static <Object> Object[][] deepCopy(Object[][] matrix) {
        return (Object[][])Arrays.stream(matrix).map((el) -> {
            return (Object[])el.clone();
        }).toArray(($) -> {
            return (Object[][])matrix.clone();
        });
    }
}

