package ru.vsu.tests;


import org.junit.Test;
import ru.vsu.*;
import ru.vsu.models.Dice;
import ru.vsu.models.Node;
import ru.vsu.models.NodeValue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTests {
    @Test
    public void Dices28ShouldBeGenerated() {
        Game game = Game.newGameBuilder()
                .withDices()
                .build();
        assertEquals(28, game.getBank().size());
    }

    @Test
    public void Dices2dShouldBeGenerated() {
        Game game = Game.newGameBuilder()
                .withDices()
                .build();

        List<Dice> dices = new ArrayList<>();
        for(int i = 0; i < NodeValue.values().length; i++){
            for(int j = i; j < NodeValue.values().length; j++){
                Node node1 = new Node(NodeValue.values()[i]);
                Node node2 = new Node(NodeValue.values()[j]);
                ArrayList<Node> nodes = new ArrayList<Node>();
                nodes.add(node1);
                nodes.add(node2);
                dices.add(new Dice(nodes));
            }
        }

        assertTrue(game.getBank().containsAll(dices));
    }
}
