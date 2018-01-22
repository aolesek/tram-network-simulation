package com.tram.network.simulation.model.queues;

import com.tram.network.simulation.application.ApplicationUtils;
import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.LineDirection;
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
        waitNSteps(ApplicationUtils.timeAtStop);
        trams.add(cell);
    }

    public void waitNSteps(int n) {
        //adding empty tram to queue means stopping for n steps
      //  System.out.println("Adding "+n+" empty trams to queue");
        for (int i = 0; i < n; i++)
            trams.add(new Cell(TramState.VOID, 0, new Line(-1, LineDirection.NE)));
    }

    @Override
    public Cell getTram(List<Line> lines) {
        Cell tram = null;


        List<Cell> rigthDirectionTrams = new ArrayList<>();
        for (int i = 0; i < trams.size(); i++) {
            if (lines.contains(trams.get(i).getLine()) || (trams.get(i).getState() == TramState.VOID)) {
                rigthDirectionTrams.add(trams.get(i));
            }
        }


        //System.out.println(rigthDirectionTrams);

        if ( ! rigthDirectionTrams.isEmpty() )
            tram = rigthDirectionTrams.get(0);

        if ( (tram != null) && (tram.getState() != TramState.VOID)) {
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


        } else {
            trams.remove(tram); // removing void tram
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
