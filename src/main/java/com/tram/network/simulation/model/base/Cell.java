package com.tram.network.simulation.model.base;

public class Cell {

    private TramState state = TramState.VOID;
    private Integer coords = 0;
    private Line line;


    public Cell(TramState s, int c, Line l) {
        this.state = s;
        this.coords = c;
        this.line = l;
    }

    public TramState getState() {
        return state;
    }

    Integer getCoords() {
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

    public String toString() {
        if (state == TramState.VOID) return "[     ]";
        return "[" + line.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        Cell c = (Cell) o;
        return (c.state == state) & (c.line == line) & (c.coords == coords);
    }
}
