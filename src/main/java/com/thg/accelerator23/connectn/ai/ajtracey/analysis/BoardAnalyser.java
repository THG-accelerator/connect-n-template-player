package com.thg.accelerator23.connectn.ai.ajtracey.analysis;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ajtracey.model.Line;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class BoardAnalyser {
    private Function<Position, Position> hMover = p -> new Position(p.getX() + 1, p.getY());

    private Function<Position, Position> hMoverBackwards = p -> new Position (p.getX() - 1, p.getY());
    private Function<Position, Position> vMover = p -> new Position(p.getX(), p.getY() + 1);

    private Function<Position, Position> vMoverBackwards = p -> new Position(p.getX(), p.getY() -1);
    private Function<Position, Position> diagUpRightMover = hMover.compose(vMover);

    private Function<Position, Position> diagDownLeftMover = hMoverBackwards.compose(vMoverBackwards);
    private Function<Position, Position> diagUpLeftMover =
            p -> new Position(p.getX() - 1, p.getY() + 1);

    private Function<Position, Position> diagDownRightMover =
            p -> new Position(p.getX() + 1, p.getY() -1);
    private Map<Function<Position, Position>, List<Position>> positionsByFunction;

    private final int boardHeight;

    private final int boardWidth;

    private Map<Integer, Integer> distanceFromCentreSpaces;

    public BoardAnalyser(GameConfig config) {
        boardHeight = config.getHeight();
        boardWidth = config.getWidth();
        positionsByFunction = new HashMap<>();
        List<Position> leftEdge = IntStream.range(0, config.getHeight())
                .mapToObj(Integer::new)
                .map(i -> new Position(0, i))
                .collect(Collectors.toList());
        List<Position> bottomEdge = IntStream.range(0, config.getWidth())
                .mapToObj(Integer::new)
                .map(i -> new Position(i, 0))
                .collect(Collectors.toList());
        List<Position> rightEdge = leftEdge.stream()
                .map(p -> new Position(config.getWidth() - 1, p.getY()))
                .collect(Collectors.toList());
        List<Position> topEdge = bottomEdge.stream()
                .map(p -> new Position(p.getX(), config.getHeight() - 1))
                .collect(Collectors.toList());

        List<Position> leftBottom = Stream.concat(leftEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());
        List<Position> rightBottom = Stream.concat(rightEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());

        List<Position> rightTop = Stream.concat(rightEdge.stream(),
                topEdge.stream()).distinct().collect(Collectors.toList());
        List<Position> leftTop = Stream.concat(leftEdge.stream(),
                topEdge.stream()).distinct().collect(Collectors.toList());


        positionsByFunction.put(hMover, leftEdge);
        positionsByFunction.put(hMoverBackwards, rightEdge);
        positionsByFunction.put(vMover, bottomEdge);
        positionsByFunction.put(vMoverBackwards, topEdge);
        positionsByFunction.put(diagUpRightMover, leftBottom);
        positionsByFunction.put(diagDownLeftMover, rightTop);
        positionsByFunction.put(diagUpLeftMover, rightBottom);
        positionsByFunction.put(diagDownRightMover, leftTop);

        Map<Integer, Integer> mapForDistancesFromCentreSpace = new HashMap<>();
        int maxDistanceFromCentre = Math.floorDiv(boardWidth, 2);
        if(boardWidth % 2 == 0) {
            maxDistanceFromCentre--;
        }

        for(int i = Math.floorDiv(boardWidth, 2) -1; i>= 0 ; i--){
            mapForDistancesFromCentreSpace.put(i, maxDistanceFromCentre+1);
            maxDistanceFromCentre--;
        }
        maxDistanceFromCentre = Math.floorDiv(boardWidth, 2);
        for(int i=Math.floorDiv(boardWidth, 2); i <= boardWidth-1; i++){
            mapForDistancesFromCentreSpace.put(i,maxDistanceFromCentre);
            maxDistanceFromCentre--;
        }
        distanceFromCentreSpaces = mapForDistancesFromCentreSpace;
    }

    private <T> List<T> returnsTheSeverityOfTheirBestMoveBasedOnOurMove(Board boardAfterOurMove, Counter theirCounter){
        return binaryOutcomeOfMoveMade(boardAfterOurMove, theirCounter);
    }

    private <T> List<T> returnsTheOutcomeOnMostInARowIfWeMakeAMove(Board boardAfterTheirMove, Counter ourCounter){
        return binaryOutcomeOfMoveMade(boardAfterTheirMove, ourCounter);
    }

    private <T> List<T> binaryOutcomeOfMoveMade(Board boardAfterLastMove, Counter ourCounter) {
        int mostHad = 0;
        List dataOfOutcome = new ArrayList<>(2);
        dataOfOutcome.add(0);
        dataOfOutcome.add(boardAfterLastMove);
        for(int i=0; i < boardWidth; i++){
            try{
                Board boardAfterTheMove = new Board(boardAfterLastMove, i, ourCounter);
                Map<Counter, Integer> currentGameStateMap = calculateGameState(boardAfterTheMove).getMaxInARowByCounter();
                int mostHadFromThisSpace = currentGameStateMap.get(ourCounter);
                if(mostHadFromThisSpace > mostHad){
                    dataOfOutcome.set(0, mostHadFromThisSpace);
                    dataOfOutcome.set(1, boardAfterTheMove);
                    mostHad = mostHadFromThisSpace;
                }
            }
            catch(InvalidMoveException e){
            }
        }
        return dataOfOutcome;
    }

    private int returnsBinaryValueOfOurMoveForAGivenX(int potentialSpaceWeCanTake, Board board, Counter counter){
        int theBinaryValueOfThisChaos;
        try {
            Board boardAfterOurMove = new Board(board, potentialSpaceWeCanTake, counter);
            Map<Counter, Integer> currentGameStateMap = calculateGameState(board).getMaxInARowByCounter();
            int mostWeWouldHave = currentGameStateMap.get(counter);
            List outcomeOfTheirMove = returnsTheSeverityOfTheirBestMoveBasedOnOurMove(boardAfterOurMove, counter.getOther());
            int mostTheyCouldGetInTheirNextMove = (int) outcomeOfTheirMove.get(0);
            int distanceFromCentreValue = distanceFromCentreSpaces.get(potentialSpaceWeCanTake);
            theBinaryValueOfThisChaos = (3*mostWeWouldHave) - (2*mostTheyCouldGetInTheirNextMove)+ distanceFromCentreValue;

        }
        catch(InvalidMoveException e){
            theBinaryValueOfThisChaos = Integer.MIN_VALUE;
        }

        return theBinaryValueOfThisChaos;
    }

    public List<Integer> returnsXValueForOurBestMove(Board board, Counter counter){
        Map<Integer, Integer> xAndCorrespondingBinaryValue = new HashMap<>(boardWidth);
        for(int i=0; i<boardWidth; i++){
            xAndCorrespondingBinaryValue.put(i, returnsBinaryValueOfOurMoveForAGivenX(i, board, counter));
        }
        System.out.println(xAndCorrespondingBinaryValue);
        List<Map.Entry<Integer, Integer>> valueToUse = xAndCorrespondingBinaryValue.entrySet().stream().toList();
        int currentMaxInt = Integer.MIN_VALUE;
        int xValueToUse = Math.floorDiv(boardWidth, 2);
        List<Integer> xValuesWhichCanBeUsed = new ArrayList<>();
        for(int i=0; i<valueToUse.size(); i++) {
            if(valueToUse.get(i).getValue() > currentMaxInt) {
                currentMaxInt = valueToUse.get(i).getValue();
                xValueToUse = valueToUse.get(i).getKey();
                xValuesWhichCanBeUsed = new ArrayList<>();
                xValuesWhichCanBeUsed.add(xValueToUse);
            }
            else if(valueToUse.get(i).getValue().equals(currentMaxInt)){
                xValuesWhichCanBeUsed.add(valueToUse.get(i).getKey());
            }
        }


        return xValuesWhichCanBeUsed;
    }


    public GameState calculateGameState(Board board) {
        List<BoardLine> lines = getLines(board);
        Map<Counter, Integer> bestRunByColour = new HashMap<>();
        for (BoardLine line : lines) {
            Map<Counter, Integer> bestRunInLine = getBestRunByColour(line);
            bestRunByColour = maxMap(bestRunInLine, bestRunByColour);
            line.returnToBeginning();
        }
        boolean boardFull = isBoardFull(board);
        return new GameState(bestRunByColour, board.getConfig(), boardFull);
    }

    private boolean nMinusXInARow(Counter counter, Board board, int X) {
        return calculateGameState(board).getMaxInARowByCounter().get(counter) >= X;
    }

    public List<BoardLine> nMinusXLines(Counter counter, Board board, int X) {
        List<BoardLine> linesWithNMinus1 = new ArrayList<>();
        if (nMinusXInARow(counter, board, X)) {
            List<BoardLine> lines = getLines(board);
            for (BoardLine line : lines) {
                if (getBestRunByColour(line).get(counter) >= X){
                    line.returnToBeginning();
                    linesWithNMinus1.add((BoardLine) line);
                }
            }
        }
        return linesWithNMinus1;
    }

    public List<Position> returnListOfPositionsForAWinCase(Counter counterToTest, Board currentBoard){
        List<BoardLine>  linesWithNMinus2 = nMinusXLines(counterToTest, currentBoard, currentBoard.getConfig().getnInARowForWin()-2);
        List<Position> positionsOfWinningBoard = new ArrayList<>();
        linesWithNMinus2.forEach(line -> {
            while(line.hasNext() && !line.isCounterInCurrentPosition() || line.getCounterInCurrentPosition() != counterToTest) {
                line.next();
            }
            Integer numberInARow = 1;

            while(line.hasNext()){
                line.next();
                if(line.isCounterInCurrentPosition()){
                    if(line.getCounterInCurrentPosition().equals(counterToTest)){
                        numberInARow++;
                    }
                    else{
                        numberInARow = 0;

                    }

                }
                else if(numberInARow.equals(currentBoard.getConfig().getnInARowForWin()-1) && !currentBoard.hasCounterAtPosition(line.getCurrentPosition()) && currentBoard.isWithinBoard(line.getCurrentPosition())){
                    positionsOfWinningBoard.add(line.getCurrentPosition());
                    numberInARow=0;

                }
                else if(numberInARow.equals(currentBoard.getConfig().getnInARowForWin()-2) && !currentBoard.hasCounterAtPosition(line.getCurrentPosition())){
                    Position blankSpacePotentiallyWinning = line.getCurrentPosition();
                    line.next();
                    if(line.isCounterInCurrentPosition() && line.getCounterInCurrentPosition() == counterToTest && currentBoard.hasCounterAtPosition(line.getCurrentPosition()) && currentBoard.isWithinBoard(line.getCurrentPosition())){
                        positionsOfWinningBoard.add(blankSpacePotentiallyWinning);
                    }
                }
            }

        });


        System.out.println(positionsOfWinningBoard.size() + " winning spaces");
        System.out.println(positionsOfWinningBoard);


        return positionsOfWinningBoard;
    }

    public List<Position> returnBlackListOfPositions(Counter theirCounter, Board board){
        List<Position> theirWinningPositions = returnListOfPositionsForAWinCase(theirCounter, board);
        List<Position> blackList = new ArrayList<>();
        for (Position position: theirWinningPositions) {
            blackList.add(new Position(position.getX(), position.getY() -1));
        }
        return blackList;
    }

    public boolean winningPositionExists(Counter counter, Board board){
        for (int i=0; i<board.getConfig().getWidth();i++) {
            try {
                Board futureBoard = new Board(board, i, counter);
                if (calculateGameState(futureBoard).isWin()) {
                    return true;
                }
            } catch(InvalidMoveException e){
            }
        }
        return false;
    }

    public int winningPosition(Counter counter, Board board){
        for (int i=0; i<board.getConfig().getWidth();i++) {
            try {
                Board futureBoard = new Board(board, i, counter);
                if (calculateGameState(futureBoard).isWin()) {
                    return i;
                }
            } catch (InvalidMoveException e){}
        }
        return -1;
    }

    private Map<Counter, Integer> maxMap(Map<Counter, Integer> map1, Map<Counter, Integer> map2) {
        HashMap<Counter, Integer> maxMap = new HashMap<>();
        for (Map.Entry<Counter, Integer> entry : map1.entrySet()) {
            maxMap.put(entry.getKey(), Math.max(entry.getValue(), map2.getOrDefault(entry.getKey(), 0)));
        }
        return maxMap;
    }

    private boolean isBoardFull(Board board) {
        return IntStream.range(0, board.getConfig().getWidth())
                .allMatch(
                        i -> board.hasCounterAtPosition(new Position(i, board.getConfig().getHeight() - 1)));
    }

    private List<BoardLine> getLines(Board board) {
        ArrayList<BoardLine> lines = new ArrayList<>();
        for (Map.Entry<Function<Position, Position>, List<Position>> entry : positionsByFunction
                .entrySet()) {
            Function<Position, Position> function = entry.getKey();
            List<Position> startPositions = entry.getValue();
            lines.addAll(startPositions.stream().map(p -> new BoardLine(board, p, function))
                    .collect(Collectors.toList()));
        }
        return lines;
    }

    private List<BoardLine> getColumnLines(Board board) {
        ArrayList<BoardLine> lines = new ArrayList<>();
        Function<Position, Position> function = vMover;
        List<Position> startPositions = positionsByFunction.get(function);
        lines.addAll(startPositions.stream().map(p -> new BoardLine(board, p, function))
                .collect(Collectors.toList()));
        return lines;
    }

    public List<Position> getNextPositions(Board board){
        List<BoardLine> columns = getColumnLines(board);
        List<Position> positionsForNextIndex = new ArrayList<>();
        columns.forEach(column -> {
            boolean notYetFound = true;
            if(!column.isCounterInCurrentPosition()){
                positionsForNextIndex.add(column.getCurrentPosition());
                notYetFound = false;
            }
            while(column.hasNext() && notYetFound){
                column.next();
                if(!column.isCounterInCurrentPosition()){
                    positionsForNextIndex.add(column.getCurrentPosition());
                    notYetFound = false;
                }
            }
        });

        return positionsForNextIndex;
    }

    private Map<Counter, Integer> getBestRunByColour(Line line) {
        HashMap<Counter, Integer> bestRunByColour = new HashMap<>();
        for (Counter c : Counter.values()) {
            bestRunByColour.put(c, 0);
        }
        Counter current = null;
        int currentRunLength = 0;
        while (line.hasNext()) {
            Counter next = line.next();
            if (current != next) {
                if (current != null) {
                    if (Math.max(currentRunLength, 1) > bestRunByColour.get(current)) {
                        bestRunByColour.put(current, Math.max(currentRunLength, 1));
                    }
                }
                currentRunLength = 1;
                current = next;
            } else {
                currentRunLength++;
            }
        }
        if (current != null && Math.max(currentRunLength, 1) > bestRunByColour.get(current)) {
            bestRunByColour.put(current, Math.max(currentRunLength, 1));
        }
        return bestRunByColour;
    }
}