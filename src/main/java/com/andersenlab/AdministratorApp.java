package com.andersenlab;

import com.andersenlab.util.ConfigHandler;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.JsonHandler;
import com.andersenlab.util.JsonHandlerImp;
import com.andersenlab.view.Console;

public class AdministratorApp {
    public static void main(String[] args) {
        String configPath = args.length >= 1 ? args[0] : null;
        var config = ConfigHandler.createConfig(configPath);

        HotelFactory hotelFactory = new HotelFactory();
        hotelFactory.getConfig().setConfigData(config);
        JsonHandler jsonHandler = new JsonHandlerImp(hotelFactory);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                jsonHandler.save();
            } catch (Exception e) {
                throw new RuntimeException("Error occurred while exiting the program");
            }
        }));

        jsonHandler.load();
        new Console(hotelFactory).start();
    }
}