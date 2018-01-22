package com.tram.network.simulation.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tram.network.simulation.model.base.TramStatus;
import com.tram.network.simulation.model.geo.Coords2D;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GPSTrams {

    public static List<TramStatus> getTrams() {
        List<TramStatus> trams = new ArrayList<>();

        String sURL = "http://www.ttss.krakow.pl/internetservice/geoserviceDispatcher/services/vehicleinfo/vehicles?positionType=CORRECTED&colorType=ROUTE_BASED"; //just a string

        URL url = null;
        try {
            url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            JsonArray vehicles = rootobj.getAsJsonArray("vehicles");

            for (int i = 0; i < vehicles.size(); i++) {
                JsonObject vehicle = vehicles.get(i).getAsJsonObject();
                if ( vehicle.get("name") !=null ) {
                    String name = vehicle.get("name").getAsString();
                    double longitude = vehicle.get("longitude").getAsDouble() / 3600000.0;
                    double latitude = vehicle.get("latitude").getAsDouble() / 3600000.0;
                    TramStatus gpsTram = new TramStatus(null,name,new Coords2D(longitude,latitude),0.0,null);
                    trams.add(gpsTram);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trams;

    }
}
