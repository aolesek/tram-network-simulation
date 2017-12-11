package com.tram.network.simulation.model.geo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoPath {
    List<Coords2D> points = new ArrayList<>();
    double length;

    public GeoPath(String stringPath) {
        String [] coordinates = stringPath.split("; ");

        for (String coordinate : coordinates) {
            String [] xy = coordinate.split(", ");
            Double x = Double.parseDouble(xy[0]);
            Double y = Double.parseDouble(xy[1]);
            points.add(
                    new Coords2D(x,y)
            );
        }

        this.length = calculateLength(points);
    }

    public GeoPath(List<Coords2D> pts, double len) {
        this.points = pts;
        this.length = len;
    }

    public double calculateLength(List<Coords2D> pts) {
        double distance = 0.0;

        if (pts.size() <= 1)
            return 0.0;

        Coords2D oldpoint = pts.get(0).copy();

        for(int i = 1; i < pts.size(); i++) {
            distance += distance(pts.get(i).getX(), oldpoint.getX(), pts.get(i).getY(), oldpoint.getY(), 0.0, 0.0);
            oldpoint = pts.get(i).copy();
        }

        return distance;
    }

    public GeoPath reverse() {
        List <Coords2D> newPoints = new ArrayList<>();
        newPoints.addAll(points);
        Collections.reverse(newPoints);

        return new GeoPath(newPoints, length);
    }

    public Coords2D getProgCoordinates(double progress) {
        double distanceFromStart = progress * length;
        int lastPassedPointNumber = 0;

        for (int i = 1; i <= points.size(); i++) {
            List<Coords2D> part = points.subList(0, i+1);

            if (distanceFromStart <= (calculateLength(part))) {
                lastPassedPointNumber = i - 1;

                break;
            }
        }

        Coords2D lastPoint = points.get(lastPassedPointNumber);
        Coords2D nextPoint = points.get(lastPassedPointNumber + 1);

        Vector2D vector = new Vector2D(lastPoint.getX(), lastPoint.getY(), nextPoint.getX(), nextPoint.getY());
        vector.normalize();

        double distanceFromLastPoint = distanceFromStart - calculateLength(points.subList(0,lastPassedPointNumber+1));
        if (distanceFromLastPoint < 0.0)
            distanceFromLastPoint = 0.0;

        vector.multiply(distanceFromLastPoint);

        return new Coords2D(lastPoint.getX() + vector.getX(), lastPoint.getY() + vector.getY());



    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}