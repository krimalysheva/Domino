package ru.vsu.models;

import ru.vsu.BgColor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Dice> dices = new ArrayList<>();
    private final BgColor color;
    private final Random rnd = new Random();

    public Player(String name) {
        this.name = name;

        Random rand = new Random();
        color = BgColor.values()[rand.nextInt(16)];
    }

    public Dice getRandomDice() {
        Dice d = dices.get(rnd.nextInt(dices.size()));
        dices.remove(d);
        return d;
    }

    public Dice getDiceForEnds(List<Dice> endDices) {
        try {
            /*https://vertex-academy.com/tutorials/ru/java-8-stream-find/*/
            List<Node> endNodes = endDices.stream().flatMap(d -> d.getEndNodes().stream()).collect(Collectors.toList());
            for (Node node :
                    endNodes) {
                Optional<Dice> dice = dices.stream().filter(d -> d.getEndNodes().contains(node)).findFirst();
                if (dice.isPresent()) {
                    dices.remove(dice.get());
                    return dice.get();
                }
            }
            return null;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public void addDice(Dice d) {
        d.getNodes().forEach(n->n.setColor(color));
        dices.add(d);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dice> getDices() {
        return dices;
    }

    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    public BgColor getTextColor() {
        return color;
    }
}
