package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.nodes.Node;

public class SimpleTimetable implements Timetable {
    @Override
    public Boolean isItDepartureTime() {
        return true;
    }

    @Override
    public void tramDeparted() {

    }

    @Override
    public Boolean isThereADelay(Line line, String node) {
        return false;
    }
}
