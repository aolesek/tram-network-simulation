package com.tram.network.simulation.model.queues;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;

import java.util.List;

public interface Queue {
    public void addTram(Cell cell);

    public Cell getTram(List<Line> lines);
}
