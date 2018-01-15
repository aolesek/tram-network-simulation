package com.tram.network.simulation.model;

import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import org.junit.Test;

public class TimetableParserTests {

    @Test
    public void testParsingTimetable() {
        TimetableFactory factory = new TimetableFactory(null);
        Timetable timetable = factory.construct("4 \n5  10 20 30 \n 6  10 14");
        System.out.println("timetable");
    }
}
