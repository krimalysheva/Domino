package ru.vsu.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private List<Dice> dices = new ArrayList<>();
    private final Random rnd = new Random();

    public Player(String name) {
        this.name = name;
    }

    public Dice getRandomDice() {
        Dice d = dices.get(rnd.nextInt(dices.size()));
        dices.remove(d);
        return d;
    }

    public boolean hasDice(NodeValue value){
        try {
            /*https://vertex-academy.com/tutorials/ru/java-8-stream-find/*/
            dices.stream().filter(x->x.getNodes().stream().anyMatch(n->n.getValue() == value)).findFirst().get();
            return true;
        }
        catch(Exception ex){
            return false;
        }

    }

    public Dice getDice(NodeValue value){
        try {
            Dice dice = dices.stream().filter(d->d.getNodes().stream().anyMatch(n->n.getValue() == value)).findFirst().get();
            dices.remove(dice);
            return dice;
        }
        catch (Exception ex){
            return null;
        }
    }

    public void addDice(Dice d){
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
}
