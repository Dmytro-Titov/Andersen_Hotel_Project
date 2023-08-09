package com.andersenlab.view;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.JsonHandler;

import java.util.List;
import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final ApartmentService apartmentService;
    private final PerkService perkService;
    private final JsonHandler jsonHandler;



    public Console() {
        HotelFactory hotelFactory = new HotelFactory();
        clientService = hotelFactory.getClientService();
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();

        jsonHandler = new JsonHandler(hotelFactory);

//        clientService.save("Denis", 1);
//        clientService.save("Dima", 2);
//        clientService.save("Nick", 1);
//        apartmentService.save(1, 100);
//        apartmentService.save(2, 150);
//        apartmentService.save(1, 100);
//        perkService.save("minibar", 33.5);
//        perkService.save("ironing", 15);
//        perkService.save("breakfast_in_apartment", 20);




    }

    public void start() {


        System.out.println("Hotel Administrator Alpha v0.1");
        System.out.println("Print 'help' for the list of commands");

        loop:
        while (true) {
            String command = scanner.nextLine().trim();
            String[] commandArray = command.split("\s+");

            if (commandArray.length < 1) {
                ConsolePrinter.insufficientArguments();
                continue;
            } else {
                commandArray[0] = commandArray[0].toLowerCase();
            }

            for (String s : commandArray) {
                if (negativeCheck(s)) {
                    ConsolePrinter.negativeArgumentValue();
                    continue loop;
                }
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
                        executeCommand(commandArray, CommandType.CLIENT);
                        continue;
                    case "apartment":
                        executeCommand(commandArray, CommandType.APARTMENT);
                        continue;
                    case "perk":
                        executeCommand(commandArray, CommandType.PERK);
                        continue;
                    case "save":
                        jsonHandler.save();
                        continue;
                    case "load":
                        jsonHandler.load();
                        continue;
                    default:
                        ConsolePrinter.unknownCommand(command);
                }
            } catch (NumberFormatException e) {
                ConsolePrinter.illegalArgument();
            } catch (IllegalArgumentException e) {
                ConsolePrinter.illegalArgumentWithMsg(e.getMessage());
            } catch (RuntimeException e) {
                ConsolePrinter.printError(e.getMessage());
            }
        }
    }

    private void executeCommand(String[] commandArray, CommandType type) {
        if (commandArray.length < 2) {
            ConsolePrinter.insufficientArguments();
            return;
        } else {
            commandArray[1] = commandArray[1].toLowerCase();
        }

        switch (type) {
            case CLIENT -> clientCommand(commandArray);
            case APARTMENT -> apartmentCommand(commandArray);
            case PERK -> perkCommand(commandArray);
        }
    }

    /*
    *   client command list:
    *       client list
    *
    *       client get *client-id* +
    *       client debt *client-id* +
    *       client checkout *client-id* +
    *       client getperks *client-id* +
    *       client list *sort-type* +
    *
    *       client add *name* *quantity* +
    *       client serve *client-id* *perk-id* +
    *       client checkin *client-id* *stay-duration* +
    *
    *       client checkin *client-id* *stay-duration* *apartment-id*
    */

    private void clientCommand(String[] commandArray) {
        switch (commandArray.length) {
            case 2 -> {
                if (commandArray[1].equals("list")) {
                    ConsolePrinter.printList(clientService.getAll());
                } else {
                    throw new IllegalArgumentException();
                }
            }
            case 3 -> {
                switch (commandArray[1]) {
                    case "get" ->
                        ConsolePrinter.printEntity(clientService.getById(Long.parseLong(commandArray[2])));
                    case "debt" ->
                        ConsolePrinter.printClientDebt(clientService.getStayCost(Long.parseLong(commandArray[2])));
                    case "checkout" ->
                        ConsolePrinter.printCheckout(clientService.checkOutApartment(Long.parseLong(commandArray[2])));
                    case "getperks" ->
                        ConsolePrinter.printClientPerks(clientService.getAllPerks(Long.parseLong(commandArray[2])));
                    case "list" -> {
                        List<Client> list = switch (commandArray[2].toLowerCase()) {
                            case "id" -> clientService.getSorted(ClientService.ClientSortType.ID);
                            case "name" -> clientService.getSorted(ClientService.ClientSortType.NAME);
                            case "checkout" -> clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
                            case "status" -> clientService.getSorted(ClientService.ClientSortType.STATUS);
                            default -> throw new IllegalArgumentException();
                        };
                        ConsolePrinter.printList(list);
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
            case 4 -> {
                switch (commandArray[1]) {
                    case "add" -> {
                        nullCheck(commandArray[3]);
                        ConsolePrinter.printAddedClient(clientService.save(
                                commandArray[2],
                                Integer.parseInt(commandArray[3])));
                    }
                    case "serve" ->
                        ConsolePrinter.printServedPerk(clientService.addPerk(
                                Long.parseLong(commandArray[2]),
                                Long.parseLong(commandArray[3])));
                    case "checkin" -> {
                        nullCheck(commandArray[3]);
                        ConsolePrinter.printCheckIn(clientService.checkInApartment(
                                Long.parseLong(commandArray[2]),
                                Integer.parseInt(commandArray[3]),
                                0));
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
            case 5 -> {
                if (commandArray[1].equals("checkin")) {
                    nullCheck(commandArray[3]);
                    ConsolePrinter.printCheckIn(clientService.checkInApartment(
                            Long.parseLong(commandArray[2]),
                            Integer.parseInt(commandArray[3]),
                            Long.parseLong(commandArray[4])));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            default -> ConsolePrinter.syntaxError();
        }
    }

    /*
     *   apartment command list:
     *      apartment list
     *
     *      apartment get *client-id*
     *      apartment list *sort-type*
     *      apartment price *apartment-id*
     *      apartment changestatus *apartment-id*
     *
     *      apartment add *capacity* *price*
     *      apartment price *apartment-id* *new-price*
     */

    private void apartmentCommand(String[] commandArray) {
        switch (commandArray.length) {
            case 2 -> {
                if (commandArray[1].equals("list")) {
                    ConsolePrinter.printList(apartmentService.getAll());
                } else throw new IllegalArgumentException();
            }
            case 3 -> {
                switch (commandArray[1]) {
                    case "get" ->
                        ConsolePrinter.printEntity(apartmentService.getById(Long.parseLong(commandArray[2])));
                    case "list" -> {
                        List<Apartment> list = switch (commandArray[2].toLowerCase()) {
                            case "id" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.ID);
                            case "price" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.PRICE);
                            case "capacity" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY);
                            case "status" -> apartmentService.getSorted(ApartmentService.ApartmentSortType.STATUS);
                            default -> throw new IllegalArgumentException();
                        };
                        ConsolePrinter.printList(list);
                    }
                    case "price" ->
                        ConsolePrinter.printApartmentPrice(apartmentService.getById(Long.parseLong(commandArray[2])));
                    case "changestatus" ->
                        ConsolePrinter.printApartmentStatusChange(apartmentService.changeStatus(Long.parseLong(commandArray[2])));
                    default -> throw new IllegalArgumentException();
                }
            }
            case 4 -> {
                switch (commandArray[1]) {
                    case "add" -> {
                        nullCheck(commandArray[2]);
                        ConsolePrinter.printAddedApartment(apartmentService.save(
                                Integer.parseInt(commandArray[2]), Double.parseDouble(commandArray[3])));
                    }
                    case "price" ->
                        ConsolePrinter.printApartmentPriceChange(apartmentService.changePrice(
                                Long.parseLong(commandArray[2]), Double.parseDouble(commandArray[3])));
                    default -> throw new IllegalArgumentException();
                }
            }
            default -> ConsolePrinter.syntaxError();
        }
    }

    /*  perk command list:
            perk list

            perk price *perk-id*
            perk get *perk-id*
            perk list *sort-type*

            perk add *name* *price*
            perk price *perk-id* *new-price*
     */

    private void perkCommand(String[] commandArray) {
        switch (commandArray.length) {
            case 2 -> {
                if (commandArray[1].equals("list")) {
                    ConsolePrinter.printList(perkService.getAll());
                } else throw new IllegalArgumentException();
            }
            case 3 -> {
                switch (commandArray[1]) {
                    case "get" ->
                        ConsolePrinter.printEntity(perkService.getById(Long.parseLong(commandArray[2])));
                    case "price" ->
                        ConsolePrinter.printPerkPrice(perkService.getById(Long.parseLong(commandArray[2])));
                    case "list" -> {
                        List<Perk> list = switch (commandArray[2].toLowerCase()) {
                            case "id" -> perkService.getSorted(PerkService.PerkSortType.ID);
                            case "name" -> perkService.getSorted(PerkService.PerkSortType.NAME);
                            case "price" -> perkService.getSorted(PerkService.PerkSortType.PRICE);
                            default -> throw new IllegalArgumentException();
                        };
                        ConsolePrinter.printList(list);
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
            case 4 -> {
                switch (commandArray[1]) {
                    case "add" ->
                        ConsolePrinter.printAddedPerk(perkService.save(commandArray[2], Double.parseDouble(commandArray[3])));
                    case "price" ->
                        ConsolePrinter.printPerkPriceChange(perkService.changePrice(
                                Long.parseLong(commandArray[2]), Double.parseDouble(commandArray[3])
                        ));
                    default -> throw new IllegalArgumentException();
                }
            }
            default -> ConsolePrinter.syntaxError();
        }
    }

    private boolean negativeCheck(String element) {
        return element.matches("-\\d+");
    }

    private void nullCheck(String s) {
        if (Double.parseDouble(s) == 0.0) {
            throw new IllegalArgumentException("This argument cannot be null");
        }
    }

    private enum CommandType {
        CLIENT, APARTMENT, PERK
    }
}
