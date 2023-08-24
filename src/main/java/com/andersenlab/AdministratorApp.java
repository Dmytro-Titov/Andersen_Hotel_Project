package com.andersenlab;

import com.andersenlab.config.Config;
import com.andersenlab.dao.onDiskImpl.OnDiskJsonHandler;
import com.andersenlab.util.ConfigHandler;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.view.Console;

public class AdministratorApp {
    public static void main(String[] args) {
        String configPath = args.length >= 1 ? args[0] : null;
        Config config = new Config(configPath);
        HotelFactory hotelFactory = new HotelFactory(config);
        OnDiskJsonHandler onDiskJsonHandler = new OnDiskJsonHandler(config.getConfigData().getDatabase().getPath());

        onDiskJsonHandler.load();
        new Console(hotelFactory).start();
    }
}