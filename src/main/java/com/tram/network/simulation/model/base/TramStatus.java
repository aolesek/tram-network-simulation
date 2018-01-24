package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.geo.Coords2D;

public class TramStatus {
    //private Line line;
    private Line officialLine;
    private String pathId;
    private double progress;
    private Coords2D coordinates;
    private long id;

    public TramStatus(long id,Line line, String pathId, Coords2D coordinates, double progress, Line officialLine) {
        //this.line = line;
        this.officialLine = officialLine;
        this.pathId = pathId;
        this.coordinates = coordinates;
        this.progress = progress;
        this.id = id;
    }
}
