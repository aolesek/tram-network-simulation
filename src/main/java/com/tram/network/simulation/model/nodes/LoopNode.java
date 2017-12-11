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

public class LoopNode implements Node {
    private String name;
    private Coords2D coordinates;
    private StopQueue queue; //Queue is same as in the tram stop, only difference is reverting tram's direction after arrival.

    public LoopNode(String name, Map<Line,Timetable> timetables) {

        this.name = name;
        queue = new StopQueue(timetables);
    }

    public LoopNode(Coords2D coordinates, String name, Map<Line,Timetable> timetables) {
        this.coordinates = coordinates;
        this.name = name;
        queue = new StopQueue(timetables);
    }

    public List<TramStatus> getTrams() {
        List<TramStatus> trams = new ArrayList<>();
        List<Cell> stoppedTrams = queue.getStoppedTrams();

        for (Cell tram : stoppedTrams) {
            trams.add(
                    new TramStatus(
                            tram.getLine(), name, coordinates, 1.0
                    )
            );
        }

        return trams;
    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        Cell tram = queue.getTram(lines);
        if (tram != null)
            System.out.println("Tramwaj " + tram + " opuścił pętle " + name);
        return tram;
}
    @Override
    public void tramArrived(Cell cell) {
        cell.setLine( cell.getLine().reverseDirection());
        if (cell != null)
            System.out.println("Tramwaj " + cell + " dotarł do pętli " + name);
        queue.addTram(cell);
    }

    @Override
    public void addTramToQueue(Cell cell) {
        queue.addTram(cell);
    }
}
