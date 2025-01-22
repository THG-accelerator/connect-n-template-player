package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;

public class Analysis {
    /*
    public methods:
    - getValidLocations(Board) -> ArrayList<Position>
        get all valid locations to place on the board
    - isWin(Board, Counter) -> boolean
        returns true if counterString wins
    - gameOver(Board) -> boolean
        returns true if no more space in board or if someone wins

    private methods:
    - getMinVacantY(int, Board) -> int
        returns the y position for column x
    * */

    private static int getMinVacantY(int x, Board board) {
        int h = board.getConfig().getHeight();
        //edge-case when column is full
        if (board.getCounterPlacements()[x][h - 1] != null) {
            return 100;
        }
        for (int i = h - 1; i >= 0; --i) {
            if (i == 0 || board.getCounterPlacements()[x][i - 1] != null) {
                return i;
            }
        }
        throw new RuntimeException("no y is vacant");
    }

    public static ArrayList<Position> getValidLocations(Board board) {
        int width = board.getConfig().getWidth();
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            Position pos = new Position(i, getMinVacantY(i, board));
            if (board.isWithinBoard(pos)) {
                positions.add(pos);
            }
        }
        return positions;
    }

    public static boolean isWin(Board board, Counter counter) {
        Counter[][] grid = board.getCounterPlacements();
        int height = board.getConfig().getHeight();
        int width = board.getConfig().getWidth();

        //horizontal
        for (int c = 0; c < width - 3; c++) {
            for (int r = 0; r < height; r++) {
                if (grid[c][r] == counter && grid[c + 1][r] == counter && grid[c + 2][r] == counter && grid[c + 3][r] == counter) {
                    return true;
                }
            }
        }
        //vertical
        for (int c = 0; c < width; c++) {
            for (int r = 0; r < height - 3; r++) {
                if (grid[c][r] == counter && grid[c][r + 1] == counter && grid[c][r + 2] == counter && grid[c][r + 3] == counter) {
                    return true;
                }
            }
        }
        //diagonal TopRight to BottomLeft");
        for (int c = 0; c < width - 3; c++) {
            for (int r = 0; r < height - 3; r++) {
                if (grid[c][r] == counter && grid[c + 1][r + 1] == counter && grid[c + 2][r + 2] == counter && grid[c + 3][r + 3] == counter) {
                    return true;
                }
            }
        }
        //diagonal TopLeft to BottomRight");
        for (int c = 0; c < width; c++) {
            for (int r = 3; r < height; r++) {
                if (grid[c][r] == counter && grid[c + 1][r - 1] == counter && grid[c + 2][r - 2] == counter && grid[c + 3][r - 3] == counter) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean gameOver(Board board, Counter counter) {
        return isWin(board, counter) || isWin(board, counter.getOther()) || getValidLocations(board).isEmpty();
    }
}
