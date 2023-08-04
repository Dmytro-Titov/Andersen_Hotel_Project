package com.andersenlab.view;

import com.andersenlab.entity.Client;

import java.util.List;

public class ConsolePrinter {

    public static void exit() {
        System.out.print("Exiting program...");
    }

    public static void printClient(Client client) {
        System.out.println(client == null ? "Client with this id not found." : client);
    }

    public static void printAddedClient(Client client) {
        System.out.printf("You added new client! Name: %s, id: %s\n", client.getName(), client.getId());
    }

    public static void printClientDebt(double debt) {
        if (debt == 0) {
            System.out.println("This client has no debt yet!");
        } else {
            System.out.printf("Current debt for this client is %s\n", debt);
        }
    }


    public static void printClients(List<Client> clientList) {
        if (clientList.size() == 0) {
            System.out.println("There are no clients.");
        } else {
            for (Client client : clientList) {
                System.out.println(client);
            }
        }
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
        System.out.print("This argument is illegal.\s");
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

    public static void typeHelp() {
        System.out.println("Type 'help' for instructions.");
    }
}
