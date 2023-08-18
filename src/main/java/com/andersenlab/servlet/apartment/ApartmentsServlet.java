package com.andersenlab.servlet.apartment;


import com.andersenlab.entity.Apartment;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "ApartmentsServlet",
        urlPatterns = {"/apartments"}
)
public class ApartmentsServlet extends HttpServlet {
    private HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    //EXAMPLE: http://localhost:8080/apartments for getAll() and http://localhost:8080/apartments?type=capacity for getSorted()
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sortType = req.getParameter("type");
        if (sortType != null && sortType.length() > 0) {
            getSortedApartments(resp, sortType.toUpperCase());
        } else {
            getAll(resp);
        }
    }

    //EXAMPLE: http://localhost:8080/apartments for save()
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Apartment newApartment = objectMapper.readValue(ServletUtils.readBody(req.getReader()), Apartment.class);
        Apartment savedApartment = hotelFactory.getApartmentService()
                .save(newApartment.getCapacity(), newApartment.getPrice());

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), savedApartment);
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        List<Apartment> apartments = hotelFactory.getApartmentService().getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), apartments);
    }

    private void getSortedApartments(HttpServletResponse resp, String sortType) throws IOException {
        try {
            List<Apartment> apartments = hotelFactory.getApartmentService()
                    .getSorted(sortType);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), apartments);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
