package com.tram.network.simulation.model;

public class Line {
    private int number = 0;
    private LineDirection direction = LineDirection.NE;

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
}
