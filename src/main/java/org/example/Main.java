package org.example;

import org.example.Model.Route;
import org.example.Model.TravelPlanner;
import org.example.mapper.RouteDataLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TravelPlanner planner = new TravelPlanner();

        List<Route> routes = RouteDataLoader.loadRoutes("library_data.json");
        routes.forEach(planner::addRoute);

        List<List<Route>> allRoutes = planner.findAllRoutes("Sverige", "Ecuador");

        List<List<Route>> groupedJourneys = planner.groupRoutesByJourney(allRoutes);

        planner.printJourneys(groupedJourneys);
    }
}