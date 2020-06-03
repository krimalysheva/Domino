package ru.vsu;

public enum Color {
    ANSI_RESET("\u001B[0m");


    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
