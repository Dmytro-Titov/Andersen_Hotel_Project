package com.andersenlab;

import com.andersenlab.factory.HotelFactory;

import java.util.Scanner;

public class AdministratorApp {


    public static void main(String[] args) {
        start();
    }

    private static void start() {

        HotelFactory hotelFactory = HotelFactory.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hotel Administrator Alpha v0.1");
        System.out.println("Print 'help' for the list of commands");

        loop:
        while (true) {

            String command = scanner.nextLine().toLowerCase().trim();
            String[] commandArray = command.split("\s");


            switch (commandArray[0]) {
                case "help":
                    System.out.println("list of commands"); //full list of commands
                    continue;
                case "client":
                    hotelFactory.getClientService().run(command);
                    continue;
                case "apartment":
                    hotelFactory.getApartmentService().run(command);
                    continue;
                //etc
                case "exit":
                    System.out.println("Exiting program");
                    break loop;
            }

            System.out.println("Wrong command. Please try again or type 'help' for instructions");
        }
    }
}