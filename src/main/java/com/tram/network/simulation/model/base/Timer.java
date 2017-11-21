package com.tram.network.simulation.model;

import com.tram.network.simulation.model.timetables.DepartureTime;

public interface Timer {

    public DepartureTime getCurrentTime();

}
