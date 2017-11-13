package com.tram.network.simulation.application;

import com.tram.network.simulation.model.*;

public class Application {
    public static void main(String [] args)
    {
        Path path = new Path(50,3,7,null,null,null);
        path.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        System.out.println(path);
        path = path.nextState();
        System.out.println(path);
        path = path.nextState();
        System.out.println(path);
        path = path.nextState();
        System.out.println(path);


    }
}
