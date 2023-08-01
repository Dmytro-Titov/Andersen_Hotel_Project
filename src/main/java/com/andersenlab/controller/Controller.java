package com.andersenlab.controller;

import com.andersenlab.view.MainMenu;

public class Controller {
    MainMenu mainMenu = new MainMenu();
    public void run(){
        while (true){
            mainMenu.run();
        }
    }
}
