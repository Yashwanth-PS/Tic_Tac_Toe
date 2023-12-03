package service;

import models.Board;
import models.Cell;
import models.Move;
import models.Player;
import models.constants.CellState;

import java.util.Scanner;

public class MoveService {
    // TO-DO: validate the move and throw exception and Handle it.
    Scanner scanner = new Scanner(System.in);
    public Move makeMove(Board board, Player player) {
        while (true) {  // This loop will keep asking the user for row and column values until they enter a valid move.
            try {
                System.out.println(player.getName() + ", Please enter the row for the move");
                int row = scanner.nextInt();
                System.out.println(player.getName() + ", Please enter the column for the move");
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

/* TO-DO code implementation and move validation, and checks for the following scenarios:
Validate the input row and column to be within the board boundaries.
Check if the selected cell is empty before making the move.
If the selected cell is not empty, display an error message
Ask the player to enter valid coordinates again.
You can create a custom exception (e.g., InvalidMoveException) to handle these validation errors. */