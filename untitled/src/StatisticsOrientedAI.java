import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsOrientedAI {

    private final Map<String, Integer> stats = new HashMap<>();

    Game game = new Game();

    public void createStatsForO() {
        for (int i = 0; i < 1_000_000; i++) { // 10_000_000
            boolean gameOver = false;
            String field = "_________";
            List<String> winningStates = new ArrayList<>();
            List<String> currentGame = new ArrayList<>();

            char winner = '?';

            char npc = Types.O.type;
            char player = Types.X.type;

            while (!gameOver) {

                if (game.getAvailableMoves(field).size() == 0) {
                    gameOver = true;
                } else {
                    field = game.smartMove(npc, field); //field = game.randomMove(npc, field);
                    currentGame.add(field);
                }

                if (game.isVictory(npc, field)) {
                    winner = npc;
                    gameOver = true;
                } else if (game.isVictory(player, field)) {
                    gameOver = true;
                }

                if (game.getAvailableMoves(field).size() == 0) {
                    gameOver = true;
                } else {
                    field = game.randomMove(player, field);
                    currentGame.add(field);
                }

                if (game.isVictory(npc, field)) {
                    winner = npc;
                    gameOver = true;
                } else if (game.isVictory(player, field)) {
                    gameOver = true;
                }
            }

            if(winner == npc) {
                winningStates = currentGame;
            }

            for (String winningState : winningStates) {
                stats.put(winningState, stats.getOrDefault(winningState, 0) + 1);
            }
        }
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/results.txt"))) {
            for (String state : stats.keySet()) {
                bw.write(state + "=" + stats.get(state) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, Integer> load(String path) {
        Map<String, Integer> map = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.ready()) {
                String line = br.readLine();
                String[] parts = line.split("=");
                map.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static void main(String[] args) {
        StatisticsOrientedAI ai = new StatisticsOrientedAI();

        //System.out.println(ai.game.isVictory('X', "_0X0X0X__"));

        ai.createStatsForO();

        ai.save();
    }
}
