package com.tram.network.simulation.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tram.network.simulation.data.CityMap;
import com.tram.network.simulation.data.CityMapBuilder;
import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import spark.ResponseTransformer;

import java.io.IOException;
import java.sql.Time;
import java.util.Map;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {


        GlobalTimer timer = new GlobalTimer();

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

        Map<String,Node> nodesMap = citymap.getNodesMap();

        nodesMap.get("Wzgorza Krzeslawickie").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );



        Thread thread = new Thread(new TimerGovernor(timer));
        thread.start();

        externalStaticFileLocation("src/main/resources/public/");

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final ResponseTransformer json = gson::toJson;

        port(4567);
        get("/trams", (req, res) -> timer.getTrams(), json);
        get("/time", (req, res) -> timer.getCurrentTime(), json);
        get("/step", (req, res) -> timer.getOneStepTime(), json);

    }
}