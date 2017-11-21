package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.nodes.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
    private Map<Integer,Cell> cells = new HashMap<>();
    private int length;
    private int velocity = 3;
    private int defaultVelocity = 5;
    private Node source, destination;
    private List<Line> lines;

    private CellIterator cellIterator = new CellIterator();

    public Path (int length, int velocity, int defaultVelocity, Node source, Node destination, List<Line> lines) {
        this.length = length;
        this.velocity = velocity;
        this.defaultVelocity = defaultVelocity;
        this.source = source;
        this.destination = destination;
        this.lines = lines;

        for (int i = 0; i < length; i++) {
            cells.put(i, new Cell(TramState.VOID, i, new Line(0,LineDirection.NE)));
        }
    }

    protected Path newInstance()
    {
        return new Path(length, velocity, defaultVelocity, source, destination, lines);
    }

    public void setCellState(int coords, Cell cell)
    {
        cells.put(coords, cell);
    }

    public Path nextState() {

        //TODO: implement velocity modifications based on time, traffic and random evenets

        Path newPath = newInstance();

        //TODO: might not be correct
        if (velocity != 0) {
            Cell newTram = source.getTramFromQueue(lines);
            if (newTram != null) newPath.setCellState(0,newTram);
        }



        while( cellIterator.hasNext()) {
            Cell cell = cellIterator().next();
            if (cell.getState() == TramState.TRAM) {
                int newCoords = cell.getCoords() + velocity;
                if (newCoords < length) {
                    cell.setCoords(newCoords);
                    newPath.setCellState(newCoords, cell);
                } else {
                    destination.tramArrived(cell);

                }
            }
        }

        return newPath;
    }

    private CellIterator cellIterator()
    {
       return cellIterator;
    }

    protected Boolean hasNextCoordinates(int coords)
    {
        return coords < length - 1;
    }

    protected int nextCoordinates(int coords)
    {
        return coords + 1;
    }

    protected int initialCoordinates()
    {
        return -1;
    }

    public String toString() { return "Path:" + cells.toString(); }

    private class CellIterator {
        private int currentCoordinates;

        CellIterator()
        {
            currentCoordinates = initialCoordinates();
        }

        public Boolean hasNext()
        {
            return hasNextCoordinates(currentCoordinates);
        }

        public Cell next() {
            currentCoordinates = nextCoordinates(currentCoordinates);
            return  new Cell(
                    cells.get(currentCoordinates).getState(),
                    currentCoordinates,
                    cells.get(currentCoordinates).getLine()
            );
        }
    }
}
