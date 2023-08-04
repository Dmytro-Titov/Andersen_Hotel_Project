package com.andersenlab.view;

import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;

import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.Optional;
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
            // TODO: 04.08.2023 remove debug "command to console" vvv
            System.out.println(Arrays.toString(commandArray));

            if (commandArray.length < 1) {
                ConsolePrinter.insufficientArguments();
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
                default:
                    ConsolePrinter.unknownCommand(command);
            }
        }
    }

    private void clientCommand(String[] commandArray) {

        if (commandArray.length < 2) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        if (commandArray[1].equals("list") && commandArray.length == 2) {
            // need no sort getter or id sort getter
            // TODO: 03.08.2023 change request when service is fixed
            ConsolePrinter.printClients(hotelFactory.getClientService().sortByName());
            return;
        }

        if (commandArray.length < 3) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        switch (commandArray[1]) {
            case "get":
                if (checkArgument(commandArray[2], ArgumentType.ID)) {
                    Client client = hotelFactory.getClientService().getById(Long.parseLong(commandArray[2]));
                    ConsolePrinter.printClient(client);
                }
                return;
            case "add":
                if (checkArgument(commandArray[2], ArgumentType.NAME)) {
                    // TODO: 03.08.2023 change request when service is fixed
                    /*Client client =*/ hotelFactory.getClientService().save(commandArray[2]);
                    //print message when client is returned
                }
                return;
            case "debt":
                if (checkArgument(commandArray[2], ArgumentType.ID)) {
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

                }

                if (checkArgument(commandArray[2], ArgumentType.CLIENT_SORT_TYPE)) {
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

    private boolean checkArgument(String argument, ArgumentType argumentType) {
        if (argumentType.equals(ArgumentType.ID) ||
                argumentType.equals(ArgumentType.DURATION) ||
                argumentType.equals(ArgumentType.CAPACITY)) {

            if (!argument.matches("-?\\d+")) {
                ConsolePrinter.illegalArgument();
                return false;
            }

            if (Integer.parseInt(argument) < 1) {
                ConsolePrinter.lowArgumentValue();
                return false;
            }

            return true;
        }

        if (argumentType.equals(ArgumentType.PRICE)) {

            if (!argument.matches("-?\\d+\\.\\d+")) {
                ConsolePrinter.illegalArgument();
                return false;
            }

            if (Double.parseDouble(argument) < 0) {
                ConsolePrinter.lowArgumentValue();
                return false;
            }

            return true;
        }

        if (argumentType.equals(ArgumentType.CLIENT_SORT_TYPE)) {
            return argument.equals("id") ||
                    argument.equals("name") ||
                    argument.equals("checkout") ||
                    argument.equals("status");
        }

        if (argumentType.equals(ArgumentType.APARTMENT_SORT_TYPE)) {
            return argument.equals("id") ||
                    argument.equals("price") ||
                    argument.equals("capacity") ||
                    argument.equals("status");
        }

        if (argumentType.equals(ArgumentType.PERK_SORT_TYPE)) {
            return argument.equals("id") ||
                    argument.equals("name") ||
                    argument.equals("price");
        }

        if (argumentType.equals(ArgumentType.NAME)) {
            return true;
        }

        ConsolePrinter.syntaxError();
        return false;
    }

    private enum ArgumentType {
        NAME, ID, PRICE, CAPACITY, DURATION, CLIENT_SORT_TYPE, APARTMENT_SORT_TYPE, PERK_SORT_TYPE
    }
}
