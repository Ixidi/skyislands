package me.ixidi.skyisland.util.integer;

public class IntComparer {

    private int first;
    private int second;

    public IntComparer(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getBigger() {
        if (first > second) {
            return first;
        }
        return second;
    }

    public int getSmaller() {
        if (first < second) {
            return first;
        }
        return second;
    }
}
