package com.andersenlab.servlet.perk;

import com.andersenlab.entity.Perk;
import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(
        name = "UpdatePerkServlet",
        urlPatterns = {"/perks/update"}
)
public class UpdatePerkServlet extends HttpServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
        ObjectMapper objectMapper = new ObjectMapper();

        Perk perk = objectMapper.readValue(ServletUtils.readBody(req.getReader()), Perk.class);

        try {
            Perk updatedPerk = hotelFactory.getPerkService().update(perk);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), updatedPerk);
        } catch (IdDoesNotExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
