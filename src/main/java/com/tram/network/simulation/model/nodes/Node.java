package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.TramStatus;

import java.util.List;

public interface Node {
    Cell getTramFromQueue(List<Line> lines);
    void tramArrived(Cell cell);
    void addTramToQueue(Cell cell);
    List<TramStatus> getTrams();
}
