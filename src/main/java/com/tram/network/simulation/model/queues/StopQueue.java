package com.tram.network.simulation.model.queues;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.base.TramState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopQueue implements Queue {

    private List<Cell> trams = new ArrayList<>();
    private Map<Line,Timetable> timetables;

    public StopQueue(Map<Line,Timetable> timetables) {
        this.timetables = timetables;
    }

    @Override
    public void addTram(Cell cell) {
        if (cell.getState() == TramState.VOID) throw new IllegalArgumentException();

        cell.setCoords(0);
        trams.add(cell);
    }

    @Override
    public Cell getTram(List<Line> lines) {
        Cell tram = null;
        if ( ! trams.isEmpty() )
            tram = trams.get(0);

        if ( tram != null) {
            Line line = tram.getLine();
            Timetable lineTimetable = timetables.get(line);
            if (lines.contains(line) && (lineTimetable != null) && lineTimetable.isItDepartureTime() ) {
                trams.remove(0);
                lineTimetable.tramDeparted();
                return tram;
            }

        }

        return null;
    }
}
