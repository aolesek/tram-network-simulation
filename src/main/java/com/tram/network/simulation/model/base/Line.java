package com.tram.network.simulation.model.base;

public class Line {
    private int number = 0;
    private LineDirection direction = LineDirection.NE;

    private LineDirection oppositeDirection() {
        return (direction == LineDirection.NE) ? LineDirection.SW : LineDirection.NE;
    }

    public Line(String str) {
        String [] tokens = str.split(" ");
        this.number = Integer.parseInt(tokens[0]);

        this.direction = (tokens[1].matches("NE")) ? LineDirection.NE : LineDirection.SW;
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

    public Line(int number, LineDirection direction) {
        this.number = number;
        this.direction = direction;
    }

    public Line reverseDirection() {
        return new Line(number, oppositeDirection());
    }

    public LineDirection getDirection() {
        return direction;
    }

    public void setDirection(LineDirection direction) {
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        return number + "->" + direction.toString();
    }
}
