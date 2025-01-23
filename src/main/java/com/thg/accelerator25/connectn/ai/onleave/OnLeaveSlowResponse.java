package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.*;


public class OnLeaveSlowResponse extends Player {

    // timeout value, gives 500ms buffer
    private static final long TIMEOUT_SECONDS = 9500;
    private long startTime;

    private final Map<Long, Integer> boardHashMap = new HashMap<>();

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

        if (boardHashMap.size() > 100000) {
            clearCache();
        }

        for (int depth = 4; !isTimeUp() && depth < 80; depth++) {
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
        System.out.println("Cache Size: " + boardHashMap.size());
        return bestMoveFound;
    }

    private long hashCode(Board board) {
        Counter[][] counterPlacement = board.getCounterPlacements();
        long result = 1;
        for (Counter[] counters : counterPlacement) {
            result = 31 * result + Arrays.hashCode(counters);
        }
        return result;
    }

    private void clearCache() {
        boardHashMap.clear();
    }

    private List<Integer> searchMovesAtDepth(Board board, int depth, List<Integer> legalMoves) throws TimeoutException {
        int depthBestScore = Integer.MIN_VALUE;
        int depthBestMove = legalMoves.get(0);
//        System.out.println("Searching for moves at depth " + depth);
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
//        System.out.println("Best score found: " + depthBestScore);
//        System.out.println("Best move found: " + depthBestMove);
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

        long boardHash = hashCode(board);
        if (boardHashMap.containsKey(boardHash)) {
            return boardHashMap.get(boardHash);
        }

        List<Integer> moves = getLegalMoves(board);
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int move : moves) {
            Board newBoard = new Board(board, move, currentCounter);
            int score = minimax(newBoard, depth - 1, true, alpha, beta, currentCounter.getOther(), initialDepth);

            if (isMaximizing) {
                bestScore = Math.max(score, bestScore);
                alpha = Math.max(alpha, score);
            } else {
                bestScore = Math.min(score, bestScore);
                beta = Math.min(beta, score);
            }
            if (beta <= alpha) {
                break;
            }
        }

        // Store the result in hash map
        boardHashMap.put(boardHash, bestScore);

