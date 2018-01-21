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


        List<Cell> rigthDirectionTrams = new ArrayList<>();
        for (int i = 0; i < trams.size(); i++) {
            if (lines.contains(trams.get(i).getLine())) {
                rigthDirectionTrams.add(trams.get(i));
            }
        }

        if ( ! rigthDirectionTrams.isEmpty() )
            tram = rigthDirectionTrams.get(0);

        if ( tram != null) {
            Line line = tram.getLine();
            if (timetables.containsKey(line)) {
                Timetable lineTimetable = timetables.get(line);

                if (lines.contains(line) && (lineTimetable != null) && lineTimetable.isItDepartureTime() ) {
                    trams.remove(tram);
                    lineTimetable.tramDeparted();
                    return tram;
                }
            } else {
               if (lines.contains(line)) {
                   trams.remove(tram);
                   return tram;
               }

            }


        }

        return null;
    }

    public List<Cell> getStoppedTrams() {
        List<Cell> stoppedTrams = new ArrayList<>();
        stoppedTrams.addAll(trams);

        return stoppedTrams;
    }
}
