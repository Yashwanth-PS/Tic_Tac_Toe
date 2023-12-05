package strategies.BotPlayingStrategy;

import models.Board;
import models.Move;
import models.Player;

public interface BotPlayingStrategy {
    Move makeMove(Player player, Board board);
}
