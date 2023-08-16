package com.andersenlab.util;

import com.andersenlab.config.Config;
import com.andersenlab.factory.HotelFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtils {
    public static HotelFactory getHotelFactoryInstance() {
        var configData = ConfigHandler.createConfig("src/main/resources/config/config-dev.yaml");
        Config config = new Config();
        config.setConfigData(configData);
        HotelFactory hotelFactory = new HotelFactory(config);
        return hotelFactory;
    }
    public static String readBody(BufferedReader reader) throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        return jsonBody.toString();
    }
}
