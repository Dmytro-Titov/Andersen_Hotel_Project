package com.andersenlab.servlet.perk;

import com.andersenlab.entity.Perk;
import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "GetAllPerkServlet",
        urlPatterns = {"/perks/all"}
)
public class GetAllPerkServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
        ObjectMapper objectMapper = new ObjectMapper();

        List<Perk> perks = hotelFactory.getPerkService().getAll();

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), perks);
    }
}
