package com.andersenlab.servlet.apartment;

import com.andersenlab.entity.Apartment;
import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.factory.ServletFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "ApartmentChangeStatusByIdServlet",
        urlPatterns = {"/apartments/change-status/id"}
)
public class ApartmentChangeStatusByIdServlet extends HttpServlet {
    private ApartmentService apartmentService = ServletFactory.INSTANCE.getApartmentService();
    private ObjectMapper objectMapper = new ObjectMapper();

    //EXAMPLE: http://localhost:8080/apartments/id?id=3 for changeStatus()
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Apartment changedStatusApartment = apartmentService.changeStatus(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), changedStatusApartment);
        } catch (IdDoesNotExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
