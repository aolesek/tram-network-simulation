package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.TramStatus;
import com.tram.network.simulation.model.geo.Coords2D;
import com.tram.network.simulation.model.queues.StopQueue;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopNode implements Node {

    private String name;
    private StopQueue queue;
    private Coords2D coordinates;

    public StopNode(Coords2D coordinates, String name, Map<Line,Timetable> timetables) {
        this.coordinates = coordinates;
        this.name = name;
        queue = new StopQueue(timetables,name);

    }

    @Override
    public List<TramStatus> getTrams() {
        List<TramStatus> trams = new ArrayList<>();
        List<Cell> stoppedTrams = queue.getStoppedTrams();

        for (Cell tram : stoppedTrams) {
            trams.add(
                    new TramStatus(
                            tram.getId(), tram.getLine(), name, coordinates, 1.0,tram.getOfficialLine()
                    )
            );
        }

        return trams;
    }

    public StopNode(String name, Map<Line,Timetable> timetables) {
        this.name = name;
        queue = new StopQueue(timetables);
    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        Cell tram = queue.getTram(lines);
        //if (tram != null)
            //System.out.println("Tramwaj "  + tram + " opuścił przystanek " + name);
        return tram;
    }

    @Override
    public void tramArrived(Cell cell) {
        //if (cell != null)
            //System.out.println("Tramwaj " + cell + " dotarł do przystanku " + name);
        queue.addTram(cell);
    }

    @Override
    public void addTramToQueue(Cell cell) {
        queue.addStartingTram(cell);
    }

    public String toString() {
        return name + "  (stop)";
    }
}
