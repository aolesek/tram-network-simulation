package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.queues.StopQueue;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.List;
import java.util.Map;

public class LoopNode implements Node {
    String name;
    private StopQueue queue; //Queue is same as in the tram stop, only difference is reverting tram's direction after arrival.

    public LoopNode(String name, Map<Line,Timetable> timetables) {

        this.name = name;
        queue = new StopQueue(timetables);
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
