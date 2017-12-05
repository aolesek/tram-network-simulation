package com.tram.network.simulation.model.base;

public class TramStatus {
    private Line line;
    private String pathId;
    private double progress;

    public TramStatus(Line line, String pathId, double progress) {
        this.line = line;
        this.pathId = pathId;
        this.progress = progress;
    }
}
