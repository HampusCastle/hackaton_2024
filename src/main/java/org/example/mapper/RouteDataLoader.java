package org.example.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Model.Route;

import java.io.File;
import java.util.List;

public class RouteDataLoader {
    public static List<Route> loadRoutes(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Route.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}