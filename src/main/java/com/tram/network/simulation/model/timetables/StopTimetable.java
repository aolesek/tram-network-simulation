package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.model.base.Timer;

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
