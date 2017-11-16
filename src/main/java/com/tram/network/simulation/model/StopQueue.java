package com.tram.network.simulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StopQueue implements Queue {
    private List<Cell> trams = new ArrayList<>();

    @Override
    public void addTram(Cell cell) {
        if (cell.getState() == TramState.VOID) throw new IllegalArgumentException();

        cell.setCoords(0);
        trams.add(cell);
    }

    @Override
    public Cell getTram(List<Line> lines) {

        //TODO: Implement departing tram only if the departure time has passed, it's only sketch based on junction
        //TODO: Checking lines is not necessary at this case, simply don't want to break something
        for (ListIterator<Cell> iter = trams.listIterator(); iter.hasNext(); ) {
            Cell tram = iter.next();
            Line line = tram.getLine();
            if (lines.contains(line)) {
                iter.remove();
                return tram;
            }
        }
        return null;
    }
}
