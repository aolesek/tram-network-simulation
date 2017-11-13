package com.tram.network.simulation.model;

import java.util.List;

public interface Queue {
    public void addTram(Cell cell);

    public Cell getTram(List<Line> lines);
}
