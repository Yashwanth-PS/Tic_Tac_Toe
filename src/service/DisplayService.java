package service;

import models.Player;
import models.constants.CellState;

public class DisplayService {
    Player player;
    CellState cellState;

    public DisplayService(Player player, CellState cellState) {
        this.player = player;
        this.cellState = cellState;
    }

    public void display(){
        if(player == null){
            System.out.print("| |");
        } else if(cellState.equals(CellState.BLOCKED)){
            System.out.print("||||");
        } else{
            System.out.print("|" + player.getSymbol().getSymbolChar() + "|");
        }
    }
}
