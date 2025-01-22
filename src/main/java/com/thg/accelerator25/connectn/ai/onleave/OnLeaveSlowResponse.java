package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OnLeaveSlowResponse extends Player {

    // timeout value, gives 500ms buffer
    private static final long TIMEOUT_SECONDS = 9500;
    private long startTime;

    public OnLeaveSlowResponse(Counter counter) {
        //TODO: fill in your name here
        super(counter, OnLeaveSlowResponse.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        //TODO: some crazy analysis
        //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
        startTime = System.currentTimeMillis();
        List<Integer> legalMoves = getLegalMoves(board);
        int bestMoveFound = legalMoves.get(0);
        int bestScoreFound = Integer.MIN_VALUE;
        int bestDepthFound = 0;
        // for a given depth, first value is the best move, second value is the best score
        List<Integer> depthBest;

        for (int depth = 5; !isTimeUp() && depth < 80; depth++) {
            try {
                depthBest = searchMovesAtDepth(board, depth, legalMoves);
                if (depthBest.get(1) > bestScoreFound) {
                    bestMoveFound = depthBest.get(0);
                    bestScoreFound = depthBest.get(1);
                    bestDepthFound = depth;
                }
            } catch (TimeoutException e) {
                System.out.println("Timeout, use previous best result");
                break;
            }
        }
        System.out.println("Best move found: " + bestMoveFound);
        System.out.println("Best score found: " + bestScoreFound);
        System.out.println("Best depth found: " + bestDepthFound);
        return bestMoveFound;
    }


    private List<Integer> searchMovesAtDepth(Board board, int depth, List<Integer> legalMoves) throws TimeoutException {
        int depthBestScore = Integer.MIN_VALUE;
        int depthBestMove = legalMoves.get(0);
        System.out.println("Searching for moves at depth " + depth);
        for (int move : legalMoves) {
            int score;
            if (isTimeUp()) {
                throw new TimeoutException();
            }
            try {
                Board newBoard = new Board(board, move, getCounter());
                score = minimax(newBoard, depth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, getCounter().getOther(), depth - 1);
            } catch (InvalidMoveException e) {
                break;
            }

            if (score > depthBestScore) {
                depthBestScore = score;
                depthBestMove = move;
            }
        }
        System.out.println("Best score found: " + depthBestScore);
        System.out.println("Best move found: " + depthBestMove);
        return Arrays.asList(depthBestMove, depthBestScore);
    }

    private boolean isTimeUp() {
        return System.currentTimeMillis() - startTime > TIMEOUT_SECONDS;
    }


    private static class TimeoutException extends Exception {
    }

    public List<Integer> getLegalMoves(Board board) {
        List<Integer> legalMoves = new ArrayList<>();
        Counter[][] counterPlacements = board.getCounterPlacements();
        for (int col = 0; col < board.getConfig().getWidth(); col++) {
            if (counterPlacements[col][board.getConfig().getHeight() - 1] == null) {
                legalMoves.add(col);
            }
        }
        return legalMoves;
    }

    private int minimax(Board board, int depth, boolean isMaximizing,
                        int alpha, int beta, Counter currentCounter, int initialDepth) throws TimeoutException, InvalidMoveException {
        if (isTimeUp()) {
            throw new TimeoutException();
        }
        if (depth == 0 || isGameOver(board)) {
            return evaluatePosition(board, initialDepth);
        }

        List<Integer> moves = getLegalMoves(board);
        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int move : moves) {
                Board newBoard = new Board(board, move, currentCounter);
                int score = minimax(newBoard, depth - 1, false, alpha, beta, currentCounter.getOther(), initialDepth);
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int move : moves) {
                Board newBoard = new Board(board, move, currentCounter);
                int score = minimax(newBoard, depth - 1, true, alpha, beta, currentCounter.getOther(), initialDepth);
                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }

    private int evaluatePosition(Board board, int initialDepth) {
        Counter[][] counterPlacements = board.getCounterPlacements();
        Counter counter = getCounter();
        int score = 0;
        int myThrees = 0;
        int oppThrees = 0;
        for (int row = 0; row < counterPlacements.length; row++) {
            for (int col = 0; col < counterPlacements[row].length; col++) {

                // Immediate wins/losses, should choose option that wins in fastest way
                if (hasFourInARow(counterPlacements, row, col, counter)) {
                    return Integer.MAX_VALUE - initialDepth;
                } else if (hasFourInARow(counterPlacements, row, col, counter.getOther())) {
                    return Integer.MIN_VALUE + initialDepth;
                }

                // some weight based on depth
                score -= 5 * initialDepth;

                // Three in a row scenarios
                int myThreesHere = hasThreeInARow(counterPlacements, row, col, counter);
                int oppThreesHere = hasThreeInARow(counterPlacements, row, col, counter.getOther());
                myThrees += myThreesHere;
                score += 7000 * myThreesHere;
                oppThrees += oppThreesHere;
                score -= 7000 * oppThreesHere;

                // Two in a row scenarios
                score += 1000 * hasTwoInARow(counterPlacements, row, col, counter);
                score -= 1000 * hasTwoInARow(counterPlacements, row, col, counter.getOther());

                // Centre control
                score += centreControlColumn(counterPlacements, row, col, counter);
                score -= centreControlColumn(counterPlacements, row, col, counter.getOther());

                // Additional offensive strategies
                score += 50 * ((isStackedPairs(counterPlacements, row, col, counter)) ? 1 : 0);
                score -= 50 * ((isStackedPairs(counterPlacements, row, col, counter.getOther()) ? 1 : 0));
                score += 150 * evaluateTriangleSetup(counterPlacements, row, col, counter);
                score -= 150 * evaluateTriangleSetup(counterPlacements, row, col, counter.getOther());
                score += 200 * evaluateDoubleThreatPattern(counterPlacements, row, col, counter);
                score -= 200 * evaluateDoubleThreatPattern(counterPlacements, row, col, counter.getOther());
//                score += evaluateBottomRowControl(counterPlacements, counter);
//                score -= evaluateBottomRowControl(counterPlacements, counter.getOther());
            }
        }
        // If opponent has three in a row and we don't, this is very bad
        if (oppThrees > 0 && myThrees == 0) {
            return -10 ^ 6 + initialDepth;  // Not quite as bad as an actual loss
        }
        return score;
    }

    private boolean isGameOver(Board board) {
        Counter[][] counterPlacements = board.getCounterPlacements();
        if (isBoardFull(counterPlacements)) {
            return true;
        }
        if (hasFourInARow(counterPlacements, Counter.O) || (hasFourInARow(counterPlacements, Counter.X))) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull(Counter[][] counterPlacements) {
        // returns false if any space is empty, otherwise return true
        for (Counter[] counterPlacement : counterPlacements) {
            for (Counter counter : counterPlacement) {
                if (counter == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasFourInARow(Counter[][] counterPlacements, Counter counter) {
        for (int row = 0; row < counterPlacements.length; row++) {
            for (int col = 0; col < counterPlacements[row].length; col++) {
                if (hasFourInARow(counterPlacements, row, col, counter)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasFourInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        if (counter == counterPlacements[row][col]) {
            // check horizontal right
            if (col + 3 < counterPlacements[row].length &&
                    counter == counterPlacements[row][col + 1] &&
                    counter == counterPlacements[row][col + 2] &&
                    counter == counterPlacements[row][col + 3]) {
                return true;
            }
            // check vertical down
            if (row + 3 < counterPlacements.length &&
                    counter == counterPlacements[row + 1][col] &&
                    counter == counterPlacements[row + 2][col] &&
                    counter == counterPlacements[row + 3][col]) {
                return true;
            }
            // check diagonal (down, right)
            if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length &&
                    counter == counterPlacements[row + 1][col + 1] &&
                    counter == counterPlacements[row + 2][col + 2] &&
                    counter == counterPlacements[row + 3][col + 3]) {
                return true;
            }
            // check diagonal (down, left)
            if (row + 3 < counterPlacements.length && col - 3 >= 0 &&
                    counter == counterPlacements[row + 1][col - 1] &&
                    counter == counterPlacements[row + 2][col - 2] &&
                    counter == counterPlacements[row + 3][col - 3]) {
                return true;
            }
        }
        return false;
    }

    private int hasThreeInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int count = 0;
        // Horizontal check
        if (col + 3 < counterPlacements[row].length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[row][col + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row == counterPlacements.length - 1 ||
                                counterPlacements[row + 1][col + i] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[row][col + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && validMove && filled == 3) {
                    count++;
                }
            }
        }

        // Vertical check (similar pattern)
        if (row + 3 < counterPlacements.length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[row + i][col] != null) {
                            validPattern = false;
                            break;
                        }
                    } else {
                        if (counter == counterPlacements[row + i][col]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 3) {
                    count++;
                }
            }
        }

        // Diagonal check
        // Diagonal (down-right) check
        if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        // Check if empty position is valid (has support or is bottom row)
                        if (counterPlacements[row + i][col + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == counterPlacements.length - 1 ||
                                counterPlacements[row + i + 1][col + i] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[row + i][col + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && validMove && filled == 3) {
                    count++;
                }
            }
        }

        // Diagonal (down-left) check
        if (row + 3 < counterPlacements.length && col - 3 >= 0) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[row + i][col - i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == counterPlacements.length - 1 ||
                                counterPlacements[row + i + 1][col - i] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[row + i][col - i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && validMove && filled == 3) {
                    count++;
                }
            }
        }
        return count;
    }

    private int hasTwoInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int count = 0;
        // Horizontal right check (4 consecutive cells)
        if (col + 3 < counterPlacements[row].length) {
            int filled = 0;
            int empty = 0;
            // Count how many of the 4 positions are filled with the same counter
            for (int i = 0; i < 4; i++) {
                if (counter == counterPlacements[row][col + i]) {
                    filled++;
                } else if (counterPlacements[row][col + i] == null) {
                    empty++;
                }
            }
            // If there are exactly 2 filled and 2 empty (no opponent pieces), count it
            if (filled == 2 && empty == 2) {
                count += 1;
            }
        }

        // Vertical down check
        if (row + 3 < counterPlacements.length) {
            int filled = 0;
            int empty = 0;
            for (int i = 0; i < 4; i++) {
                if (counter == counterPlacements[row + i][col]) {
                    filled++;
                } else if (counterPlacements[row + i][col] == null) {
                    empty++;
                }
            }
            if (filled == 2 && empty == 2) {
                count += 1;
            }
        }

        // Diagonal (down-right) check
        if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length) {
            int filled = 0;
            int empty = 0;
            boolean validEmpty = true;

            for (int i = 0; i < 4; i++) {
                if (counter == counterPlacements[row + i][col + i]) {
                    filled++;
                } else if (counterPlacements[row + i][col + i] == null) {
                    // Check if empty position has support
                    if (row + i == counterPlacements.length - 1 ||
                            counterPlacements[row + i + 1][col + i] != null) {
                        empty++;
                    } else {
                        validEmpty = false;
                    }
                }
            }
            if (filled == 2 && empty == 2 && validEmpty) {
                count++;
            }
        }

        // Diagonal (down-left) check
        if (row + 3 < counterPlacements.length && col - 3 >= 0) {
            int filled = 0;
            int empty = 0;
            boolean validEmpty = true;

            for (int i = 0; i < 4; i++) {
                if (counter == counterPlacements[row + i][col - i]) {
                    filled++;
                } else if (counterPlacements[row + i][col - i] == null) {
                    // Check if empty position has support
                    if (row + i == counterPlacements.length - 1 ||
                            counterPlacements[row + i + 1][col - i] != null) {
                        empty++;
                    } else {
                        validEmpty = false;
                    }
                }
            }
            if (filled == 2 && empty == 2 && validEmpty) {
                count++;
            }
        }
        return count;
    }

    private int centreControlColumn(Counter[][] counterPlacements, int row, int col, Counter counter) {
        if (counterPlacements[row][col] == counter) {
            return (int) (4.5 - Math.abs(col - 4.5));
        }
        return 0;
    }

    // has pairs of pieces stacked vertically (good for building)
    private boolean isStackedPairs(Counter[][] counterPlacements, int row, int col, Counter counter) {
        return row > 1 && counterPlacements[row][col] == counter &&
                counterPlacements[row - 1][col] == counter;
    }

    // evaluate triangle setups (three pieces forming a triangle, 4 directions)
    private int evaluateTriangleSetup(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int count = 0;
        if (counterPlacements[row][col] == counter) {
            // Check for triangle pattern
            if (row > 0 && col > 0) {
                if (counterPlacements[row - 1][col] == counter && counterPlacements[row][col - 1] == counter) {
                    count += 1;
                }
            }
            if (row > 0 && col < counterPlacements[row].length - 1) {
                if (counterPlacements[row - 1][col] == counter && counterPlacements[row][col + 1] == counter) {
                    count += 1;
                }
            }
            if (row < counterPlacements.length - 1 && col > 0) {
                if (counterPlacements[row + 1][col] == counter && counterPlacements[row][col - 1] == counter) {
                    count += 1;
                }
            }
            if (row < counterPlacements.length - 1 && col < counterPlacements[row].length - 1) {
                if (counterPlacements[row + 1][col] == counter && counterPlacements[row][col + 1] == counter) {
                    count += 1;
                }
            }
        }
        return count;
    }

    // Evaluate positions that could lead to double threats
    private int evaluateDoubleThreatPattern(Counter[][] counterPlacements, int row, int col, Counter counter) {
        // Check for patterns that could lead to double threats
        // Example: X_X_ pattern with support underneath
        // Example: _X_X
        int count = 0;
        if (col + 3 < counterPlacements[0].length && row > 0) {
            if (counterPlacements[row - 1][col + 1] != null &&
                    counterPlacements[row - 1][col + 3] != null &&
                    counterPlacements[row][col] == counter &&
                    counterPlacements[row][col + 1] == null &&
                    counterPlacements[row][col + 2] == counter &&
                    counterPlacements[row][col + 3] == null) {
                count += 1;
            }
            if (counterPlacements[row - 1][col] != null &&
                    counterPlacements[row - 1][col + 2] != null &&
                    counterPlacements[row][col] == null &&
                    counterPlacements[row][col + 1] == counter &&
                    counterPlacements[row][col + 2] == null &&
                    counterPlacements[row][col + 3] == counter) {
                count += 1;
            }
        }
        return count;
    }

    // Evaluate control of bottom row (foundation for building)
    private int evaluateBottomRowControl(Counter[][] counterPlacements, Counter counter) {
        int score = 0;
        int bottomRow = 0;

        for (int col = 0; col < counterPlacements[0].length; col++) {
            if (counterPlacements[bottomRow][col] == counter) {
                // More value for central positions
                int centerDistance = Math.abs(col - counterPlacements[0].length / 2);
                score += 75 - (10 * centerDistance);  // More points for center
            }
        }
        return score;
    }

//    private int contreControlColumnRow(Counter[][] counterPlacements, Counter counter){
//        int count = 0;
//        int[] columnWeight = {0, 1, 2, 3, 4, 4, 3, 2, 1, 0};
//        int[] rowWeight = {0, 1, 2, 3, 3, 2, 1, 0};
//        for (int row = 0; row < counterPlacements.length; row++) {
//            for (int col = 0; col < counterPlacements[row].length; col++) {
//                if (counterPlacements[row][col] == counter) {
//                    count = count + columnWeight[col] + rowWeight[row];
//                }
//            }
//        }
//        return count;
//    }
}
