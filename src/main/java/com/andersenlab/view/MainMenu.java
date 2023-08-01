package com.andersenlab.view;

import com.andersenlab.factory.Factory;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("List of available command:");

        System.out.println("Add a client: click 1");
        System.out.println("Check in an apartment: click 2");
        System.out.println("Check out an apartment: click 3");
        System.out.println("Change apartment status to be unavailable: click 4");
        System.out.println("Change the price of an apartment: click 5");
        System.out.println("Add an apartment (can be of different capacity): click 6");
        System.out.println("List apartments sorted by price, capacity, availability: click 7");
        System.out.println("List clients sorted by name, by check out date, status: click 8");
        System.out.println("Add a service (e.g., flower arrangement, ironing, laundry): click 9");
        System.out.println("Change the price of a service: click 10");
        System.out.println("Apply a service for the client: click 11");
        System.out.println("List services sorted by name, price: click 12");
        System.out.println("Get the current price for the client’s stay: click 13");


        String input = scanner.nextLine();

        switch (input) {
            case "1":
                System.out.println("Please input the name");
                inputDataUser(scanner.nextLine());
                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
            case "5":

                break;
            case "6":

                break;
            case "7":

                break;
            case "8":
                getClients();
                break;
            case "9":

                break;
            case "10":

                break;

            case "11":

                break;

            case "12":

                break;

            case "13":

                break;

            default:
                System.out.println("The command was not recognize : " + input);
        }
    }

    private void inputDataUser(String input1){
        Factory.getInstance().getClientService().addClient(input1);
    }

    private void getClients(){
        Factory.getInstance().getClientService().getClients();
    }
}
