package com.tram.network.simulation.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OtherTests {

    @Test
    public void tmpTest() {
        List<Integer> departures = new ArrayList<>();

        departures.add(1);
        departures.add(2);
        departures.add(3);
        departures.add(4);

        System.out.println(departures);

        departures.remove(0);
        departures.remove(0);

        System.out.println(departures);
    }
}
