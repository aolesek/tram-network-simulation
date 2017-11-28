package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.queues.StopQueue;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.List;
import java.util.Map;

public class StopNode implements Node {

    private StopQueue queue;

    public StopNode(Map<Line,Timetable> timetables) {
        queue = new StopQueue(timetables);
    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        return queue.getTram(lines);
    }

    @Override
    public void tramArrived(Cell cell) {
        queue.addTram(cell);
    }
}