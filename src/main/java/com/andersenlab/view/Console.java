package com.andersenlab.view;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);
    private final HotelFactory hotelFactory = HotelFactory.getInstance();
    private final ClientService clientService = hotelFactory.getClientService();
    private final ApartmentService apartmentService = hotelFactory.getApartmentService();
    private final PerkService perkService = hotelFactory.getPerkService();

    public void start() {

        System.out.println("Hotel Administrator Alpha v0.1");
        System.out.println("Print 'help' for the list of commands");

        clientService.save("John", 1);
        clientService.save("Anny", 3);
        clientService.save("Demeter", 2);

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

            try {
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
            } catch (IllegalArgumentException e) {
                ConsolePrinter.illegalArgument();
            } catch (RuntimeException e) {
                ConsolePrinter.printError(e.getMessage());
            }
        }
    }

    /*
    *   client command list:
    *       client list / client getall
    *
    *       client get *client-id* +
    *       client debt *client-id* +
    *       client checkout *client-id* +
    *       client getperks *client-id* +
    *       client list *sort-type* / client getall *sort-type* +
    *
    *       client add *name* *quantity* +
    *       client serve *client-id* *perk-id* +
    *       client checkin *client-id* *stay-duration* +
    *
    *       client checkin *client-id* *stay-duration* *apartment-id*
    */

    private void clientCommand(String[] commandArray) {

        if (commandArray.length < 2) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        if ((commandArray[1].equals("list") || commandArray[1].equals("getall")) && commandArray.length == 2) {
            ConsolePrinter.printList(clientService.getAll());
            return;
        }

        if (commandArray.length < 3) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        switch (commandArray[1]) {
            case "get" -> {
                Client client = clientService.getById(Long.parseLong(commandArray[2]));
                ConsolePrinter.printEntity(client);
                return;
            }
            case "debt" -> {
                double debt = clientService.getStayCost(Long.parseLong(commandArray[2]));
                ConsolePrinter.printClientDebt(debt);
                return;
            }
            case "checkout" -> {
                ConsolePrinter.printCheckout(clientService.checkOutApartment(Long.parseLong(commandArray[2])));
                return;
            }
            case "getperks" -> {
                ConsolePrinter.printClientPerks(clientService.getAllPerks(Long.parseLong(commandArray[2])));
                return;
            }
            case "getall", "list" -> {
                List<Client> list = switch (commandArray[2]) {
                    case "id" -> clientService.getSorted(ClientService.ClientSortType.ID);
                    case "name" -> clientService.getSorted(ClientService.ClientSortType.NAME);
                    case "checkout" -> clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
                    case "status" -> clientService.getSorted(ClientService.ClientSortType.STATUS);
                    default -> throw new IllegalArgumentException();
                };
                ConsolePrinter.printList(list);
                return;
            }
        }

        if (commandArray.length < 4) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        switch (commandArray[1]) {
            case "add" -> {
                ConsolePrinter.printAddedClient(clientService.save(
                        commandArray[2],
                        Integer.parseInt(commandArray[3])));
                return;
            }
            case "serve" -> {
                ConsolePrinter.printServedPerk(clientService.addPerk(
                        Long.parseLong(commandArray[2]),
                        Long.parseLong(commandArray[3])));
                return;
            }
            case "checkin" -> {
                if (commandArray.length > 4) break;
                ConsolePrinter.printCheckIn(clientService.checkInApartment(
                        Long.parseLong(commandArray[2]),
                        Integer.parseInt(commandArray[3]),
                        0));
                return;
            }
        }

        if (commandArray[1].equals("checkin") && commandArray.length == 5) {
            ConsolePrinter.printCheckIn(clientService.checkInApartment(
                    Long.parseLong(commandArray[2]),
                    Integer.parseInt(commandArray[3]),
                    Long.parseLong(commandArray[4])));
            return;
        }

        ConsolePrinter.syntaxError();
    }

    /*
     *   apartment command list:
     *      apartment list / apartment getall
     *
     *      apartment get *client-id*
     *      apartment list *sort-type* / apartment getall *sort-type*
     *      apartment price *apartment-id*
     *      apartment changestatus *apartment-id*
     *
     *      apartment add *capacity* *price*
     *      apartment price *apartment-id* *new-price*
     */

    private void apartmentCommand(String[] commandArray) {
        if (commandArray.length < 2) {
            ConsolePrinter.insufficientArguments();
            return;
        }

        switch (commandArray.length) {
            case 2 -> {
                if (commandArray[1].equals("list")) {
                    ConsolePrinter.printList(apartmentService.getAll());
                }
            }
            case 3 -> {
                switch (commandArray[1]) {
                    case "get" -> {
                        ConsolePrinter.printEntity(apartmentService.getById(Long.parseLong(commandArray[2])));
                    }
                    case "getall", "list" -> {
                        List<Apartment> list = switch (commandArray[2]) {
                            case "id" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.ID);
                            case "price" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.PRICE);
                            case "capacity" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY);
                            case "status" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.STATUS);
                            default -> throw new IllegalArgumentException();
                        };
                        ConsolePrinter.printList(list);
                    }
                    case "price" -> {
                        ConsolePrinter.printApartmentPrice(apartmentService.getById(Long.parseLong(commandArray[2])));
                    }
                    case "changestatus" -> {
                        ConsolePrinter.printApartmentStatusChange(apartmentService.changeStatus(Long.parseLong(commandArray[2])));
                    }
                }
            }
            case 4 -> {
                switch (commandArray[1]) {
                    case "add" -> {
                        ConsolePrinter.printAddedApartment(apartmentService.save(
                                Integer.parseInt(commandArray[2]), Double.parseDouble(commandArray[3])));
                    }
                    case "price" -> {
                        ConsolePrinter.printApartmentPriceChange(apartmentService.changePrice(
                                Long.parseLong(commandArray[2]), Double.parseDouble(commandArray[3])));
                    }
                }
            }
            default -> ConsolePrinter.syntaxError();
        }
    }

    private void perkCommand(String[] commandArray) {
        System.out.println("perk command");
    }

    private boolean checkArgument(String argument, ArgumentType argumentType) {
        if (argumentType.equals(ArgumentType.ID) ||
                argumentType.equals(ArgumentType.DURATION) ||
                argumentType.equals(ArgumentType.CAPACITY) ||
                argumentType.equals(ArgumentType.QUANTITY)) {

            if (!argument.matches("-?\\d+")) {
                ConsolePrinter.illegalArgument();
                throw new IllegalArgumentException("Argument type isn't valid");
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
            if (argument.equals("id") ||
                argument.equals("name") ||
                argument.equals("checkout") ||
                argument.equals("status")) {
                return true;
            } else {
                ConsolePrinter.illegalArgument();
                return false;
            }

        }

        if (argumentType.equals(ArgumentType.APARTMENT_SORT_TYPE)) {
            if (argument.equals("id") ||
                argument.equals("price") ||
                argument.equals("capacity") ||
                argument.equals("status")) {
                return true;
            } else {
                ConsolePrinter.illegalArgument();
                return false;
            }
        }

        if (argumentType.equals(ArgumentType.PERK_SORT_TYPE)) {
            if (argument.equals("id") ||
                argument.equals("name") ||
                argument.equals("price")) {
                return true;
            } else {
                ConsolePrinter.illegalArgument();
                return false;
            }
        }

        if (argumentType.equals(ArgumentType.NAME)) {
            return true;
        }

        ConsolePrinter.syntaxError();
        return false;
    }

    private enum ArgumentType {
        NAME, ID, PRICE, CAPACITY, QUANTITY, DURATION, CLIENT_SORT_TYPE, APARTMENT_SORT_TYPE, PERK_SORT_TYPE
    }
}
