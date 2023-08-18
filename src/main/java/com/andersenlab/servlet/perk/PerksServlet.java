package com.andersenlab.servlet.perk;

import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "PerksServlet",
        urlPatterns = {"/perks"}
)
public class PerksServlet extends HttpServlet {
    private HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    //EXAMPLE: http://localhost:8080/perks for getAll() and http://localhost:8080/perks?type=price for getSorted()
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sortType = req.getParameter("type");
        if (sortType != null && sortType.length() > 0) {
            getSortedPerks(resp, sortType.toUpperCase());
        } else {
            getAll(resp);
        }
    }

    //EXAMPLE: http://localhost:8080/perks for save()
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Perk newPerk = objectMapper.readValue(ServletUtils.readBody(req.getReader()), Perk.class);
        Perk savedPerk = hotelFactory.getPerkService().save(newPerk.getName(), newPerk.getPrice());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), savedPerk);
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        List<Perk> perks = hotelFactory.getPerkService().getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), perks);
    }

    private void getSortedPerks(HttpServletResponse resp, String sortType) throws IOException {
        try {
            List<Perk> perks = hotelFactory.getPerkService().getSorted(PerkService.PerkSortType.valueOf(sortType));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), perks);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}