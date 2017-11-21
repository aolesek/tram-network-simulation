package com.tram.network.simulation.model.queues;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.TramState;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class JunctionQueue implements Queue {

    private List<Cell> trams = new ArrayList<>();

    @Override
    public void addTram(Cell cell) {
        if (cell.getState() == TramState.VOID) throw new IllegalArgumentException();

        cell.setCoords(0);
        trams.add(cell);
    }

    @Override
    public Cell getTram(List<Line> lines) {
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
