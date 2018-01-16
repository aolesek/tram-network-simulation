package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.geo.Coords2D;
import com.tram.network.simulation.model.geo.GeoPath;
import com.tram.network.simulation.model.nodes.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
    private Map<Integer, Cell> cells = new HashMap<>();
    private int length;
    private int velocity = 3;
    private int defaultVelocity = 5;
    private Node source, destination;
    private List<Line> lines;
    private String id;
    private GeoPath geoPath;
    private CellIterator cellIterator = new CellIterator();

    public Path(Node source, Node destination, int defaultVelocity, List<Line> lines, GeoPath geoPath, int velocity) {
        this.id = source + " to " + destination;
        this.source = source;
        this.destination = destination;
        this.defaultVelocity = defaultVelocity;
        this.velocity = velocity;
        this.lines = lines;
        this.geoPath = geoPath;
        this.length = geoPath.getIntegerLength();

        for (int i = 0; i < length; i++) {
            cells.put(i, new Cell(TramState.VOID, i, new Line(0, LineDirection.NE)));
        }
    }

    public Path(String id, int length, int velocity, int defaultVelocity, Node source, Node destination, List<Line> lines, GeoPath geopath) {
        this(length, velocity, defaultVelocity, source, destination, lines, geopath);
        this.id = id;
    }

    public Path(int length, int velocity, int defaultVelocity, Node source, Node destination, List<Line> lines) {
        this(length, velocity, defaultVelocity, source, destination, lines, null);
    }

    public Path(int length, int velocity, int defaultVelocity, Node source, Node destination, List<Line> lines, GeoPath geopath) {
        this.length = length;
        this.velocity = velocity;
        this.defaultVelocity = defaultVelocity;
        this.source = source;
        this.destination = destination;
        this.lines = lines;
        this.geoPath = geopath;

        for (int i = 0; i < length; i++) {
            cells.put(i, new Cell(TramState.VOID, i, new Line(0, LineDirection.NE)));
        }
    }

    public String getId() {
        return id;
    }

    public Coords2D getCoordsByProgress(double progress) {
        if (geoPath != null) {
            return geoPath.getProgCoordinates(progress);
        }

        return new Coords2D(0.0, 0.0);
    }

    protected Path newInstance() {
        return new Path(length, velocity, defaultVelocity, source, destination, lines, this.geoPath);
    }

    public void setCellState(int coords, Cell cell) {
        cells.put(coords, cell);
    }

    public Path nextState(Boolean randomEvent, String currentTime) {
        String[] parts = currentTime.split(":");
        int v = velocity;
        int hour = Integer.parseInt(parts[0]);
        if((hour>=0 && hour<7) || (hour>17 && hour<24) || (hour>9 && hour<15)){
            v = defaultVelocity;
        }
//        if(randomEvent){
//            v = 0;
//        }

        Path newPath = newInstance();

        //TODO: might not be correct
        if (velocity != 0) {
            Cell newTram = source.getTramFromQueue(lines);
            if (newTram != null) newPath.setCellState(0, newTram);
        }


        while (cellIterator.hasNext()) {
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

    private CellIterator cellIterator() {
        return cellIterator;
    }

    protected Boolean hasNextCoordinates(int coords) {
        return coords < length - 1;
    }

    protected int nextCoordinates(int coords) {
        return coords + 1;
    }

    protected int initialCoordinates() {
        return -1;
    }

    public Map<Integer, Cell> getCells() {
        return cells;
    }

    public int getLength() {
        return length;
    }

    public String toString() {
        return "Path:" + cells.toString();
    }

    private class CellIterator {
        private int currentCoordinates;

        CellIterator() {
            currentCoordinates = initialCoordinates();
        }

        public Boolean hasNext() {
            return hasNextCoordinates(currentCoordinates);
        }

        public Cell next() {
            currentCoordinates = nextCoordinates(currentCoordinates);
            return new Cell(
                    cells.get(currentCoordinates).getState(),
                    currentCoordinates,
                    cells.get(currentCoordinates).getLine()
            );
        }
    }
}