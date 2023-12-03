package models;

import models.constants.PlayerType;

import java.util.Scanner;

public class Player {
    private static int idCounter = 0;
    private int id;
    private Symbol symbol;
    private String name;
    private PlayerType playerType;
    private Scanner scanner;

    public Player(Symbol symbol, String name, PlayerType playerType) {
        this.id = idCounter++;
        this.symbol = symbol;
        this.name = name;
        this.playerType = playerType;
        this.scanner = new Scanner(System.in);
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}

/* TO-DO code implementation and move validation, and checks for the following scenarios:
Validate the input row and column to be within the board boundaries.
Check if the selected cell is empty before making the move.
If the selected cell is not empty, display an error message
Ask the player to enter valid coordinates again.
You can create a custom exception (e.g., InvalidMoveException) to handle these validation errors. */