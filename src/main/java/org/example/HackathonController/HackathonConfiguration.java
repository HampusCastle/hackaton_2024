package org.example.HackathonController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.Model.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HackathonConfiguration {


    private static final HttpGet REQUEST = new HttpGet("http://localhost:8080/SortingTheLibrary/library");
    private final HttpClient httpClient = HttpClients.createDefault();


    public boolean returnStatusCodeForHttpResponse(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == 200;
    }


    public String createResponseForDataGet() throws IOException {

        HttpResponse respone = httpClient.execute(REQUEST);
        if (returnStatusCodeForHttpResponse(respone)) {
            return EntityUtils.toString(respone.getEntity(), "UTF-8");
        }
        return "Can't allocate any data";
    }


    public List<Route> returnDataForRouteSelection() {
        List<Route> trips = new ArrayList<>();
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        try {
            String json = createResponseForDataGet();
            Route[] root = om.readValue(json, Route[].class);

            trips.addAll(Arrays.asList(root));

        } catch (IOException e) {
            System.out.println("Error while initiating data from json string");
        }

        return trips;
    }



}
