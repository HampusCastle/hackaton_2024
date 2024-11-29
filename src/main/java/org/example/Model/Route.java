package org.example.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class Route {

    private String id;
    private String documentType;
    private String travelType;
    private LocalDateTime departureDateTime;
    private String travelFrom;
    private String travelTo;
    private double price;
    private LocalTime travelTime;
    private String information;
    private String vehicleNumber;
    private boolean isDelayed;
    private LocalTime hoursDelayed;
    private LocalDateTime arrivalDateTime;

    public LocalTime totalTravelTime() {
        LocalTime totalTime = this.travelTime;
        if (isDelayed && hoursDelayed != null) {
            totalTime = totalTime.plusHours(hoursDelayed.getHour()).plusMinutes(hoursDelayed.getMinute());
        }
        return totalTime;
    }

    public LocalDateTime calculateArrivalTime() {
        LocalDateTime calculatedArrival = departureDateTime.plusHours(travelTime.getHour()).plusMinutes(travelTime.getMinute());
        if (isDelayed && hoursDelayed != null) {
            calculatedArrival = calculatedArrival.plusHours(hoursDelayed.getHour()).plusMinutes(hoursDelayed.getMinute());
        }
        return calculatedArrival;
    }

}