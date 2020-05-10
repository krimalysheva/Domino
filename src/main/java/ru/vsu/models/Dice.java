package ru.vsu.models;
/*https://habr.com/ru/company/luxoft/blog/270383/*/
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Dice {
    private List<Node> nodes;

    public Dice(List<Node> sides){
        this.nodes = sides;
    }

    @Override
    public String toString() {
        return "[" + nodes.get(0) + "|" + nodes.get(1) + "]";
    }

    @Override
    //todo
    //получаем костяшки 0-1 и 1-0 они одинаковые, а хеш-коды разные
    public int hashCode() {
        return Integer.parseInt(nodes.stream().map(n->Integer.toString(n.getValue().getValue())).sorted().reduce("", String::concat));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Dice)
        {
            return obj.hashCode() == this.hashCode();
        }
        return false;
    }

    public List<Node> getEndNodes(){

        return getNodes().stream().filter(n->n.getOutput() == null).collect(Collectors.toList());
    }
    public List<Node> getNodes(){
        return nodes;
    }

    /*public void reverse(){
        Collections.reverse(nodes);
    }*/
}
