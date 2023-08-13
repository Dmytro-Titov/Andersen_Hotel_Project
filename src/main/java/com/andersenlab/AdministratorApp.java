package com.andersenlab;

import com.andersenlab.config.Config;
import com.andersenlab.dao.onDiskImpl.JsonHandler;
import com.andersenlab.dao.onDiskImpl.JsonHandlerImp;
import com.andersenlab.util.ConfigHandler;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.view.Console;

public class AdministratorApp {
    public static void main(String[] args) {
        String configPath = args.length >= 1 ? args[0] : null;
        var config = ConfigHandler.createConfig(configPath) ;
        Config.INSTANCE.setConfigData(config);

        HotelFactory hotelFactory = new HotelFactory();
        JsonHandler jsonHandler = new JsonHandlerImp();

        jsonHandler.load();
        new Console(hotelFactory).start();
    }
}