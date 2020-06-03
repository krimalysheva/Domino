package ru.vsu;

import ru.vsu.models.Dice;
import ru.vsu.models.Node;
import ru.vsu.models.NodeValue;
import ru.vsu.models.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private List<Dice> bank = new ArrayList<>();
    private List<Dice> board = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    private Game() {
    }

    public List<Dice> getBank() {
        return bank;
    }

    public List<Dice> getEnds() {
        return board.stream()
                .filter(d -> d.getNodes().stream().anyMatch(n -> n.getOutput() == null))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Dice getDiceFromBank() {
        if (bank.size() > 0) {
            int random = new Random().nextInt(bank.size());
            Dice d = bank.get(random);
            bank.remove(d);
            return d;
        }
        return null;
    }

    public void start() {
        while (bank.size() > 0) {
            for (Player p : getPlayers()) {
                if (board.size() == 0)
                    putDiceOnBoard(p.getRandomDice(), null);
                else {
                    List<Dice> ends = getEnds();
                    Dice dice = null;

                    while (bank.size() > 0) {
                        dice = p.getDiceForEnds(ends);
                        if (dice != null)
                            break;
                        else
                            p.addDice(getDiceFromBank());
                    }

                    if (dice != null) {
                        putDiceOnBoard(dice, ends);
                    } else {
                        System.out.println("End");

                        return;
                    }
                }

                printTree(getEnds().get(0));
            }
        }
    }

    private void printTree(Dice endDice) {
        StringBuilder treeStringBuilder = new StringBuilder();
        List<Node> endNodes = endDice.getEndNodes();
        if (endNodes == null || endNodes.size() == 0)
            return;

        Node startNode = endNodes.get(0);
        Node startNodeInput = startNode.getInput();
        Node nextNode = startNodeInput.getOutput();

        while (nextNode != null) {
            treeStringBuilder

                    .append(startNode.getColor())
                    .append("[")
                    .append(startNode)
                    .append('|')
                    .append(startNodeInput)
                    .append("]")
                    .append(Color.ANSI_RESET);
            startNode = nextNode;
            startNodeInput = nextNode.getInput();
            nextNode = startNodeInput.getOutput();
        }
        System.out.println(treeStringBuilder);
    }

    private void createDices() {
        for (int i = 0; i < NodeValue.values().length; i++) {
            for (int j = i; j < NodeValue.values().length; j++) {

                Node node1 = new Node(NodeValue.values()[i]);
                Node node2 = new Node(NodeValue.values()[j]);

                List<Node> nodes = new ArrayList<Node>(Arrays.asList(node1, node2));

                bank.add(new Dice(nodes));
            }
        }
        Collections.shuffle(bank);
    }

    private void putDiceOnBoard(Dice newDice, List<Dice> ends) {
        board.add(newDice);
        if (ends != null) {
            List<Node> endNodes = ends.stream().flatMap(d -> d.getEndNodes().stream()).collect(Collectors.toList());
            Node endNode = endNodes.stream().filter(n -> newDice.getEndNodes().contains(n)).findFirst().get();

            Node newNodeOutput = newDice.getEndNodes().stream().filter(n -> n.getValue() == endNode.getValue()).findFirst().get();

            endNode.setOutput(newNodeOutput);
            newNodeOutput.setOutput(endNode);

        }
    }

    public static GameBuilder newGameBuilder() {
        return new Game().new GameBuilder();
    }

    //https://habr.com/ru/post/244521/ - не совсем стандартный билдер
    public class GameBuilder {

        private GameBuilder() {

        }

        public GameBuilder withPlayers(List<Player> players) {
            Game.this.players = players;
            return this;
        }

        public GameBuilder withDices() {
            Game.this.createDices();
            return this;
        }

        public GameBuilder withDicesPerPlayer(int count) {
            for (int i = 0; i < count; i++) {
                for (Player p :
                        Game.this.players) {
                    p.addDice(getDiceFromBank());
                }
            }
            return this;
        }

        public Game build() {
            Game game = new Game();
            game.players = Game.this.players;
            game.bank = Game.this.bank;

            return game;
        }
    }

}
