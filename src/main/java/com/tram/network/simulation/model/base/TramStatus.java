package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.geo.Coords2D;

public class TramStatus {
    private Line line;
    private String pathId;
    private double progress;
    private Coords2D coordinates;

    public TramStatus(Line line, String pathId, Coords2D coordinates, double progress) {
        this.line = line;
        this.pathId = pathId;
        this.coordinates = coordinates;
        this.progress = progress;
    }
}
