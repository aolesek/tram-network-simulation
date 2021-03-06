package com.tram.network.simulation.model.nodes;

import com.tram.network.simulation.model.base.Cell;
import com.tram.network.simulation.model.base.TramStatus;
import com.tram.network.simulation.model.geo.Coords2D;
import com.tram.network.simulation.model.queues.JunctionQueue;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.queues.StopQueue;
import com.tram.network.simulation.model.timetables.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunctionNode implements Node {

    String name;
    JunctionQueue queue = new JunctionQueue();
    private Coords2D coordinates;

    public JunctionNode(String name) {
        this.name = name;
    }

    public JunctionNode() {
    }

    public JunctionNode(String name, Coords2D coordinates) {
        this.coordinates = coordinates;
        this.name = name;
    }

    @Override
    public Cell getTramFromQueue(List<Line> lines) {
        Cell tram = queue.getTram(lines);
        //if (tram != null)
            //System.out.println("Tramwaj " + tram + " opuścił skrzyżowanie " + name + " w kierunku " + lines);
        return tram;
    }

    @Override
    public void tramArrived(Cell cell) {
        //if (cell != null)
            //System.out.println("Tramwaj " + cell +  " dotarł do skrzyżowania " + name);
        queue.addTram(cell);
    }

    @Override
    public void addTramToQueue(Cell cell) {
        queue.addTram(cell);
    }

    @Override
    public List<TramStatus> getTrams() {
        List<TramStatus> trams = new ArrayList<>();
        List<Cell> stoppedTrams = queue.getStoppedTrams();

        for (Cell tram : stoppedTrams) {
            trams.add(
                    new TramStatus(
                            tram.getId(), tram.getLine(), name, coordinates, 1.0,tram.getOfficialLine()
                    )
            );
        }

        return trams;
    }

    public String toString() {
        return name + "  (junction)";
    }
}
