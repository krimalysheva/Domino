package ru.vsu.models;


import ru.vsu.BgColor;

import java.util.Objects;

public class Node {
    private final NodeValue value; /*https://ru.stackoverflow.com/questions/498742/private-final-Нельзя-изменить/498744*/

    private Node input;
    private Node output;
    private BgColor color;

    public Node(NodeValue value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return Integer.toString(value.getValue()) ;
    }

    @Override
    /*https://habr.com/ru/post/168195/*/
    public int hashCode() {
        return value.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Node){
            return obj.hashCode() == this.hashCode();
        }
        return false;
    }

    public NodeValue getValue(){
        return this.value;
    }

    public Node getInput() {
        return input;
    }

    public void setInput(Node input) {
        this.input = input;
    }

    public Node getOutput() {
        return output;
    }

    public void setOutput(Node output) {
        this.output = output;
    }

    public void setColor(BgColor color) {
        this.color = color;
    }

    public BgColor getColor() {
        return color;
    }
}
