package com.andersenlab;

import com.andersenlab.factory.HotelFactory;
import com.andersenlab.view.Console;

public class AdministratorApp {
    public static void main(String[] args) {
        new Console(new HotelFactory()).start();
    }
}