package ru.vsu;

import ru.vsu.models.NodeValue;
import ru.vsu.models.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    private static final String configFileName = "config.properties";

    public static void main(String[] args) {
        try {
            Game game = Game.newGameBuilder()
                    .withPlayers(readPlayers())
                    .withDices()
                    .withDicesPerPlayer(6)
                    .build();
            game.start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Player> readPlayers() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите количество игроков");
        int playersCount = sc.nextInt();

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < playersCount; i++) {
            Player p = new Player("player" + i);
            players.add(p);
        }
        return players;
    }
}
