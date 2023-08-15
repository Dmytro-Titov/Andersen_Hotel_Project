package com.andersenlab.servlet.perk;

import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(
        name = "SavePerkServlet",
        urlPatterns = {"/perks/save"}
)
public class SavePerkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
        ObjectMapper objectMapper = new ObjectMapper();

        Perk newPerk = objectMapper.readValue(ServletUtils.readBody(req.getReader()), Perk.class);
        Perk savedPerk = hotelFactory.getPerkService().save(newPerk.getName(), newPerk.getPrice());

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), savedPerk);
    }
}
