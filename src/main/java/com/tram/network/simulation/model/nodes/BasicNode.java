package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.Line;

import java.util.List;

public class BasicNode implements Node {

    @Override
    public void tramArrived(Cell cell) {}

    @Override
    public Cell getTramFromQueue(List<Line> lines) { return null; }
}
