package com.tram.network.simulation.model.timetables;

import java.util.ArrayList;
import java.util.List;

public class TimetableFactory {

    public Timetable construct(String departures) {
        String [] lines = departures.split("\n");
        List<DepartureTime> timetable = new ArrayList<>();


        for (String line : lines) {
            String [] tokens = line.split(" ");

            if (tokens.length > 1) {
                for (int i = 1; i < tokens.length; i++) {
                    timetable.add(new DepartureTime(
                            Integer.parseInt(tokens[0]),
                            Integer.parseInt(tokens[i])
                    ));
                }
            }
        }
        System.out.println(timetable);
        return new StopTimetable(timetable, null);

    }
}