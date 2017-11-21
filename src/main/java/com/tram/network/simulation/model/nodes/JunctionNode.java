package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.queues.JunctionQueue;
import com.tram.network.simulation.model.base.Line;

import java.util.List;

public class JunctionNode implements Node {

    JunctionQueue queue = new JunctionQueue();

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        return queue.getTram(lines);
    }

    @Override
    public void tramArrived(Cell cell) {
        queue.addTram(cell);
    }
}
