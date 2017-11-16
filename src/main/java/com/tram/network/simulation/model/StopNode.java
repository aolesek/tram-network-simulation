package com.tram.network.simulation.model;

import java.util.List;

public class StopNode implements Node {

    StopQueue queue = new StopQueue();

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        return queue.getTram(lines);
    }

    @Override
    public void tramArrived(Cell cell) {
        queue.addTram(cell);
    }
}
