package com.andersenlab;

import com.andersenlab.config.Config;
import com.andersenlab.util.ConfigHandler;
import com.andersenlab.view.Console;

public class AdministratorApp {
    public static void main(String[] args) {
        var config = ConfigHandler.createConfig(args);
        Config.INSTANCE.setConfigData(config);

        System.out.println(Config.INSTANCE.getConfigData().getDatabase().getPath());


        new Console().start();
    }
}