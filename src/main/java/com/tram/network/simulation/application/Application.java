package com.tram.network.simulation.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.geo.GeoPath;
import com.tram.network.simulation.model.nodes.JunctionNode;
import com.tram.network.simulation.model.nodes.LoopNode;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.nodes.StopNode;
import com.tram.network.simulation.model.timetables.SimpleTimetable;
import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import spark.ResponseTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {


        GlobalTimer timer = new GlobalTimer();

        List<Path> paths = new ArrayList<>();

        // Loop 1
        TimetableFactory f1 = new TimetableFactory(timer);
        Timetable l1t1 = f1.construct("00 00\n8 00\n16 00");
        Timetable l1t2 = f1.construct("00 00\n8 00\n16 00");

        Map<Line,Timetable> timetables = new HashMap<>();
        timetables.put(new Line(1, LineDirection.NE), l1t1); //l1t1
        timetables.put(new Line(2, LineDirection.NE), l1t2 ); //l1t2

        Node l1 = new LoopNode("L1",timetables);

        // Junction 1

        Node j1 = new JunctionNode("J1");

        // Stop 1
        TimetableFactory f2 = new TimetableFactory(timer);
        Timetable s1t1 = f2.construct("5 00\n13 00\n21 00");
        Timetable s1t2 = f2.construct("3 00\n11 00\n19 00");

        Map<Line,Timetable> timetables2 = new HashMap<>();
        timetables2.put(new Line(1, LineDirection.NE), s1t1);
        timetables2.put(new Line(1, LineDirection.SW), s1t2);

        Node s1 = new StopNode("S1",timetables2);

        // Stop 2
        TimetableFactory f3 = new TimetableFactory(timer);
        Timetable s2t1 = f3.construct("5 00\n13 00\n21 00");
        Timetable s2t2 = f3.construct("3 00\n11 00\n19 00");

        Map<Line,Timetable> timetables3 = new HashMap<>();
        timetables3.put(new Line(1, LineDirection.NE), s2t1);
        timetables3.put(new Line(1, LineDirection.SW), s2t2);

        Node s2 = new StopNode("S2",timetables3);

        // Stop 3
        TimetableFactory f4 = new TimetableFactory(timer);
        Timetable s3t1 = f4.construct("4 00\n12 00\n20 00");
        Timetable s3t2 = f4.construct("4 00\n12 00\n20 00");

        Map<Line,Timetable> timetables4 = new HashMap<>();
        timetables4.put(new Line(2, LineDirection.NE), s3t1);
        timetables4.put(new Line(2, LineDirection.SW), s3t2);

        Node s3 = new StopNode("S3",timetables4);

        // Junction 2

        Node j2 = new JunctionNode("J2");

        // Loop 2
        TimetableFactory f5 = new TimetableFactory(timer);
        Timetable l2t1 = f5.construct("00 00\n8 00\n16 00");
        Timetable l2t2 = f5.construct("00 00\n8 00\n16 00");

        Map<Line,Timetable> timetables5 = new HashMap<>();
        timetables5.put(new Line(1, LineDirection.SW), l2t1);
        timetables5.put(new Line(2, LineDirection.SW), l2t2);

        Node l2 = new LoopNode("L2",timetables5);

        // PATHS

        //L1J1 Paths

        GeoPath l1j1g = new GeoPath("50.064378, 19.917861; 50.064273, 19.917574; 50.064331, 19.917507; 50.064419, 19.917387; 50.064489, 19.917262; 50.064529, 19.917168; 50.064562, 19.917054; 50.064672, 19.916475; 50.064394, 19.916399; 50.064165, 19.916448; 50.063996, 19.916549");
        ArrayList<Line> l1j1lines = new ArrayList<>();
        l1j1lines.add(new Line(1, LineDirection.NE));
        l1j1lines.add(new Line(2, LineDirection.NE));
        paths.add(
                new Path(10,2,7,l1,j1, l1j1lines, l1j1g)
        );
        ArrayList<Line> j1l1lines = new ArrayList<>();
        j1l1lines.add(new Line(1, LineDirection.SW));
        j1l1lines.add(new Line(2, LineDirection.SW));
        paths.add(
                new Path(10,2,7,j1,l1, j1l1lines, l1j1g.reverse())
        );

        //J1S1 PATHS
        GeoPath j1s1g = new GeoPath("50.063996, 19.916549; 50.063864, 19.916754; 50.063382, 19.917268; 50.063250, 19.917375; 50.063087, 19.917414; 50.062907, 19.917447; 50.062867, 19.917627");
        ArrayList<Line> j1s1lines = new ArrayList<>();
        j1s1lines.add(new Line(1, LineDirection.NE));
        paths.add(
                new Path(8,2,7,j1,s1, j1s1lines, j1s1g)
        );

        ArrayList<Line> s1j1lines = new ArrayList<>();
        s1j1lines.add(new Line(1, LineDirection.SW));
        paths.add(
                new Path(8,2,7,s1,j1, s1j1lines, j1s1g.reverse())
        );

        //J1S3 PATHS
        GeoPath j1s3g = new GeoPath("50.063996, 19.916549; 50.063175, 19.916585; 50.062748, 19.916529; 50.062321, 19.916474; 50.062255, 19.916487");
        ArrayList<Line> j1s3lines = new ArrayList<>();
        j1s3lines.add(new Line(2, LineDirection.NE));
        paths.add(
                new Path(10,2,7,j1,s3, j1s3lines, j1s3g)
        );

        ArrayList<Line> s3j1lines = new ArrayList<>();
        s3j1lines.add(new Line(2, LineDirection.SW));
        paths.add(
                new Path(10,2,7,s3,j1, s3j1lines, j1s3g.reverse())
        );

        //s1s2 PATHS
        GeoPath s1s2g = new GeoPath("50.062867, 19.917627; 50.062770, 19.917703; 50.062655, 19.917650; 50.062603, 19.917496; 50.062414, 19.917520; 50.062272, 19.917543");
        ArrayList<Line> s1s2lines = new ArrayList<>();
        s1s2lines.add(new Line(1, LineDirection.NE));
        paths.add(
                new Path(4,2,7,s1,s2, s1s2lines, s1s2g)
        );

        ArrayList<Line> s2s1lines = new ArrayList<>();
        s2s1lines.add(new Line(1, LineDirection.SW));
        paths.add(
                new Path(4,2,7,s2,s1, s2s1lines, s1s2g.reverse())
        );

        //s2j2 PATHS
        GeoPath s2j2g = new GeoPath("50.062272, 19.917543; 50.062106, 19.917569; 50.061092, 19.917729; 50.060880, 19.917767");
        ArrayList<Line> s2j2lines = new ArrayList<>();
        s2j2lines.add(new Line(1, LineDirection.NE));
        paths.add(
                new Path(8,2,7,s2,j2, s2j2lines, s2j2g)
        );

        ArrayList<Line> j2s2lines = new ArrayList<>();
        j2s2lines.add(new Line(1, LineDirection.SW));
        paths.add(
                new Path(8,2,7,j2,s2, j2s2lines, s2j2g.reverse())
        );

        //s3j2 PATHS
        GeoPath s3j2g = new GeoPath("50.062867, 19.917627; 50.062770, 19.917703; 50.062655, 19.917650; 50.062603, 19.917496; 50.062414, 19.917520; 50.062272, 19.917543");
        ArrayList<Line> s3j2lines = new ArrayList<>();
        s3j2lines.add(new Line(2, LineDirection.NE));
        paths.add(
                new Path(10,2,7,s3,j2, s3j2lines, s3j2g)
        );

        ArrayList<Line> j2s3lines = new ArrayList<>();
        j2s3lines.add(new Line(2, LineDirection.SW));
        paths.add(
                new Path(10,2,7,j2,s3, j2s3lines, s3j2g.reverse())
        );

        //j2l2 PATHS
        GeoPath j2l2g = new GeoPath("50.060880, 19.917767; 50.060366, 19.917849; 50.060098, 19.919571");
        ArrayList<Line> j2l2lines = new ArrayList<>();
        j2l2lines.add(new Line(1, LineDirection.NE));
        j2l2lines.add(new Line(2, LineDirection.NE));
        paths.add(
                new Path(10,2,7,j2,l2, j2l2lines, j2l2g)
        );

        ArrayList<Line> l2j2lines = new ArrayList<>();
        l2j2lines.add(new Line(1, LineDirection.SW));
        l2j2lines.add(new Line(2, LineDirection.SW));

        paths.add(
                new Path(10,2,7,l2, j2, l2j2lines, j2l2g.reverse())
        );

        //Simple network is now initialized.
        //Adding initial trams

        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
        );

        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );
        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );

        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
        );
        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
        );
        l1.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
        );

        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );
        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );
        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );

        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
        );
        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
        );
        l2.addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
        );

        timer.setPathNetwork(paths);



        Thread thread = new Thread(new TimerGovernor(timer));
        thread.start();




        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final ResponseTransformer json = gson::toJson;

        port(4567);

        get("/trams", (req, res) -> timer.getTrams(), json);
        get("/time", (req, res) -> timer.getCurrentTime(), json);
        get("/step", (req, res) -> timer.getOneStepTime(), json);

    }
}