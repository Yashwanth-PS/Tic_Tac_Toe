package service;

import models.Player;
import models.constants.CellState;

public class DisplayService {

    public void display(CellState cellState){
        if(player == null){
            System.out.print("| |");
        } else if(cellState.equals(CellState.BLOCKED)){
            System.out.print("||||");
        } else{
            System.out.print("|" + player.getSymbol().getSymbolChar() + "|");
        }
    }
}
