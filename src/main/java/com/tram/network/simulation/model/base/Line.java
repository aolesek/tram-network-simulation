package com.tram.network.simulation.model.base;

public class Line {
    private int number = 0;
    private LineDirection direction = LineDirection.NE;

    public Line(String str) {
        String[] tokens = str.split(" ");
        this.number = Integer.parseInt(tokens[0]);

        this.direction = (tokens[1].matches("NE")) ? LineDirection.NE : LineDirection.SW;
    }

    public Line(int number, LineDirection direction) {
        this.number = number;
        this.direction = direction;
    }

    private LineDirection oppositeDirection() {
        return (direction == LineDirection.NE) ? LineDirection.SW : LineDirection.NE;
    }

    @Override
    public int hashCode() {
        int dir = (direction == LineDirection.NE) ? 1000 : 2000;
        return dir + number;
    }

    @Override
    public boolean equals(Object o) {
        Line l = (Line) o;
        return (l.number == this.number) & (l.direction == this.direction);
    }

    public Line reverseDirection() {
        return new Line(number, oppositeDirection());
    }

    public String toString() {
        return number + "->" + direction.toString();
    }
}