        return bestScore;
    }

    private int evaluatePosition(Board board, int initialDepth) {
        Counter[][] counterPlacements = board.getCounterPlacements();
        Counter counter = getCounter();
        int score = 0;
        int myThrees = 0;
        int oppThrees = 0;
        for (int col = 0; col < counterPlacements.length; col++) {
            for (int row = 0; row < counterPlacements[col].length; row++) {

                // Immediate wins/losses, should choose option that wins in fastest way
                if (hasFourInARow(counterPlacements, row, col, counter)) {
                    return Integer.MAX_VALUE - initialDepth;
                } else if (hasFourInARow(counterPlacements, row, col, counter.getOther())) {
                    return Integer.MIN_VALUE + initialDepth;
                }

                // some weight based on depth
                score -= 5 * initialDepth;

                // Three in a row scenarios
                List<Integer> myThreesHere = hasThreeInARow(counterPlacements, row, col, counter);
                List<Integer> oppThreesHere = hasThreeInARow(counterPlacements, row, col, counter.getOther());
                myThrees += myThreesHere.get(0) + myThreesHere.get(1);
                myThrees += myThreesHere.get(0) + myThreesHere.get(1);
                score += 5000 * myThreesHere.get(0);
                score += 2500 * myThreesHere.get(1);
                oppThrees += oppThreesHere.get(0) + oppThreesHere.get(1);
                score -= 5000 * oppThreesHere.get(0);
                score -= 2500 * oppThreesHere.get(1);

                // Two in a row scenarios
                List<Integer> myTwosHere = hasTwoInARow(counterPlacements, row, col, counter);
                List<Integer> oppTwosHere = hasTwoInARow(counterPlacements, row, col, counter.getOther());
                score += 1500 * myTwosHere.get(0);
                score += 750 * myTwosHere.get(1);
                score -= 1500 * oppTwosHere.get(0);
                score -= 750 * oppTwosHere.get(1);

                // Centre control
                score += centreControlColumn(counterPlacements, row, col, counter);
                score -= centreControlColumn(counterPlacements, row, col, counter.getOther());

                // Additional offensive strategies
                score += 200 * ((isStackedPairs(counterPlacements, row, col, counter)) ? 1 : 0);
                score -= 200 * ((isStackedPairs(counterPlacements, row, col, counter.getOther()) ? 1 : 0));
                score += 300 * evaluateTriangleSetup(counterPlacements, row, col, counter);
                score -= 400 * evaluateTriangleSetup(counterPlacements, row, col, counter.getOther());
                //              score += 200 * evaluateDoubleThreatPattern(counterPlacements, row, col, counter);
                //               score -= 200 * evaluateDoubleThreatPattern(counterPlacements, row, col, counter.getOther());
//                score += evaluateBottomRowControl(counterPlacements, counter);
//                score -= evaluateBottomRowControl(counterPlacements, counter.getOther());
            }
        }
        // If opponent has three in a row and we don't, this is very bad
        if (oppThrees > 0 && myThrees == 0) {
            return Integer.MIN_VALUE / 2;  // Not quite as bad as an actual loss
        }
        if (myThrees >= 2) score += 15000;
        if (oppThrees >= 2) score -= 15000;
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
        for (int col = 0; col < counterPlacements.length; col++) {
            for (int row = 0; row < counterPlacements[col].length; row++) {
                if (hasFourInARow(counterPlacements, row, col, counter)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasFourInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        if (counter == counterPlacements[col][row]) {
            // check horizontal right
            if (col + 3 < counterPlacements.length &&
                    counter == counterPlacements[col + 1][row] &&
                    counter == counterPlacements[col + 2][row] &&
                    counter == counterPlacements[col + 3][row]) {
                return true;
            }
            // check vertical up
            if (row + 3 < counterPlacements[col].length &&
                    counter == counterPlacements[col][row + 1] &&
                    counter == counterPlacements[col][row + 2] &&
                    counter == counterPlacements[col][row + 3]) {
                return true;
            }
            // check diagonal (up, right)
            if (col + 3 < counterPlacements.length && row + 3 < counterPlacements[col].length &&
                    counter == counterPlacements[col + 1][row + 1] &&
                    counter == counterPlacements[col + 2][row + 2] &&
                    counter == counterPlacements[col + 3][row + 3]) {
                return true;
            }
            // check diagonal (down, right)
            if (col + 3 < counterPlacements.length && row - 3 >= 0 &&
                    counter == counterPlacements[col + 1][row - 1] &&
                    counter == counterPlacements[col + 2][row - 2] &&
                    counter == counterPlacements[col + 3][row - 3]) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> hasThreeInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int countCanPlay = 0;
        int countCantPlay = 0;
        // Horizontal check
        if (col + 3 < counterPlacements.length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[col + i][row] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row == 0 ||
                                counterPlacements[col + i][row - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col + i][row]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 3) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }

        // Vertical check
        if (row + 3 < counterPlacements[col].length) {
            // Only check bottom empty position since pieces must stack
            boolean validPattern = true;

            // Check the bottom three positions for counters
            for (int i = 0; i < 3; i++) {
                if (counter != counterPlacements[col][row + i]) {
                    validPattern = false;
                    break;
                }
            }

            // Check if top position is empty
            if (validPattern && counterPlacements[col][row + 3] == null) {
                countCanPlay++; // Vertical threes are always playable immediately
            }
        }

        // Diagonal check
        // Diagonal up right check
        if (col + 3 < counterPlacements.length && row + 3 < counterPlacements[col].length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        // Check if empty position is valid (has support or is bottom row)
                        if (counterPlacements[col + i][row + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == 0 ||
                                counterPlacements[col + i][row + i - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col + i][row + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 3) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }

        // Diagonal (up-left) check
        if (row + 3 < counterPlacements[col].length && col - 3 >= 0) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[col - i][row + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == 0 ||
                                counterPlacements[col - i][row + i - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col - i][row + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 3) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }
        return Arrays.asList(countCanPlay,countCantPlay);
    }

    private List<Integer> hasTwoInARow(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int countCanPlay = 0;
        int countCantPlay = 0;
        // Horizontal check
        if (col + 3 < counterPlacements.length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[col + i][row] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row == 0 ||
                                counterPlacements[col + i][row - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col + i][row]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 2) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }

//        // Vertical check (similar pattern)
//        if (row + 2 < counterPlacements[col].length) {
//            boolean validPattern = true;
//            for (int i = 0; i < 2; i++) {
//                if (counter != counterPlacements[col][row + i]) {
//                    validPattern = false;
//                    break;
//                }
//            }
//            if (counterPlacements[col][row + 2] != null) {
//                validPattern = false;
//            }
//            if (validPattern) {
//                countCanPlay++;
//            }
//        }

        // Diagonal check
        // Diagonal up right check
        if (col + 3 < counterPlacements.length && row + 3 < counterPlacements[col].length) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        // Check if empty position is valid (has support or is bottom row)
                        if (counterPlacements[col + i][row + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == 0 ||
                                counterPlacements[col + i][row + i - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col + i][row + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 2) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }

        // Diagonal (up-left) check
        if (row + 3 < counterPlacements[col].length && col - 3 >= 0) {
            for (int emptyPos = 0; emptyPos < 4; emptyPos++) {
                int filled = 0;
                boolean validPattern = true;
                boolean validMove = true;

                for (int i = 0; i < 4; i++) {
                    if (i == emptyPos) {
                        if (counterPlacements[col - i][row + i] != null) {
                            validPattern = false;
                            break;
                        }
                        // Check if move is valid (either bottom row or has support)
                        if (row + i == 0 ||
                                counterPlacements[col - i][row + i - 1] != null) {
                            validMove = true;
                        } else {
                            validMove = false;
                        }
                    } else {
                        if (counter == counterPlacements[col - i][row + i]) {
                            filled++;
                        } else {
                            validPattern = false;
                            break;
                        }
                    }
                }
                if (validPattern && filled == 2) {
                    if (validMove) {
                        countCanPlay++;
                    } else {
                        countCantPlay++;
                    }
                }
            }
        }
        return Arrays.asList(countCanPlay,countCantPlay);
    }

    private int centreControlColumn(Counter[][] counterPlacements, int row, int col, Counter counter) {
        if (counterPlacements[col][row] == counter) {
            return (int) (4.5 - Math.abs(col - 4.5));
        }
        return 0;
    }

    // has pairs of pieces stacked vertically (good for building)
    private boolean isStackedPairs(Counter[][] counterPlacements, int row, int col, Counter counter) {
        return row > 1 && row < counterPlacements[col].length - 1 &&
                counterPlacements[col][row] == counter &&
                counterPlacements[col][row - 1] == counter &&
                counterPlacements[col][row + 1] == null;
    }

    // evaluate triangle setups (three pieces forming a triangle, 4 directions)
    private int evaluateTriangleSetup(Counter[][] counterPlacements, int row, int col, Counter counter) {
        int count = 0;
        if (counterPlacements[col][row] == counter) {
            // Check for triangle pattern
            if (col > 0 && row > 0) {
                if (counterPlacements[col - 1][row] == counter && counterPlacements[col][row - 1] == counter) {
                    count += 1;
                }
            }
            if (col < counterPlacements.length - 1 && row > 0) {
                if (counterPlacements[col + 1][row] == counter && counterPlacements[col][row - 1] == counter) {
                    count += 1;
                }
            }
            if (col > 0 && row < counterPlacements[col].length - 1) { // swapped conditions
                if (counterPlacements[col - 1][row] == counter && counterPlacements[col][row + 1] == counter) {
                    count += 1;
                }
            }
            if (col < counterPlacements.length - 1 && row < counterPlacements[col].length - 1) {
                if (counterPlacements[col + 1][row] == counter && counterPlacements[col][row + 1] == counter) {
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
        if (col + 3 < counterPlacements.length && row > 0) {
            if (counterPlacements[col + 1][row - 1] != null &&
                    counterPlacements[col + 3][row - 1] != null &&
                    counterPlacements[col][row] == counter &&
                    counterPlacements[col + 1][row] == null &&
                    counterPlacements[col + 2][row] == counter &&
                    counterPlacements[col + 3][row] == null) {
                count += 1;
            }
            if (counterPlacements[col][row - 1] != null &&
                    counterPlacements[col + 2][row - 1] != null &&
                    counterPlacements[col][row] == null &&
                    counterPlacements[col + 1][row] == counter &&
                    counterPlacements[col + 2][row] == null &&
                    counterPlacements[col + 3][row] == counter) {
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
