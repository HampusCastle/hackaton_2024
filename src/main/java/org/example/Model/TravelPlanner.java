package org.example.Model;

import java.time.LocalDateTime;
import java.util.*;

public class TravelPlanner {

    private final List<Route> allRoutes = new ArrayList<>();

    public void addRoute(Route route) {
        allRoutes.add(route);
    }

    public List<List<Route>> findAllRoutes(String start, String end) {
        List<List<Route>> allPossibleRoutes = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        findRoutes(start, end, new ArrayList<>(), visited, allPossibleRoutes);
        return allPossibleRoutes;
    }

    private void findRoutes(String start, String end, List<Route> currentRoute, Set<String> visited, List<List<Route>> allPossibleRoutes) {
        for (Route route : allRoutes) {
            if (route.getTravelFrom().equalsIgnoreCase(start) && !visited.contains(route.getTravelTo()) &&
                    isDateWithinRange(route.getDepartureDateTime())) {

                List<Route> newRoute = new ArrayList<>(currentRoute);
                newRoute.add(route);
                visited.add(route.getTravelTo());

                if (route.getTravelTo().equalsIgnoreCase(end)) {
                    allPossibleRoutes.add(newRoute);
                } else {
                    findRoutes(route.getTravelTo(), end, newRoute, visited, allPossibleRoutes);
                }

                visited.remove(route.getTravelTo());
            }
        }
    }


    private boolean isDateWithinRange(LocalDateTime departureDateTime) {
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 3, 23, 59, 59, 999999);
        return !departureDateTime.isBefore(startDate) && !departureDateTime.isAfter(endDate);
    }

    public List<List<Route>> groupRoutesByJourney(List<List<Route>> allRoutes) {
        List<List<Route>> groupedJourneys = new ArrayList<>();
        Set<String> seenJourneys = new HashSet<>();

        for (List<Route> routeList : allRoutes) {
            String journeyKey = getJourneyKey(routeList);
            if (!seenJourneys.contains(journeyKey)) {
                groupedJourneys.add(routeList);
                seenJourneys.add(journeyKey);
            }
        }
        return groupedJourneys;
    }

    private String getJourneyKey(List<Route> routeList) {
        StringBuilder journeyKey = new StringBuilder();
        for (Route route : routeList) {
            journeyKey.append(route.getTravelFrom()).append("->").append(route.getTravelTo()).append(";");
        }
        return journeyKey.toString();
    }

    public void printJourneys(List<List<Route>> groupedJourneys) {
        if (groupedJourneys != null && !groupedJourneys.isEmpty()) {
            for (List<Route> journey : groupedJourneys) {
                System.out.println("Journey from Sverige to Ecuador:");

                double totalPrice = 0;
                int totalTimeInSeconds = 0;

                for (Route route : journey) {
                    System.out.println("Travel from " + route.getTravelFrom() + " to " + route.getTravelTo());
                    System.out.println("Departure: " + route.getDepartureDateTime());
                    System.out.println("Total Travel Time: " + route.totalTravelTime());
                    System.out.println("Price: " + route.getPrice());
                    System.out.println("Arrival Time: " + route.calculateArrivalTime());
                    System.out.println("---------------");

                    totalPrice += route.getPrice();
                    totalTimeInSeconds += route.totalTravelTime().toSecondOfDay();
                }

                int totalHours = totalTimeInSeconds / 3600;
                int totalMinutes = (totalTimeInSeconds % 3600) / 60;

                System.out.println("Total price for the journey: " + totalPrice);
                System.out.println("Total travel time for the journey: " + totalHours + " hours and " + totalMinutes + " minutes");
                System.out.println("---------------");
            }
        } else {
            System.out.println("No valid journeys found.");
        }
    }
}