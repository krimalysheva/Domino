package ru.vsu.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.vsu.models.Dice;
import ru.vsu.models.Node;
import ru.vsu.models.NodeValue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DiceTests {
    @Test
    public void equalDicesWithSameNodesObjectsShouldBeTrue(){
        List<Node> nodes= new ArrayList<Node>();
        nodes.add(new Node(NodeValue.Zero));
        nodes.add(new Node(NodeValue.One));

        Dice d1 = new Dice(nodes);
        Dice d2 = new Dice(nodes);

        assertEquals(d1, d2);
    }

    @Test
    public void equalDicesWithDifferentNodesObjectsShouldBeTrue(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Zero));
        nodes1.add(new Node(NodeValue.One));

        List<Node> nodes2= new ArrayList<Node>();
        nodes2.add(new Node(NodeValue.Zero));
        nodes2.add(new Node(NodeValue.One));

        Dice d1 = new Dice(nodes1);
        Dice d2 = new Dice(nodes2);

        assertEquals(d1, d2);
    }

    @Test
    public void equalDicesWithDifferentNodesObjectsInDifferentOrderShouldBeTrue(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Zero));
        nodes1.add(new Node(NodeValue.One));

        List<Node> nodes2= new ArrayList<Node>();
        nodes2.add(new Node(NodeValue.One));
        nodes2.add(new Node(NodeValue.Zero));

        Dice d1 = new Dice(nodes1);
        Dice d2 = new Dice(nodes2);

        assertEquals(d1, d2);
    }

    @Test
    public void equalDicesWithDifferentNodesObjectsShouldBeFalse(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Zero));
        nodes1.add(new Node(NodeValue.One));

        List<Node> nodes2= new ArrayList<Node>();
        nodes2.add(new Node(NodeValue.Zero));
        nodes2.add(new Node(NodeValue.Two));

        Dice d1 = new Dice(nodes1);
        Dice d2 = new Dice(nodes2);

        assertNotEquals(d1, d2);
    }

    @Test
    public void hashCodeTest(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Three));
        nodes1.add(new Node(NodeValue.Four));

        Dice d1 = new Dice(nodes1);

        assertEquals(34, d1.hashCode());
    }

    @Test
    public void hashCodeTestDifferent(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Three));
        nodes1.add(new Node(NodeValue.Four));

        List<Node> nodes2= new ArrayList<Node>();
        nodes2.add(new Node(NodeValue.Two));
        nodes2.add(new Node(NodeValue.Five));

        Dice d1 = new Dice(nodes1);
        Dice d2 = new Dice(nodes2);

        assertNotEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void hashCodeTestEquals(){
        List<Node> nodes1= new ArrayList<Node>();
        nodes1.add(new Node(NodeValue.Three));
        nodes1.add(new Node(NodeValue.Four));

        List<Node> nodes2= new ArrayList<Node>();
        nodes2.add(new Node(NodeValue.Four));
        nodes2.add(new Node(NodeValue.Three));

        Dice d1 = new Dice(nodes1);
        Dice d2 = new Dice(nodes2);

        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
