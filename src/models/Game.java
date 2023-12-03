package models;

import Tic_Tac_Toe.Exception.DuplicateSymbolException;
import Tic_Tac_Toe.Exception.InvalidBotCountException;
import Tic_Tac_Toe.Exception.InvalidDimensionException;
import Tic_Tac_Toe.Exception.InvalidNumberOfPlayersException;
import Tic_Tac_Toe.Strategies.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(List<Player> players, Board board, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.board = board;
        this.moves = new ArrayList<Move>();
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Player getWinner() {
        return winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;

        private Builder(){
            this.players = new ArrayList<Player>();
            this.winningStrategies = new  ArrayList<WinningStrategy>();
            this.dimension = 0;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setwinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public void addPlayer(Player player){
            players.add(player);
        }

        public void addwinningStrategy(WinningStrategy winningStrategy){
            winningStrategies.add(winningStrategy);
        }

        private void validateBotCounts(){
            int botCount = 0;
            for(Player player : players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount++;
                }
            }
            if(botCount > 1){
                // Throw an exception
                throw new InvalidBotCountException("Bot count has exceeded 1");
            }
        }

        private void validateDimension(){
            if(dimension < 3 || dimension > 10) // 3 to 10 is the allowed range
                throw new InvalidDimensionException("Dimension should be more than 2 and less than 11");
        }

        private void validateNumberOfPlayers(){
            if(players.size() != dimension - 1){
                throw new InvalidNumberOfPlayersException("Players should be 1 less than the dimension");
            }
        }

        private void validateUniqueSymbolForAllPlayers(){
            HashSet<Character> set = new HashSet<>();
            for(Player player : players){
                set.add(player.getSymbol().getSymbolChar());
            }
            if(set.size() != players.size()){
                throw new DuplicateSymbolException("Every player should have unique Symbol");
            }
        }

        private void validate(){
            validateBotCounts();
            validateDimension();
            validateNumberOfPlayers();
            validateUniqueSymbolForAllPlayers();
        }

        public Game build(){
            validate();
            return new Game(players, new Board(dimension), winningStrategies);
        }
    }
}
