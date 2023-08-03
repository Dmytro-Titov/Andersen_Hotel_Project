package com.andersenlab.view;

import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class Console {
    private final Scanner scanner = new Scanner(System.in);
    private final HotelFactory hotelFactory = HotelFactory.getInstance();
    public void start() {
        System.out.println("Hotel Administrator Alpha v0.1");
        System.out.println("Print 'help' for the list of commands");

        loop:
        while (true) {
            String command = scanner.nextLine().toLowerCase().trim();
            String[] commandArray = command.split("\s+");
            System.out.println(Arrays.toString(commandArray));

            if (commandArray.length < 1) {
                ConsolePrinter.unknownCommand(command);
                continue;
            }

            switch (commandArray[0]) {
                case "exit":
                    ConsolePrinter.exit();
                    break loop;
                case "help":
                    ConsolePrinter.commands();
                    continue;
                case "client":
                    clientCommand(commandArray);
                    continue;
                case "apartment":
                    apartmentCommand(commandArray);
                    continue;
                case "perk":
                    perkCommand(commandArray);
                    continue;
            }

            ConsolePrinter.unknownCommand(command);
        }
    }

    private void clientCommand(String[] commandArray) {
        if (commandArray.length < 3) {
            ConsolePrinter.syntaxError();
            return;
        }

        switch (commandArray[1]) {
            case "get":
                if (checkArgument(commandArray[2], "id")) {
                    Client client = hotelFactory.getClientService().getById(Long.parseLong(commandArray[2]));
                    ConsolePrinter.printClient(client);
                }
                return;
            case "add":
                if (checkArgument(commandArray[2], "name")) {
                    // TODO: 03.08.2023 change request when service is fixed
                    /*Client client =*/ hotelFactory.getClientService().save(commandArray[2]);
                    //print message when client is returned
                }
                return;
            case "debt":
                if (checkArgument(commandArray[2], "id")) {
                    double debt = hotelFactory.getClientService().getStayCost(Long.parseLong(commandArray[2]));
                    ConsolePrinter.printClientDebt(debt);
                }
                return;
            case "checkout":
                return;
            case "getperks":
                return;
            case "getall":
            case "list":
                if (commandArray[2] == null) {
                    //need no sort getter
                    // TODO: 03.08.2023 change request when service is fixed
                    ConsolePrinter.printClients(hotelFactory.getClientService().sortByName());
                }

                if (checkArgument(commandArray[2], "clientSortType")) {
                    //need one sort getter with argument
                    // TODO: 03.08.2023 change request when service is fixed
                    ConsolePrinter.printClients(hotelFactory.getClientService().sortByName());
                }
                return;
        }

        if (commandArray.length < 4) {
            ConsolePrinter.syntaxError();
            return;
        }

        if (commandArray[1].equals("serve")) {

        }

        if (commandArray.length < 5) {

        }
    }

    private void apartmentCommand(String[] commandArray) {
        System.out.println("apartment command");
    }

    private void perkCommand(String[] commandArray) {
        System.out.println("perk command");
    }

    private boolean checkArgument(String argument, String expectedArgument) {
        if (expectedArgument.equals("id") || expectedArgument.equals("cost") || expectedArgument.equals("duration")) {
            if (!argument.matches("-?\\d+")) {
                ConsolePrinter.illegalArgument();
                return false;
            }

            if (Integer.parseInt(argument) < 1) {
                ConsolePrinter.negativeArgument();
                return false;
            }

            return true;
        }

        if (expectedArgument.equals("price")) {
            if (!argument.matches("-?\\d+\\.\\d+")) {
                ConsolePrinter.illegalArgument();
                return false;
            }

            if (Double.parseDouble(argument) < 0) {
                ConsolePrinter.negativeArgument();
                return false;
            }

            return true;
        }

        if (expectedArgument.equals("clientSortType")) {
            return argument.equals("sortbyname") ||
                    argument.equals("sortbycheckoutdate") ||
                    argument.equals("sortbystatus");
        }

        if (expectedArgument.equals("name")) {
            return true;
        }

        ConsolePrinter.syntaxError();
        return false;
    }

    private enum argumentType {
        NAME, ID, PRICE,
    }

    private enum sortType {
        NAME, ID, PRICE,
    }
}
