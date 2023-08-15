package com.andersenlab.command;

import com.andersenlab.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandProvider {

    private final String COMMAND = "command";
    private String command;
    private final String ADD_CLIENT = "add_client";
    private final String INSERT_CLIENT = "insert_client";
    private final String GET_CLIENTS = "get_clients";
    private final String GET_CLIENTS_BY_ID = "get_clients_by_ID";
    private final String GET_CLIENTS_BY_NAME = "get_clients_by_name";
    private final String GET_CLIENTS_BY_STATUS = "get_clients_by_status";
    private final String GET_CLIENTS_BY_CHECK_OUT_DATE = "get_clients_by_check_out_date";
    private final String ADD_APARTMENT = "add_apartment";
    private final String INSERT_APARTMENT = "insert_apartment";
    private final String ADD_PERK = "add_perk";
    private final String INSERT_PERK = "insert_perk";
    private final String GET_APARTMENTS = "get_apartments";
    private final String GET_APARTMENTS_BY_ID = "get_apartments_by_ID";
    private final String GET_APARTMENTS_BY_PRICE = "get_apartments_by_price";
    private final String GET_APARTMENTS_BY_CAPACITY = "get_apartments_by_capacity";
    private final String GET_APARTMENTS_BY_STATUS = "get_apartments_by_status";
    private final String GET_PERKS = "get_perks";
    private final String GET_PERKS_BY_ID = "get_perks_by_ID";
    private final String GET_PERKS_BY_NAME = "get_perks_by_name";
    private final String GET_PERKS_BY_PRICE = "get_perks_by_price";
    private final String CHECK_IN = "check_in";
    private final String CLIENT_ID = "client_id";
    private final String CHECK_OUT = "check_out";
    private final String ADD_PERK_FOR_CLIENT = "add_perk_for_client";
    private final String APARTMENT_ID = "apartment_id";
    private final String PERK_ID = "perk_id";
    private final String CHANGE_PRICE_FOR_APARTMENT = "change_price_for_apartment";
    private final String CHANGE_APARTMENT_STATUS = "change_apartment_status";
    private final String CHANGE_PRICE_FOR_PERK = "change_price_for_perk";



    public void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        ListCommand listCommand = new ListCommand(ServletUtils.getHotelFactoryInstance());
        command = req.getParameter(COMMAND);
        try {
            if (command == null) {
                req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                switch (command) {
                    case ADD_CLIENT -> listCommand.addClient(req, resp);
                    case INSERT_CLIENT -> listCommand.insertClient(req, resp);
                    case GET_CLIENTS -> listCommand.getClients(req, resp);
                    case GET_CLIENTS_BY_ID -> listCommand.getClientsByID(req, resp);
                    case GET_CLIENTS_BY_NAME -> listCommand.getClientsByName(req, resp);
                    case GET_CLIENTS_BY_STATUS -> listCommand.getClientsByStatus(req, resp);
                    case GET_CLIENTS_BY_CHECK_OUT_DATE -> listCommand.getClientsByCheckOutDate(req, resp);
                    case ADD_APARTMENT -> listCommand.addApartment(req, resp);
                    case INSERT_APARTMENT -> listCommand.insertApartment(req, resp);
                    case ADD_PERK -> listCommand.addPerk(req, resp);
                    case INSERT_PERK -> listCommand.insertPerk(req, resp);
                    case GET_APARTMENTS -> listCommand.getApartments(req, resp);
                    case GET_APARTMENTS_BY_ID -> listCommand.getApartmentsByID(req, resp);
                    case GET_APARTMENTS_BY_PRICE -> listCommand.getApartmentsByPrice(req, resp);
                    case GET_APARTMENTS_BY_CAPACITY -> listCommand.getApartmentsByCapacity(req, resp);
                    case GET_APARTMENTS_BY_STATUS -> listCommand.getApartmentsByStatus(req, resp);
                    case GET_PERKS -> listCommand.getPerks(req, resp);
                    case GET_PERKS_BY_ID -> listCommand.getPerksByID(req, resp);
                    case GET_PERKS_BY_NAME -> listCommand.getPerksByName(req, resp);
                    case GET_PERKS_BY_PRICE -> listCommand.getPerksByPrice(req, resp);
                    case CHECK_IN -> listCommand.checkIn(req, resp);
                    case CLIENT_ID -> listCommand.getClient(req, resp);
                    case CHECK_OUT -> listCommand.checkOutApartments(req, resp);
                    case ADD_PERK_FOR_CLIENT -> listCommand.addPerkForClient(req, resp);
                    case APARTMENT_ID -> listCommand.getApartment(req, resp);
                    case PERK_ID -> listCommand.getPerk(req, resp);
                    case CHANGE_PRICE_FOR_APARTMENT -> listCommand.changeApartmentPrice(req, resp);
                    case CHANGE_APARTMENT_STATUS -> listCommand.changeApartmentStatus(req, resp);
                    case CHANGE_PRICE_FOR_PERK -> listCommand.changePerkPrice(req, resp);
                }
            }
        } catch (Exception e) {
            try {
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
                System.out.println(e.getMessage());
            } catch (Exception servletException) {
                throw new CommandProviderException(servletException.getMessage());
            }
        }
    }
}
