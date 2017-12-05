package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.queues.StopQueue;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.List;
import java.util.Map;

public class StopNode implements Node {

    String name;
    private StopQueue queue;

    public StopNode(String name, Map<Line,Timetable> timetables) {
        this.name = name;
        queue = new StopQueue(timetables);
    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        Cell tram = queue.getTram(lines);
        if (tram != null)
            System.out.println("Tramwaj "  + tram + " opuścił przystanek " + name);
        return tram;
    }

    @Override
    public void tramArrived(Cell cell) {
        if (cell != null)
            System.out.println("Tramwaj " + cell + " dotarł do przystanku " + name);
        queue.addTram(cell);
    }

    @Override
    public void addTramToQueue(Cell cell) {
        queue.addTram(cell);
    }
}
