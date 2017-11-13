package com.tram.network.simulation.model;

public class Cell {

    private TramState state = TramState.VOID;
    private Integer coords = 0;
    private Line line;


    public Cell(TramState s, int c, Line l)
    {
        this.state = s;
        this.coords = c;
        this.line = l;
    }

    public String toString()
    {
        String s = (state == TramState.TRAM) ? "T" : "_";
        return s+coords.toString();
    }

    public TramState getState() {
        return state;
    }

    public void setState(TramState state) {
        this.state = state;
    }

    public Integer getCoords() {
        return coords;
    }

    public void setCoords(Integer coords) {
        this.coords = coords;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
