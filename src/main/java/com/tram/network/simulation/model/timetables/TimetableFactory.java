package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.model.base.GlobalTimer;

import java.util.ArrayList;
import java.util.List;

public class TimetableFactory {

    private GlobalTimer timer;

    public TimetableFactory(GlobalTimer timer) {
        this.timer = timer;
    }

    public Timetable construct(String departures) {
        String [] lines = departures.split(" ?\\r?\\n ?");
        List<DepartureTime> timetable = new ArrayList<>();

        for (String line : lines) {
            line.replaceAll("  ", " ");
            String [] tokens = line.split(" ");

            if (tokens.length > 1) {
                for (int i = 1; i < tokens.length; i++) {
                    if (!(tokens[0].isEmpty() || tokens[i].isEmpty()))
                    timetable.add(new DepartureTime(
                            Integer.parseInt(tokens[0]),
                            Integer.parseInt(tokens[i])
                    ));
                }
            }
        }
        //System.out.println(timetable);
        return new StopTimetable(timetable, timer);

    }
}
