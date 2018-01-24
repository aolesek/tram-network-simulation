package com.tram.network.simulation.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ApplicationUtils {
    public volatile static int timeAtStop = 0;
    public volatile static int globalOneStepTime = 5;
    public volatile static int defaultTimeBreak = 200;
    public volatile static double normalTrafficVelocityFactor = 2.2;
    public volatile static double highTrafficVelocityFactor = 1.2;
    public volatile static int currentId = 0;
    public volatile static List<String> missingTramNodes = new ArrayList<>();
    public volatile static List<Integer> invertedLines = new ArrayList<>();


    public static int getId() {
        currentId++;
        return currentId;
    }

    public static boolean isInverted(int lineNumber) {
        List<Integer> sameLine = invertedLines.stream().filter(t -> t.equals(lineNumber)).collect(Collectors.toList());
        if (sameLine.isEmpty())
            return false;
        return true;
    }
}
