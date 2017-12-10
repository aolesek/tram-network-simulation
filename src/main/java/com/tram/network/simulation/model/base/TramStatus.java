package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.geo.Coords2D;

public class TramStatus {
    private Line line;
    private String pathId;
    private Coords2D coordinates;

    public TramStatus(Line line, String pathId, Coords2D progress) {
        this.line = line;
        this.pathId = pathId;
        this.coordinates = progress;
    }
}
