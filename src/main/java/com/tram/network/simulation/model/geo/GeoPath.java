package com.tram.network.simulation.model.geo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

public class GeoPath {
    List<Point> points = new ArrayList<>();

    public GeoPath(String stringPath) {
        GeometryFactory geometryFactory = new GeometryFactory();
        String [] coordinates = stringPath.split("; ");

        for (String coordinate : coordinates) {
            String [] xy = coordinate.split(", ");
            Double x = Double.parseDouble(xy[0]);
            Double y = Double.parseDouble(xy[1]);
            points.add(
                    geometryFactory.createPoint(new Coordinate(x,y))
            );
        }
    }

    public double calculateLength() {
        double distance = 0;
        Point oldpoint = points.get(0).copy();

        for(Point point : points) {
            distance += point.distance(oldpoint);
            oldpoint = point.copy();
        }

        return distance;
    }

}