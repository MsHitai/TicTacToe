import java.util.*;

public class Game {

    Scanner scanner = new Scanner(System.in);
    Map<String, Integer> stats = StatisticsOrientedAI.load("resources/results.txt");
    Random random = new Random();

    public List<Integer> getAvailableMoves(String field) {
        List<Integer> avMoves = new ArrayList<>();
        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) == Types.BLANK.type) {
                avMoves.add(i);
            }
        }
        return avMoves;
    }

    public String humanMove(char player, String field) {
        System.out.println("Введите номер поля 1-9");
        String input = scanner.nextLine();
        int i = 0;
        try {
            i = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Неверно введено поле");
        }
        return makeMove(player, field, i);
    }

    private String makeMove(char player, String field, int i) { // todo 'field is occupied'
        return field.substring(0, i) + player + field.substring(i + 1);
    }

    public boolean isVictory(char player, String field) {
        List<int[]> positions = new ArrayList<>();

        positions.add(new int[]{0, 4, 8});
        positions.add(new int[]{0, 3, 6});
        positions.add(new int[]{1, 4, 7});
        positions.add(new int[]{2, 5, 8});
        positions.add(new int[]{0, 1, 2});
        positions.add(new int[]{3, 4, 5});
        positions.add(new int[]{6, 7, 8});
        positions.add(new int[]{2, 4, 6});

        int i = 0;
        int b = 1;
        int c = 2;

        for (int[] position : positions) {
            if (field.charAt(position[i]) == field.charAt(position[b])
                    && field.charAt(position[i]) == field.charAt(position[c])
                    && field.charAt(position[i]) == player) {
                return true;
            }
        }
        return false;
    }

    public void newGamePlayerVSAi() {
        boolean gameOver = false;
        String field = "_________";
        //String x = field.substring(0, 3) + "\n" + field.substring(3, 6) + "\n" + field.substring(6, 9);
        System.out.println(field.substring(0, 3) + "\n" + field.substring(3, 6) + "\n" + field.substring(6, 9));
        char winner = '?';

        char player = Types.X.type;
        char npc = Types.O.type;

        while (!gameOver) {
            if (getAvailableMoves(field).size() == 0) {
                gameOver = true;
            } else {
                field = humanMove(player, field);
            }

            if (isVictory(player, field)) {
                gameOver = true;
                winner = player;
            } else if (isVictory(npc, field)) {
                gameOver = true;
                winner = npc;
            }

            if (getAvailableMoves(field).size() == 0) {
                gameOver = true;
            } else {
                field = smartMove(npc, field);
            }

            System.out.println(field.substring(0, 3) + "\n" + field.substring(3, 6) + "\n" + field.substring(6, 9));



            if (isVictory(player, field)) {
                gameOver = true;
                winner = player;
            } else if (isVictory(npc, field)) {
                gameOver = true;
                winner = npc;
            }
        }

        System.out.println(winner + " wins");
    }


    private String smartMove(char player, String field) {
        List<Integer> availableMoves = getAvailableMoves(field);
        int bestScore = 0;
        String bestMove = "";

        for (Integer availableMove : availableMoves) {
            String move = makeMove(player, field, availableMove);
            int score = stats.getOrDefault(move, 0);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        if (bestScore == 0) {
            return randomMove(player, field);
        }
        return bestMove;
    }

    public String randomMove(char player, String field) {
        List<Integer> availableMoves = getAvailableMoves(field);
        int i = random.nextInt(availableMoves.size());
        return makeMove(player, field, availableMoves.get(i));
    }

    public static void main(String[] args) {
        Game game = new Game();

        boolean gameOver = false;

        while(!gameOver) {
            game.newGamePlayerVSAi();
            System.out.println("Хотите продолжить? y/n");
            String input = game.scanner.nextLine();
            if ("n".equals(input)) {
                gameOver = true;
            }
        }


    }
}
