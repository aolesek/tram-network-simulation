package com.tram.network.simulation.model;

import java.util.List;

public class LoopNode implements Node {
    StopQueue queue = new StopQueue(); //Queue is same as in the tram stop, only difference is reverting tram's direction.

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        return queue.getTram(lines);
}

    @Override
    public void tramArrived(Cell cell) {
        cell.setLine( cell.getLine().reverseDirection());
        queue.addTram(cell);
    }
}