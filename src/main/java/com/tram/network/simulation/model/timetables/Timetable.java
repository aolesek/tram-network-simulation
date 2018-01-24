package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.nodes.Node;

public interface Timetable {
    public Boolean isItDepartureTime() ;
    public void tramDeparted();
    public Boolean isThereADelay(Line line, String node);
}
