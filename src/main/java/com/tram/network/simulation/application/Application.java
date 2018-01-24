package com.tram.network.simulation.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tram.network.simulation.data.CityMap;
import com.tram.network.simulation.data.CityMapBuilder;
import com.tram.network.simulation.data.GPSTrams;
import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.DepartureTime;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import spark.ResponseTransformer;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) throws InterruptedException {


        GlobalTimer timer = new GlobalTimer();
        timer.setCurrentTime(new DepartureTime(4, 35, 0));


        //Lines with inverted direction (to provide correct order of timetables)
        List<Integer> invertedOnes = new ArrayList<>();
        invertedOnes.add(2);
        invertedOnes.add(8);
        invertedOnes.add(9);
        invertedOnes.add(11);
        //invertedOnes.add(13);

        ApplicationUtils.invertedLines.addAll(invertedOnes);


        CityMap citymap = null;

        try {
            TimetableFactory timetableFactory = new TimetableFactory(timer);
            CityMapBuilder builder = new CityMapBuilder(timetableFactory);
            builder.readCSVMapFile("/tramnet.csv");
            citymap = new CityMap(
                    builder
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.setCityMap(citymap);

        Map<String, Node> nodesMap = citymap.getNodesMap();


        TimerGovernor governor = new TimerGovernor(timer);
//        governor.setBreakType(BreakType.REAL);
//        governor.setOneStepTime(ApplicationUtils.globalOneStepTime);
        governor.setBreakType(BreakType.FIXED);
        //governor.setTimeBreak(2);
        Thread thread = new Thread(governor);
        thread.start();
        startStop(governor);

        externalStaticFileLocation("src/main/resources/public/");

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final ResponseTransformer json = gson::toJson;

        port(4567);
        get("/trams", (req, res) -> timer.getTrams(), json);
        CityMap finalCitymap = citymap;
        get("/citymap", (req, res) -> finalCitymap, json);
        get("/time", (req, res) -> timer.getCurrentTime(), json);
        get("/step", (req, res) -> timer.getOneStepTime(), json);

        get("/startstop", (req, res) -> startStop(governor));
        get("/gpsTrams", (req, res) -> GPSTrams.getTrams(), json);
        get("/time/real", (req, res) -> {
            governor.setBreakType(BreakType.REAL);
            governor.setOneStepTime(ApplicationUtils.globalOneStepTime);
            return "ok";
        }, json);
        get("/time/up", (req, res) -> {
            governor.setBreakType(BreakType.FIXED);
            governor.speedUp();
            return governor.getTimeBreak();
        }, json);

        get("/time/down", (req, res) -> {
            governor.setBreakType(BreakType.REAL);
            governor.speedDown();
            return governor.getTimeBreak();
        }, json);

        get("/time/stoptime/:time", (req, res) -> {
            String time = req.params("time");

            ApplicationUtils.timeAtStop = Integer.parseInt(time);

            return ApplicationUtils.timeAtStop;
        }, json);
    }

    private static boolean startStop(TimerGovernor governor) throws InterruptedException {
        if (governor.pause == false) {
            governor.pause = true;
        } else {
            governor.pause = false;
        }
        return true;
    }
}