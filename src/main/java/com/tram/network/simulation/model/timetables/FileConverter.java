package com.tram.network.simulation.model.timetables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static spark.Spark.externalStaticFileLocation;

public class FileConverter {

    String timetable="";

    public static void main(String[] args) {
        FileConverter fileConverter = new FileConverter();
        String abc = fileConverter.fileToString("abc");
        System.out.print(abc);
    }

    public String fileToString(String name){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("./src/main/resources/"+name+".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                timetable +=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return timetable;
    }
}
