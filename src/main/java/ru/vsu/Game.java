package ru.vsu;

import ru.vsu.models.Dice;
import ru.vsu.models.Node;
import ru.vsu.models.NodeValue;
import ru.vsu.models.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Dice> bank = new ArrayList<>();
    private List<Dice> board = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private StringBuilder treeStringBuilder = new StringBuilder();

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

    //todo fix get(0)
    //todo fix get(1) tree
    public void start() {
        while (bank.size() > 0) {
            for (Player p : getPlayers()) {
                if (board.size() == 0)
                    board.add(p.getRandomDice());
                else {
                    List<Node> endNodes = getEnds().get(0).getEndNodes();
                    Node endNode = endNodes.get(endNodes.size() - 1);

                    while (bank.size() > 0 && !p.hasDice(endNode.getValue())) {
                        p.addDice(getDiceFromBank());
                    }

                    Dice d = p.getDice(endNode.getValue());
                    if (d != null) {
                        putDiceOnBoard(d, endNode);
                    }
                }
                treeStringBuilder.append(getEnds().get(getEnds().size() - 1));
                System.out.println(treeStringBuilder);
            }
        }
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

    private void putDiceOnBoard(Dice newDice, Node endNode) {
        board.add(newDice);
        Node newNodeOutput = newDice.getEndNodes().stream().filter(n -> n.getValue() == endNode.getValue()).findFirst().get();

        endNode.setOutput(newNodeOutput);
        newNodeOutput.setOutput(endNode);
    }

    //todo fix get(1)
    /*private void buildTree(StringBuilder sb, Dice dice, ArrayList<Node> visitedNodes) {
        sb.append(dice);
        Node nextDiceNode = dice.getNodes().get(1).getOutput();
        visitedNodes.addAll(dice.getNodes());

        if (nextDiceNode != null && !visitedNodes.contains(nextDiceNode)) {
            Dice d = board.stream().filter(x -> x.getNodes().contains(nextDiceNode)).findFirst().get();
            buildTree(sb, d, visitedNodes);
        }
        else return;
    }*/

    private void buildTree(StringBuilder sb, Dice newDice){
        sb.append(newDice);
    }

    /*private void dicesGenerator(ArrayList<Node> sides, List<Dice> dices, int nodeValueIndex,  int sidesCount) {
        if (sidesCount == 0) {
            dices.add(new Dice(new ArrayList<>(sides)));
            return;
        }
        sidesCount -=1;                                                             //next loop layer
        for (int i = nodeValueIndex; i < NodeValue.values().length; i++) {
            sides.add(new Node(NodeValue.values()[i]));                             //add new side  [i]!!!!!!!
            dicesGenerator(sides, dices, i, sidesCount);                            //recursive call
            sides.remove(sides.size() - 1);                                   //remove last side to generate next
        }
    }*/

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
