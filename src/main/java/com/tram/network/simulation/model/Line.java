package com.tram.network.simulation.model;

public class Line {
    private int number = 0;
    private LineDirection direction = LineDirection.NE;

    @Override
    public boolean equals(Object o) {
        Line l = (Line) o;
        return (l.number == this.number) & (l.direction == this.direction);
    }

    public Line(int number, LineDirection direction) {
        this.number = number;
        this.direction = direction;
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
