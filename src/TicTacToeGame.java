import controller.GameController;
import models.*;
import models.constants.BotDifficultyLevel;
import models.constants.CellState;
import models.constants.GameState;
import models.constants.PlayerType;
import strategies.BotPlayingStrategy.BotPlayingStrategyFactory;

import java.util.*;

public class TicTacToeGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameController gameController = new GameController();

        System.out.println("Please enter the dimension of the Game --> ");
        int dimension = sc.nextInt();

        System.out.println("Will there be any Bot in the Game?: Y/N --> ");
        String isBotPresent = sc.next();

        List<Player> players = new ArrayList<>();
        int iteratorNumber = dimension - 1;

        if (isBotPresent.equals("Y"))
            iteratorNumber = dimension - 2;

        Set<Character> usedSymbols = new HashSet<>();
        for (int i = 0; i < iteratorNumber; i++) {
            System.out.println("What is the Name of the player number : " + (i + 1));
            String playerName = sc.next();

            // TO-DO : validate if no one passes a duplicate symbol
            System.out.println("What is the Charachter Symbol of the player number : " + (i + 1));
            String charachterSymbol = sc.next();
            char symbolChar = charachterSymbol.charAt(0);
            Symbol symbol = new Symbol(symbolChar);

            while (usedSymbols.contains(symbolChar)) {
                System.out.println("The symbol " + symbolChar + " is already used by another player. Please choose a different symbol.");
                System.out.println("What is the Charachter Symbol of the player number : " + (i + 1));
                charachterSymbol = sc.next();
                symbolChar = charachterSymbol.charAt(0);
                symbol = new Symbol(symbolChar);
            }

            usedSymbols.add(symbolChar);
            players.add(new Player(symbol, playerName, PlayerType.HUMAN));
        }

        if(isBotPresent.equals("Y")){
            System.out.println("What is the Name of the BOT : ");
            String botName = sc.next();

            System.out.println("What is the Charachter Symbol of the BOT : ");
            String charachterSymbol = sc.next();

            //TO-DO: take user input for bot difficulty level and create the object accordingly

            System.out.println("Choose Bot Difficulty Level:");
            System.out.println("1. EASY");
            System.out.println("2. MEDIUM");
            System.out.println("3. HARD");
            int botDifficultyChoice = sc.nextInt();

            BotDifficultyLevel difficultyLevel = switch (botDifficultyChoice) {
                case 2 -> BotDifficultyLevel.MEDIUM;
                case 3 -> BotDifficultyLevel.HARD;
                default -> BotDifficultyLevel.EASY;
            };

            Bot bot = new Bot(new Symbol(charachterSymbol.charAt(0)),
                    botName,
                    difficultyLevel,
                    BotPlayingStrategyFactory.getBotPlayingStrategyForDifficultyLevel(difficultyLevel));
            players.add(bot);
        }

        // Randomize the players in the List
        Collections.shuffle(players);

        Game game = gameController.createGame(dimension, players);
        int playerIndex = 0;

        // Create a stack to store the moves
        Stack<Move> moveStack = new Stack<>();
        // TO-DO: optimise the while loop and handle the exception gracefully
        while(game.getGameState().equals(GameState.IN_PROGRESS)) {
            try {
                System.out.println("CURRENT BOARD STATUS");
                gameController.displayBoard(game);
                playerIndex++;
                playerIndex = playerIndex % players.size();
                Move movePlayed = gameController.executeMove(game, players.get(playerIndex));

                // Store the move on the stack before applying it
                moveStack.push(movePlayed);

                Player winner = gameController.checkWinner(game, movePlayed);
                if(winner != null){
                    gameController.displayBoard(game);
                    System.out.println("Winner is : " + winner.getName());
                    break;
                }

                // Implement the logic for undo
                System.out.println("Do you want to undo your last move? (Y/N)");
                String undoChoice = sc.next();

                if (undoChoice.equalsIgnoreCase("Y")) {
                    if (!moveStack.isEmpty()) {
                        Move lastMove = moveStack.pop();
                        Cell lastCell = lastMove.getCell();
                        game.getBoard().getBoard().get(lastCell.getRow()).get(lastCell.getCol()).setCellState(CellState.EMPTY);
                        game.getBoard().getBoard().get(lastCell.getRow()).get(lastCell.getCol()).setPlayer(null);
                        playerIndex = (playerIndex - 1 + players.size()) % players.size();
                    } else {
                        System.out.println("Cannot undo. No moves to undo.");
                    }
                }

                // TODO : write logic for each player option to play
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                // Clear the input buffer before asking for new input
                sc.nextLine();
            }
        }
    }
}