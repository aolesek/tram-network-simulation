package com.tram.network.simulation.model.geo;

public class Coords2D {
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double x, y;

    public Coords2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coords2D(String coordinates) {
        String [] xy = coordinates.split(", ");
        this.x = Double.parseDouble(xy[0]);
        this.y = Double.parseDouble(xy[1]);
    }

    public Coords2D(Coords2D coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }



    public Coords2D copy() {
        return new Coords2D(this.x, this.y);
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
