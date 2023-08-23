package com.andersenlab.controllers;

import com.andersenlab.entity.Apartment;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController extends BaseController{
    private final ApartmentService apartmentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApartmentController(HotelFactory hotelFactory) {
        this.apartmentService = hotelFactory.getApartmentService();
    }

    @GetMapping("/id")
    public ResponseEntity<Apartment> getById(@RequestParam long id) {
        return ResponseEntity.ok(apartmentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Apartment>> getAll() {
        return ResponseEntity.ofNullable(apartmentService.getAll());
    }

    @PostMapping
    public ResponseEntity<Apartment> save(@RequestBody String body) throws JsonProcessingException {
        Apartment apartment = objectMapper.readValue(body, Apartment.class);
        return ResponseEntity.ofNullable(apartmentService.save(apartment.getCapacity(), apartment.getPrice()));
    }

    @GetMapping(params={"type"})
    public ResponseEntity<List<Apartment>> getSorted(@RequestParam String type) {
        return ResponseEntity.ofNullable(apartmentService.getSorted(type));
    }

    @PutMapping("/id")
    public ResponseEntity<Apartment> update(@RequestParam long id, @RequestBody String body) throws JsonProcessingException {
        Apartment apartment = objectMapper.readValue(body, Apartment.class);
        apartment.setId(id);
        return ResponseEntity.ok(apartmentService.update(apartment));
    }

    @PostMapping("/id")
    public ResponseEntity<Apartment> changePrice(@RequestParam long id, @RequestBody String body) throws JsonProcessingException {
        Apartment apartment = objectMapper.readValue(body, Apartment.class);
        return ResponseEntity.ok(apartmentService.changePrice(id, apartment.getPrice()));
    }

    @GetMapping("/change-status/id")
    public ResponseEntity<Apartment> changeStatus(@RequestParam long id) {
        return ResponseEntity.ok(apartmentService.changeStatus(id));
    }
}
