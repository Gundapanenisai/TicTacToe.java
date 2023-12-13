/**
 * MET CS 664-A1 Fall 2023
 * Artificial Intelligence In Class Lab #1: Tic Tac Toe
 *
 * This program creates a Tic Tac Toe game where the player can play against
 * the AI which uses the Minimax algorithm, ensuring that it cannot be beaten.
 *
 * Course Instructor:
 * - Your Instructor's Name (If you wish to include)
 *
 * Participants:
 * - saibharg@bu.edu
 * - otaibiga@bu.edu
 * - safdarmd@bu.edu
 * - Aziz696@bu.edu
 * - ...
 */

import java.util.Scanner;

public class TicTacToe {

    public static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY = ' ';

    public static void main(String[] args) {
        // Initialize the board with empty spaces
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        Scanner scanner = new Scanner(System.in);

        // Game loop
        while (true) {
            printBoard();

            // Player's move
            playerMove(scanner);

            // Check for player's win
            if (checkWinner(PLAYER_X)) {
                printBoard();
                System.out.println("Player X wins!");
                break;
            }

            // Check for draw
            if (isFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            // AI's move
            aiMove();

            // Check for AI's win
            if (checkWinner(PLAYER_O)) {
                printBoard();
                System.out.println("Player O wins!");
                break;
            }
        }
        scanner.close();
    }

    private static void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j != SIZE - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i != SIZE - 1) System.out.println("---------");
        }
    }

    private static void playerMove(Scanner scanner) {
        int row, col;
        do {
            System.out.println("Enter row (0-2) and column (0-2) separated by space for your X move: ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != EMPTY);
        board[row][col] = PLAYER_X;
    }

    private static boolean checkWinner(char player) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1, bestCol = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;
                    int moveScore = minimax(false);
                    board[i][j] = EMPTY;
                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        board[bestRow][bestCol] = PLAYER_O;
    }

    private static int minimax(boolean isMaximizing) {
        if (checkWinner(PLAYER_O)) return 1;
        if (checkWinner(PLAYER_X)) return -1;
        if (isFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_O;
                        bestScore = Math.max(bestScore, minimax(false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_X;
                        bestScore = Math.min(bestScore, minimax(true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return bestScore;
        }
    }
}
