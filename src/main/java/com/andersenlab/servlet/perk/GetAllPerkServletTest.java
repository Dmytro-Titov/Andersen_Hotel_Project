package com.andersenlab.servlet.perk;

import com.andersenlab.entity.Perk;
import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "GetAllPerkServletTest",
        urlPatterns = {"/perks/test/*"}
)
public class GetAllPerkServletTest extends HttpServlet {
    private HotelFactory hotelFactory;
    private ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        hotelFactory = ServletUtils.getHotelFactoryInstance();
        objectMapper = new ObjectMapper();

        String id = req.getParameter("id");
        String sortType = req.getParameter("type");
        if (id != null && id.length() > 0) {
            getPerkById(resp, id);
        } else if (sortType != null && sortType.length() > 0) {
            getSortedPerks(resp, sortType.toUpperCase());
        } else {
            getAll(resp);
        }

    }


    private void getAll(HttpServletResponse resp) throws IOException {
        List<Perk> perks = hotelFactory.getPerkService().getAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), perks);
    }

    private void getPerkById(HttpServletResponse resp, String id) throws IOException {
        try {
            Perk perk = hotelFactory.getPerkService().getById(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), perk);
        } catch (IdDoesNotExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getSortedPerks(HttpServletResponse resp, String sortType) {
        try {
            List<Perk> perks = hotelFactory.getPerkService().getSorted(PerkService.PerkSortType.valueOf(sortType));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), perks);
        } catch (IllegalArgumentException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
