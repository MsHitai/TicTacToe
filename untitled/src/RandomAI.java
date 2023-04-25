import java.util.Random;
import java.util.Scanner;

public class RandomAI {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        char[][] board = {{' ', ' ', ' '},
                          {' ', ' ', ' '},
                          {' ', ' ', ' '}};

        printBoard(board);

        while(true) {
            playerMove(board, scanner);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);
            computerMove(board);
            if (isGameFinished(board)) {
                break;
            }
            printBoard(board);
        }

        scanner.close();
    }

    private static boolean isGameFinished(char[][] board) {
        if (checkIfFull(board)) {
            printBoard(board);
            System.out.println("Ничья!");
            return true;
        } else if (whoWon(board, 'X')) {
            printBoard(board);
            System.out.println("Вы выиграли!");
            return true;
        } else if (whoWon(board, 'O')) {
            printBoard(board);
            System.out.println("Вы проиграли!");
            return true;
        }
        return false;
    }

    private static boolean whoWon(char[][] board, char symbol) {
        return (board[0][0] == symbol) && (board[0][1] == symbol) && (board[0][2] == symbol) ||
                (board[1][0] == symbol) && (board[1][1] == symbol) && (board[1][2] == symbol) ||
                (board[2][0] == symbol) && (board[2][1] == symbol) && (board[2][2] == symbol) ||

                (board[0][0] == symbol) && (board[1][0] == symbol) && (board[2][0] == symbol) ||
                (board[0][1] == symbol) && (board[1][1] == symbol) && (board[2][1] == symbol) ||
                (board[0][2] == symbol) && (board[1][2] == symbol) && (board[2][2] == symbol) ||

                (board[0][0] == symbol) && (board[1][1] == symbol) && (board[2][2] == symbol) ||
                (board[0][2] == symbol) && (board[1][1] == symbol) && (board[2][0] == symbol);
    }

    private static boolean checkIfFull(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                if (aChar == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void computerMove(char[][] board) {
        Random random = new Random();
        int computerMove;
        do {
            computerMove = random.nextInt(9) + 1;
        } while (!isValidMove(board, String.valueOf(computerMove)));
        System.out.println("Противник выбрал позицию " + computerMove);
        placeMove(board, String.valueOf(computerMove), 'O');
    }

    private static Boolean isValidMove(char[][] board, String position) {
        switch (position) {
            case "1":
                return board[0][0] == ' ';
            case "2":
                return board[0][1] == ' ';
            case "3":
                return board[0][2] == ' ';
            case "4":
                return board[1][0] == ' ';
            case "5":
                return board[1][1] == ' ';
            case "6":
                return board[1][2] == ' ';
            case "7":
                return board[2][0] == ' ';
            case "8":
                return board[2][1] == ' ';
            case "9":
                return board[2][2] == ' ';
            default:
                return false;
        }
    }
    private static void playerMove(char[][] board, Scanner scanner) {
        String userInput;
        while (true) {
            System.out.println("Где вы хотите поставить крестик? (1-9)");
            userInput = scanner.nextLine();
            if (isValidMove(board, userInput)) {
                break;
            } else {
                System.out.println(userInput + " не подходит! Попробуйте еще раз!");
            }
        }

        placeMove(board, userInput, 'X');
    }

    private static void placeMove(char[][] board, String position, char symbol) {
        switch (position) {
            case "1":
                board[0][0] = symbol;
                break;
            case "2":
                board[0][1] = symbol;
                break;
            case "3":
                board[0][2] = symbol;
                break;
            case "4":
                board[1][0] = symbol;
                break;
            case "5":
                board[1][1] = symbol;
                break;
            case "6":
                board[1][2] = symbol;
                break;
            case "7":
                board[2][0] = symbol;
                break;
            case "8":
                board[2][1] = symbol;
                break;
            case "9":
                board[2][2] = symbol;
                break;
            default:
                System.out.println(":-(");
        }
    }

    private static void printBoard(char[][] board) {
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("-+-+-");
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("-+-+-");
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
        System.out.println("-+-+-");
    }


}