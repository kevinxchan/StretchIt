package me.kevinxchan.kevinxchan.routineme.model;

public enum Category {
    EXERCISE("Exercise"),
    REST("Rest"),
    COUNTDOWN("Countdown");

    private final String text;

    Category(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
