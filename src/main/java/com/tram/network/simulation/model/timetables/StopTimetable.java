package com.tram.network.simulation.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StopTimetable implements Timetable {


    private List<DepartureTime> departures = new ArrayList<>();
    private Timer globalTimer;

    @Override
    public Boolean isItDepartureTime() {
        return  globalTimer.getCurrentTime().isGreaterThan( departures.get(0) );
    }

    @Override
    public void tramDeparted() {
        if (!departures.isEmpty())
            departures.remove(0);
    }
}
