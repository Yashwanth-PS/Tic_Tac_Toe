package service;

import models.Cell;
import models.Move;

public class PlayerActions {
    // TO-DO: validate the move and throw exception and Handle it.
    public Move makeMove(Board board) {
        while (true) {  // This loop will keep asking the user for row and column values until they enter a valid move.
            try {
                System.out.println(this.getName() + ", Please enter the row for the move");
                int row = scanner.nextInt();
                System.out.println(this.getName() + ", Please enter the column for the move");
                int col = scanner.nextInt();

                // Validate the move and throw an exception if it's invalid
                if (row < 0 || row >= board.getSize() || col < 0 || col >= board.getSize()) {
                    throw new InvalidMoveException("Invalid move: Row and column must be within the board boundaries.");
                }

                if (board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)) {
                    board.getBoard().get(row).get(col).setPlayer(this);
                    board.getBoard().get(row).get(col).setCellState(CellState.FILLED);
                    // Cell cell = new Cell(row, col, this);
                    // Move move = new Move(cell, this);
                    return new Move(new Cell(row, col, this), this);
                } else {
                    throw new InvalidMoveException("Invalid move: The selected cell is already filled.");
                }
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                // Clear the input buffer before asking for new input to prevent any input-related issues during repeated inputs
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input: Please enter valid integer values for row and column.");
                // Clear the input buffer before asking for new input to prevent any input-related issues during repeated inputs
                scanner.nextLine();
            }
        }
    }
}
