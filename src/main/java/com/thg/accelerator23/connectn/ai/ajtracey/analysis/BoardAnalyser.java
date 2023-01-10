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
    private Function<Position, Position> vMover = p -> new Position(p.getX(), p.getY() + 1);
    private Function<Position, Position> diagUpRightMover = hMover.compose(vMover);
    private Function<Position, Position> diagUpLeftMover =
            p -> new Position(p.getX() - 1, p.getY() + 1);
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

        List<Position> leftBottom = Stream.concat(leftEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());
        List<Position> rightBottom = Stream.concat(rightEdge.stream(),
                bottomEdge.stream()).distinct().collect(Collectors.toList());

        positionsByFunction.put(hMover, leftEdge);
        positionsByFunction.put(vMover, bottomEdge);
        positionsByFunction.put(diagUpRightMover, leftBottom);
        positionsByFunction.put(diagUpLeftMover, rightBottom);

        Map<Integer, Integer> mapForDistancesFromCentreSpace = new HashMap<>();
        int maxDistanceFromCentre = Math.floorDiv(boardWidth, 2);
        if(boardWidth % 2 == 0) {
            maxDistanceFromCentre--;
        }
        for(int i = Math.floorDiv(boardWidth, 2) -1; i>= 0 ; i--){
            mapForDistancesFromCentreSpace.put(i, maxDistanceFromCentre-i);
        }
        for(int i=Math.floorDiv(boardWidth, 2); i <= boardWidth-1; i++){
            mapForDistancesFromCentreSpace.put(i,maxDistanceFromCentre);
            maxDistanceFromCentre --;
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
            theBinaryValueOfThisChaos = mostWeWouldHave - mostTheyCouldGetInTheirNextMove + distanceFromCentreValue;

        }
        catch(InvalidMoveException e){
            theBinaryValueOfThisChaos = Integer.MIN_VALUE;
        }

        return theBinaryValueOfThisChaos;
    }

    public int returnsXValueForOurBestMove(Board board, Counter counter){
        Map<Integer, Integer> xAndCorrespondingBinaryValue = new HashMap<>(boardWidth);
        for(int i=0; i<boardWidth; i++){
            xAndCorrespondingBinaryValue.put(i, returnsBinaryValueOfOurMoveForAGivenX(i, board, counter));
        }
        List<Map.Entry<Integer, Integer>> valueToUse = xAndCorrespondingBinaryValue.entrySet().stream().toList();
        int currentMaxInt = 0;
        int xValueToUse = Math.floorDiv(boardWidth, 2);
        for(int i=0; i<valueToUse.size(); i++) {
            if(valueToUse.get(i).getValue() >= currentMaxInt) {
                currentMaxInt = valueToUse.get(i).getValue();
                xValueToUse = valueToUse.get(i).getKey();
            }
        }

        return xValueToUse;
    }


//
//    public List<Position> checkWithinColumns(Board board, Counter counter, int numberInARowToAddPositionsOf){
//        ArrayList<> verticalLines = new ArrayList<>();
//
//        Function<Position, Position> functionToMoveHorizontally = hMover;
//        List<Position> startPosition = positionsByFunction.get(hMover);
//
//        verticalLines.addAll(startPosition.stream()
//                                          .map(p -> new BoardLine(board, p, functionToMoveHorizontally))
//                                          .map(boardLine -> {
//
//                                              Map<Counter, List<Position>> listOfCounterPositionMap = new HashMap<>();
//                                              Map<Counter, Integer> counterOccuranceMap = new HashMap<>();
//                                              while (boardLine.hasNext()){
//                                                  Position currentPosition = boardLine.getCurrentPosition();
//                                                  Counter currentCounter = boardLine.next();
//                                                  if(!listOfCounterPositionMap.containsKey(currentCounter)){
//                                                      listOfCounterPositionMap.put(currentCounter, new ArrayList<>()).add(currentPosition);
//                                                      counterOccuranceMap.put(currentCounter, 1);
//                                                  }
//                                                  else{
//                                                      listOfCounterPositionMap.get(currentCounter).add(currentPosition);
//                                                      counterOccuranceMap.replace(currentCounter, counterOccuranceMap.get(counter) + 1);
//                                                  }
//                                              }
//
//                                              return //;
//                                          })
//                                          .collect(Collectors.toList()));
//
//    }

    public GameState calculateGameState(Board board) {
        List<Line> lines = getLines(board);
        Map<Counter, Integer> bestRunByColour = new HashMap<>();
        for (Line line : lines) {
            Map<Counter, Integer> bestRunInLine = getBestRunByColour(line);
            bestRunByColour = maxMap(bestRunInLine, bestRunByColour);
        }
        boolean boardFull = isBoardFull(board);
        return new GameState(bestRunByColour, board.getConfig(), boardFull);
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

    private List<Line> getLines(Board board) {
        ArrayList<Line> lines = new ArrayList<>();
        for (Map.Entry<Function<Position, Position>, List<Position>> entry : positionsByFunction
                .entrySet()) {
            Function<Position, Position> function = entry.getKey();
            List<Position> startPositions = entry.getValue();
            lines.addAll(startPositions.stream().map(p -> new BoardLine(board, p, function))
                    .collect(Collectors.toList()));
        }
        return lines;
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