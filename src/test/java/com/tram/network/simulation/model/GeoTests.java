package com.tram.network.simulation.model;

import com.tram.network.simulation.model.geo.GeoPath;
import org.junit.Test;

public class GeoTests {
    @Test
    public void DistanceTests() {
        GeoPath path = new GeoPath("50.065052, 19.916607; 50.064671, 19.916474");
        System.out.println(path.getProgCoordinates(0.25));
    }
}
