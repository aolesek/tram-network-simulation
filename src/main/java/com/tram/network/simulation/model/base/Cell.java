package com.tram.network.simulation.model.base;

import com.tram.network.simulation.application.Application;
import com.tram.network.simulation.application.ApplicationUtils;

public class Cell {

    private TramState state = TramState.VOID;
    private Integer coords = 0;
    private Line line;
    private Line officialLine;
    private long id;

    public Cell(TramState s, int c, Line l) {
        this.state = s;
        this.coords = c;
        this.line = l;
        this.officialLine = new Line(l.getNumber(), l.getDirection());
        this.id = ApplicationUtils.getId();
    }

    public Cell(long id, TramState s, int c, Line l) {
        this.state = s;
        this.coords = c;
        this.line = l;
        this.officialLine = new Line(l.getNumber(), l.getDirection());
        this.id = id;
        this.id = ApplicationUtils.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Line getOfficialLine() {
        return officialLine;
    }

    public void setOfficialLine(Line officialLine) {
        this.officialLine = officialLine;
    }

    public TramState getState() {
        return state;
    }

    public void setState(TramState state) {
        this.state = state;
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
