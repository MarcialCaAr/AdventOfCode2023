package librerias;

import java.util.ArrayList;

public class PrintingFormats {
    /**
    * Prints the board horizontally. This method is used to debug the performance of the game. If you want to see the results in a human readable format use printBoard ()
    * 
    * @param board - the board to print
    */
    public static void printBoardHorizontal(Object[][] board) {
        // Prints all the data in the board
        for (int i = 0; i < board.length; i++) {
            // Prints the board to standard output.
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "");
            }
            System.out.println();
        }
    }

    public static void printBoardHorizontal(ArrayList<ArrayList<Character>> board) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                System.out.print(board.get(i).get(j) + "");
            }
            System.out.println();
        }
    }
    public static void printBoardHorizontal(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "");
            }
            System.out.println();
        }
    }

    public static void printBoardHorizontal(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "");
            }
            System.out.println();
        }
    }


    public static void printBoardVertical(Object[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length; i++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
