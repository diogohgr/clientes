package br.com.uol.cliente.model;

import java.util.List;

public class Weather {

    private List<WeatherData> consolidated_weather;

    public Weather() {

    }

    public List<WeatherData> getConsolidated_weather() {
        return consolidated_weather;
    }

    public void setConsolidated_weather(List<WeatherData> consolidated_weather) {
        this.consolidated_weather = consolidated_weather;
    }

    @Override
    public String toString() {
        return "Weather [consolidated_weather=" + consolidated_weather + "]";
    }
}
