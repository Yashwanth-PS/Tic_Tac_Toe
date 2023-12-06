package strategies.WinningStrategy;

import models.Board;
import models.Move;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements WinningStrategy {
    private int dimension;
    private int symbolsAdded;
    private List<HashMap<Character, Integer>> rowSymbolCount = new ArrayList<>();
    private List<HashMap<Character, Integer>> colSymbolCount = new ArrayList<>();
    private HashMap<Character, Integer> topLeftDiagonalSymbolCount = new HashMap<>();
    private HashMap<Character, Integer> bottomLeftDiagonalSymbolCount = new HashMap<>();
    private HashMap<Character, Integer> cornerSymbolCount = new HashMap<>();

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        for (int i = 0; i < dimension; i++) {
            rowSymbolCount.add(new HashMap<>()); // rowSymbolCount --> [{}, {}, {}]
            colSymbolCount.add(new HashMap<>());
        }
    }

    public boolean isCellTopLeftDiagonal(int row, int col) {
        return row == col;
    }

    public boolean isCellBottomLeftDiagonal(int row, int col) {
        return (row + col) == (dimension - 1);
    }

    public boolean isCornerCell(int row, int col) {
        // 0, 0 || 0, dim - 1 || dim - 1, 0 || dim - 1, dim - 1
        if (row == 0 || row == (dimension - 1))
            return (col == 0 || col == (dimension - 1));
        return false;
    }

    @Override
    public Player checkWinner(Board board, Move lastMove) {
        symbolsAdded++;
        Player lastMovePlayer = lastMove.getPlayer();
        char symbol = lastMove.getPlayer().getSymbol().getSymbolChar();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();

        if(checkForRowWins(row, col, symbol, lastMove) != null)
            return lastMovePlayer;
        else if(checkForColumnWins(row, col, symbol, lastMove) != null)
            return lastMovePlayer;
        else if(checkForDiagonalWins(row, col, symbol, lastMove) != null)
            return lastMovePlayer;
        else if(checkForCornerWins(row, col, symbol, lastMove) != null)
            return lastMovePlayer;

        if(symbolsAdded == (dimension * dimension)) {
            board.printBoard();
            throw new GameDrawnException("Game is drawn as cells are full");
        }
        return null;
    }

    private Player checkForRowWins(int row, int col, char symbol, Move lastMove) {
        // Logic for row HashMap updates and count
        if (!rowSymbolCount.get(row).containsKey(symbol))
            rowSymbolCount.get(row).put(symbol, 0);
        rowSymbolCount.get(row).put(symbol, rowSymbolCount.get(row).get(symbol) + 1);
        // Winning by same symbol across a row
        if (rowSymbolCount.get(row).get(symbol) == dimension)
            return lastMove.getPlayer();
        return null;
    }

    private Player checkForColumnWins(int row, int col, char symbol, Move lastMove) {
        // Logic for column HashMap updates and count
        if (!colSymbolCount.get(col).containsKey(symbol))
            colSymbolCount.get(col).put(symbol, 0);
        colSymbolCount.get(col).put(symbol, colSymbolCount.get(col).get(symbol) + 1);
        // Winning by same symbol across a column
        if (colSymbolCount.get(col).get(symbol) == dimension)
            return lastMove.getPlayer();
        return null;
    }

    private Player checkForDiagonalWins(int row, int col, char symbol, Move lastMove) {
        // Check for Diagonal HashMaps
        if (isCellTopLeftDiagonal(row, col)) {
            if (!topLeftDiagonalSymbolCount.containsKey(symbol))
                topLeftDiagonalSymbolCount.put(symbol, 0);
            topLeftDiagonalSymbolCount.put(symbol, topLeftDiagonalSymbolCount.get(symbol) + 1);
            // Winning by same symbol across the topLeftDiagonal
            if (topLeftDiagonalSymbolCount.get(symbol) == dimension)
                return lastMove.getPlayer();
        }

        if (isCellBottomLeftDiagonal(row, col)) {
            if (!bottomLeftDiagonalSymbolCount.containsKey(symbol))
                bottomLeftDiagonalSymbolCount.put(symbol, 0);
            bottomLeftDiagonalSymbolCount.put(symbol, bottomLeftDiagonalSymbolCount.get(symbol) + 1);
            // Winning by same symbol across the bottomLeftDiagonal
            if (bottomLeftDiagonalSymbolCount.get(symbol) == dimension)
                return lastMove.getPlayer();
        }
        return null;
    }

    private Player checkForCornerWins(int row, int col, char symbol, Move lastMove) {
        // Check for Corners
        if (isCornerCell(row, col)) {
            if (!cornerSymbolCount.containsKey(symbol))
                cornerSymbolCount.put(symbol, 0);
            cornerSymbolCount.put(symbol, cornerSymbolCount.get(symbol) + 1);
            // Winning by same symbol in all the 4 Corners
            if (cornerSymbolCount.get(symbol) == 4)
                return lastMove.getPlayer();
        }
        return null;
    }
}

// TODO:
// Implement 4 Classes for Winning Strategy
// 1] Row Winning Strategy
// 2] Column Winning Strategy
// 3] Diagonal Winning Strategy --> Top Left, Bottom Left
// 4] Corner Winning Strategy