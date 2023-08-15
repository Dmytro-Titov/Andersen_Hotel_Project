package com.andersenlab.command;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.ClientStatus;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListCommand {


    private final ClientService clientService;
    private final ApartmentService apartmentService;
    private final PerkService perkService;
    private final String CLIENTS = "clients";
    private final String APARTMENTS = "apartments";
    private final String PERKS = "perks";
    private final String NAME = "name";
    private final String QUANTITY = "quantity";
    private final String CAPACITY = "capacity";
    private final String PRICE = "price";
    private final String CLIENT_ID = "client_id";
    private final String APARTMENT_ID = "apartment_id";
    private final String PERK_ID = "perk_id";
    private final String COAST = "coast";
    private final String DURATION = "duration";
    private final String CLIENT = "client";
    private final String APARTMENT = "apartment";
    private final String PERK = "perk";
    private final String ERROR = "error";


    public ListCommand(HotelFactory hotelFactory) {
        clientService = hotelFactory.getClientService();
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();
    }


    public void addClient(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/addClient.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void insertClient(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter(NAME);
            String quantity = req.getParameter(QUANTITY);
            if (name.isBlank() || quantity.isBlank() || !quantity.matches("\\d+")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            clientService.save(name, Integer.parseInt(quantity));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getClients(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/getClients.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getClientsByID(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Client> clients = clientService.getSorted(ClientService.ClientSortType.ID);
            session.setAttribute(CLIENTS, clients);
            req.getServletContext().getRequestDispatcher("/listClients.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getClientsByName(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Client> clients = clientService.getSorted(ClientService.ClientSortType.NAME);
            session.setAttribute(CLIENTS, clients);
            req.getServletContext().getRequestDispatcher("/listClients.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getClientsByStatus(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Client> clients = clientService.getSorted(ClientService.ClientSortType.STATUS);
            session.setAttribute(CLIENTS, clients);
            req.getServletContext().getRequestDispatcher("/listClients.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getClientsByCheckOutDate(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Client> clients = clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
            session.setAttribute(CLIENTS, clients);
            req.getServletContext().getRequestDispatcher("/listClients.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void addApartment(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/addApartment.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void insertApartment(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String capacity = req.getParameter(CAPACITY);
            String price = req.getParameter(PRICE);
            if (capacity.isBlank() || price.isBlank() || !capacity.matches("\\d+") || !price.matches("\\d+[.,]?\\d?")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            apartmentService.save(Integer.parseInt(capacity), Double.parseDouble(price));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }


    public void addPerk(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/addPerk.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void insertPerk(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter(NAME);
            String price = req.getParameter(PRICE);
            if (name.isBlank() || price.isBlank() || !price.matches("\\d+[.,]?\\d?")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            perkService.save(name, Double.parseDouble(price));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getApartments(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/getApartments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e);
        }
    }


    public void getApartmentsByID(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Apartment> apartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.ID);
            session.setAttribute(APARTMENTS, apartments);
            req.getServletContext().getRequestDispatcher("/listApartments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getApartmentsByPrice(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Apartment> apartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.PRICE);
            session.setAttribute(APARTMENTS, apartments);
            req.getServletContext().getRequestDispatcher("/listApartments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getApartmentsByCapacity(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Apartment> apartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY);
            session.setAttribute(APARTMENTS, apartments);
            req.getServletContext().getRequestDispatcher("/listApartments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getApartmentsByStatus(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Apartment> apartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.STATUS);
            session.setAttribute(APARTMENTS, apartments);
            req.getServletContext().getRequestDispatcher("/listApartments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getPerks(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getServletContext().getRequestDispatcher("/getPerks.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getPerksByID(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Perk> perks = perkService.getSorted(PerkService.PerkSortType.ID);
            session.setAttribute(PERKS, perks);
            req.getServletContext().getRequestDispatcher("/listPerks.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getPerksByName(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Perk> perks = perkService.getSorted(PerkService.PerkSortType.NAME);
            session.setAttribute(PERKS, perks);
            req.getServletContext().getRequestDispatcher("/listPerks.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getPerksByPrice(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            List<Perk> perks = perkService.getSorted(PerkService.PerkSortType.PRICE);
            session.setAttribute(PERKS, perks);
            req.getServletContext().getRequestDispatcher("/listPerks.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }


    public void getClient(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String clientId = req.getParameter(CLIENT_ID);
            req.setAttribute(CLIENT_ID, clientId);
            List<Perk> perkListForClient = clientService.getAllPerks(Long.parseLong(clientId));
            if (perkListForClient.isEmpty()) {
                req.setAttribute(PERKS, "empty");
            } else {
                req.setAttribute(PERKS, perkListForClient);
            }
            double coast = clientService.getStayCost(Long.parseLong(clientId));
            req.setAttribute(COAST, coast);
            req.getServletContext().getRequestDispatcher("/client.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void checkIn(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String clientId = req.getParameter(CLIENT);
            String duration = req.getParameter(DURATION);
            String apartmentId = req.getParameter(APARTMENT_ID);
            if (duration.isBlank() || apartmentId.isBlank() || apartmentService
                    .getById(Long.parseLong(apartmentId)) == null || !duration.matches("\\d+")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            else if (clientService.getById(Long.parseLong(clientId)).getQuantityOfPeople() > apartmentService
                    .getById(Long.parseLong(apartmentId)).getCapacity()){
                req.setAttribute(ERROR, "this apartment is not available for you");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            clientService.checkInApartment(Long.parseLong(clientId), Integer.parseInt(duration), Long.parseLong(apartmentId));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void checkOutApartments(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String clientId = req.getParameter(CLIENT);
            if (!clientService.getById(Long.parseLong(clientId)).getStatus().equals(ClientStatus.CHECKED_IN)) {
                req.setAttribute(ERROR, "this client was not check in");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            clientService.checkOutApartment(Long.parseLong(clientId));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void addPerkForClient(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String clientId = req.getParameter(CLIENT);
            String perkId = req.getParameter(PERK_ID);
            if (perkId.isBlank() || perkService.getById(Long.parseLong(perkId)) == null) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } else if (!clientService.getById(Long.parseLong(clientId)).getStatus().equals(ClientStatus.CHECKED_IN)) {
                req.setAttribute(ERROR, "This client is not checked in");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            } else {
                clientService.addPerk(Long.parseLong(clientId), Long.parseLong(perkId));
                req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }


    public void getApartment(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String apartmentId = req.getParameter(APARTMENT_ID);
            req.setAttribute(APARTMENT_ID, apartmentId);
            req.getServletContext().getRequestDispatcher("/apartment.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void getPerk(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String perkId = req.getParameter(PERK_ID);
            req.setAttribute(PERK_ID, perkId);
            req.getServletContext().getRequestDispatcher("/perk.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void changeApartmentPrice(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String apartmentId = req.getParameter(APARTMENT);
            String price = req.getParameter(PRICE);
            if (price.isBlank() || !price.matches("\\d+[.,]?\\d?")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            apartmentService.changePrice(Long.parseLong(apartmentId), Double.parseDouble(price));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void changePerkPrice(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String perkId = req.getParameter(PERK);
            String price = req.getParameter(PRICE);
            if (price.isBlank() || !price.matches("\\d+[.,]?\\d?")) {
                req.setAttribute(ERROR, "input correct data");
                req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            perkService.changePrice(Long.parseLong(perkId), Double.parseDouble(price));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e.getMessage());
        }
    }

    public void changeApartmentStatus(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String apartmentId = req.getParameter(APARTMENT);
            apartmentService.changeStatus(Long.parseLong(apartmentId));
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new CommandProviderException(e);
        }
    }
}
