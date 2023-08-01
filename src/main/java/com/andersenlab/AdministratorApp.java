package com.andersenlab;

import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import jdk.jshell.spi.ExecutionControlProvider;

import java.util.Scanner;

public class AdministratorApp {
    public static void main(String[] args) {
        boolean run = true;
        ClientService clientService = new ClientService();
        ApartmentService apartmentService = new ApartmentService();
        PerkService perkService = new PerkService();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1)Add some apartments (double price, int capacity)" +
                "2)Add some perks (String name, double price)" +
                "3)Add clients (String name)" +
                "4)List clients sorted by 'name', by check out 'date', 'status' (String)" +
                "5)List apartments sorted by 'price', 'capacity', 'availability' (String)" +
                "6)List services sorted by name, price" +
                "7)Check in an apartment (clientID , apartmentID)" +
                "8)Order perks (clientID , perkID) " +
                "9)Get the current price for the client’s stay (clientID)");
        try {
            while (run) {

                String order = scanner.next();

                //(add, sort, )
                switch (order) {

                    //apartment, perk, client
                    case "add":
                        order = scanner.next();
                        switch (order) {
                            case "apartment":
                                apartmentService.createAndAddNewApartment(scanner.nextDouble(), scanner.nextInt());
                                System.out.println(apartmentService.showAllApartments(1));
                                break;
                            case "perk":
                                break;
                            case "client":
                                break;

                        }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}