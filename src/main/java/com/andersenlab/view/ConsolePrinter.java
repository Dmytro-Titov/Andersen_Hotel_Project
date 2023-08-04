package com.andersenlab.view;

import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.List;

public class ConsolePrinter {

    public static void exit() {
        System.out.print("Exiting program...");
    }

    public static void printClient(Client client) {
        System.out.println(client);

    }

    public static void printAddedClient(Client client) {
        System.out.printf("You added new client! Name: %s, id: %s, Quantity: %s\n",
                client.getName(), client.getId(), client.getQuantityOfPeople());
    }

    public static void printClientDebt(double debt) {
        if (debt == 0) {
            System.out.println("This client has no debt yet!");
        } else {
            System.out.printf("Current debt for this client is %s\n", debt);
        }
    }

    public static void printServedPerk(Perk perk) {
        System.out.printf("You served perk '%s' for the client!\n", perk.getName());
    }

    public static void printClientPerks(List<Perk> list) {
        if (list.size() == 0) {
            System.out.println("This client has no ordered perks yet!");
        } else {
            for (Perk perk : list) {
                System.out.println(perk);
            }
        }
    }


    public static void printClients(List<Client> clientList) {
        if (clientList.size() == 0) {
            System.out.println("There are no clients yet!");
        } else {
            for (Client client : clientList) {
                System.out.println(client);
            }
        }
    }

    public static void printCheckIn(Client client) {
        System.out.printf("Client with id: %s was successfully checked-in in apartment %s!",
                client.getId(), client.getApartment().getId());
    }

    public static void printCheckout(double debt) {
        System.out.printf("Client successfully checked-out. Their current bill is: %s\n", debt);
    }

    public static void insufficientArguments() {
        System.out.print("Not enough arguments provided.\s");
        typeHelp();
    }

    public static void lowArgumentValue() {
        System.out.print("Argument value cannot be so low.\s");
        typeHelp();
    }

    public static void illegalArgument() {
        System.out.print("Illegal argument.\s");
        typeHelp();
    }

    public static void commands() {
        System.out.println("full list of commands yet to be implemented");

    }

    public static void unknownCommand(String command) {
        System.out.printf("Command '%s' not recognized.\s", command);
        typeHelp();
    }

    public static void syntaxError() {
        System.out.print("Syntax error.\s");
        typeHelp();
    }

    public static void printError(String error) {
        System.out.println(error);
    }

    public static void typeHelp() {
        System.out.println("Type 'help' for instructions.");
    }
}
