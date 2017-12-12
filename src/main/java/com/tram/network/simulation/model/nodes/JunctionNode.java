package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.TramStatus;
import com.tram.network.simulation.model.queues.JunctionQueue;
import com.tram.network.simulation.model.base.Line;

import java.util.ArrayList;
import java.util.List;

public class JunctionNode implements Node {

    String name;
    JunctionQueue queue = new JunctionQueue();

    public JunctionNode(String name) {
        this.name = name;
    }

    public JunctionNode() {

    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        Cell tram = queue.getTram(lines);
        if (tram != null)
            System.out.println("Tramwaj " + tram + " opuścił skrzyżowanie " + name + " w kierunku " + lines);
        return tram;
    }

    @Override
    public void tramArrived(Cell cell) {
        if (cell != null)
            System.out.println("Tramwaj " + cell +  " dotarł do skrzyżowania " + name);
        queue.addTram(cell);
    }

    @Override
    public void addTramToQueue(Cell cell) {
        queue.addTram(cell);
    }

    @Override
    public List<TramStatus> getTrams() {
        return new ArrayList<TramStatus>();
    }
}
