package com.andersenlab.servlet.client;

import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.exceptions.InnerLogicException;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ClientCheckOutServlet",
        urlPatterns = {"/clients/checkout"}
)
public class ClientCheckOutServlet extends HttpServlet {
    private HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    //EXAMPLE: http://localhost:8080/clients/checkout?clientId=2 for checkOutApartment()
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("clientId");
        try {
            double stayCost = hotelFactory.getClientService().checkOutApartment(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), stayCost);
        } catch (IdDoesNotExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InnerLogicException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
