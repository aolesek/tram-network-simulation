package com.tram.network.simulation.model;

import java.util.List;

public interface Node {
    Cell getTramFromQueue(List<Line> lines);
    void tramArrived(Cell cell);
}
