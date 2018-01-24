package com.tram.network.simulation.model.queues;

import com.tram.network.simulation.application.ApplicationUtils;
import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.LineDirection;
import com.tram.network.simulation.model.base.TramState;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoopQueue implements Queue {

    private List<Cell> trams = new ArrayList<>();
    private Map<Line, Timetable> timetables;

    public LoopQueue(Map<Line, Timetable> timetables) {
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

        if (lines == null)
            return null;

        for (Line line : lines) {

            List<Cell> rightLineTrams = new ArrayList<>();
            for (int i = 0; i < trams.size(); i++) {
                if (line.equals(trams.get(i).getLine())) {
                    rightLineTrams.add(trams.get(i));
                }

                if (!rightLineTrams.isEmpty())
                    tram = rightLineTrams.get(0);


                if ((tram != null) && (tram.getState() != TramState.VOID)) {
                    Line tLine = tram.getLine();

                    if (timetables.containsKey(tLine)) {
                        Timetable lineTimetable = timetables.get(line);

                        if ((line.equals(tLine)) && (lineTimetable != null) && lineTimetable.isItDepartureTime()) {
                            trams.remove(tram);
                            lineTimetable.tramDeparted();
                            return tram;
                        }
                    } else {
                        if (line == tLine) {
                            trams.remove(tram);
                            return tram;
                        }

                    }


                }
            }
        }












        return null;
    }

    public List<Cell> getStoppedTrams() {
        List<Cell> stoppedTrams = new ArrayList<>();
        stoppedTrams.addAll(trams);
        stoppedTrams.removeIf(t -> t.getState().equals(TramState.VOID));

        return stoppedTrams;
    }
}
