package service;

import models.Board;
import models.Cell;

import java.util.ArrayList;
import java.util.List;

public class BoardService {
    DisplayService displayService = new DisplayService();
    public void initialiseBoard(Board board, int size) {
        board.setSize(size);   // Example: Size --> 3
        board.setCells(new ArrayList<>()); // []
        for(int i = 0; i < size; i++){
            board.getBoard().add(new ArrayList<>());     // [[] [] []]
            for(int j = 0; j < size; j++)
                board.getBoard().get(i).add(new Cell(i, j));
                /* [ [_, _, _],
                     [_, _, _],
                     [_, _, _] ] */
        }
    }

    public void printBoard(Board board){
        for(int i = 0; i < board.getSize(); i++){
            List<Cell> row = board.getBoard().get(i);
            for(int j = 0; j < board.getSize(); j++){
                displayService.display(row.get(j).getCellState());
            }
            System.out.println();
        }
    }
}
