package com.tram.network.simulation.model.base;

import com.tram.network.simulation.model.timetables.DepartureTime;

import java.util.List;

public class GlobalTimer implements Timer {
    private List<Path> pathNetwork;
    private DepartureTime currentTime = new DepartureTime(0,0);
    private Integer oneStepTime = 1;


    public GlobalTimer(List<Path> pathNetwork) {
        this.pathNetwork = pathNetwork;
    }

    public void nextState() {
        currentTime = currentTime.addMinutes( oneStepTime);
        for (Path path : pathNetwork) {
            path = path.nextState();
        }
    }


    @Override
    public DepartureTime getCurrentTime() {
        return currentTime;
    }
}
