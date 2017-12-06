package com.tram.network.simulation.model;

import com.tram.network.simulation.model.geo.GeoPath;
import org.junit.Test;

public class GeoTests {
    @Test
    public void DistanceTests() {
        GeoPath path = new GeoPath("49.9334083, 20.0361306; 49.9331472, 20.0394250");
        System.out.println(path.calculateLength()*100000);
    }
}
