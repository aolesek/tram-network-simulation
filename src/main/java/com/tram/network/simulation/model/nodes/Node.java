package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;

import java.util.List;

public interface Node {
    Cell getTramFromQueue(List<Line> lines);
    void tramArrived(Cell cell);
}
