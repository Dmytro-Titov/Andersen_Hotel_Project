package com.andersenlab.servlet;

import com.andersenlab.dao.onDiskImpl.OnDiskApartmentDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskClientDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskPerkDaoImpl;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ServletUtils;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApartmentServletTest {

    private static HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();


    @BeforeEach
    public void setup() {
        if (hotelFactory.getConfig().getConfigData().getSaveOption().isSaveOnDisk()) {
            OnDiskClientDaoImpl onDiskClientDao = new OnDiskClientDaoImpl(hotelFactory);
            for (Client client : hotelFactory.getClientService().getAll()) {
                onDiskClientDao.remove(client.getId());
            }
            OnDiskApartmentDaoImpl onDiskApartmentDao = new OnDiskApartmentDaoImpl(hotelFactory);
            for (Apartment apartment : hotelFactory.getApartmentService().getAll()) {
                onDiskApartmentDao.remove(apartment.getId());
            }
            OnDiskPerkDaoImpl onDiskPerkDao = new OnDiskPerkDaoImpl(hotelFactory);
            for (Perk perk : hotelFactory.getPerkService().getAll()) {
                onDiskPerkDao.remove(perk.getId());
            }
        }
        hotelFactory.getClientService().save("Alex", 2);
        hotelFactory.getApartmentService().save(4, 4000.0);
        hotelFactory.getPerkService().save("loundry", 50);
        hotelFactory.getClientService().save("Zina", 2);
        hotelFactory.getApartmentService().save(2, 5000.0);
        hotelFactory.getPerkService().save("massage", 500);
    }


    @AfterEach
    public void teardown() {
        if (hotelFactory.getConfig().getConfigData().getSaveOption().isSaveOnDisk()) {
            OnDiskClientDaoImpl onDiskClientDao = new OnDiskClientDaoImpl(hotelFactory);
            for (Client client : hotelFactory.getClientService().getAll()) {
                onDiskClientDao.remove(client.getId());
            }
            OnDiskApartmentDaoImpl onDiskApartmentDao = new OnDiskApartmentDaoImpl(hotelFactory);
            for (Apartment apartment : hotelFactory.getApartmentService().getAll()) {
                onDiskApartmentDao.remove(apartment.getId());
            }
            OnDiskPerkDaoImpl onDiskPerkDao = new OnDiskPerkDaoImpl(hotelFactory);
            for (Perk perk : hotelFactory.getPerkService().getAll()) {
                onDiskPerkDao.remove(perk.getId());
            }
        }
    }


    @Test
    public void add_new_apartment() {
        Integer expected = 3;
        JSONObject requestBody = new JSONObject();
        requestBody.put("capacity", 10);
        requestBody.put("price", 5550.0);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/apartments")
                .then()
                .statusCode(201);
        Integer actual = hotelFactory.getApartmentService().getAll().size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void get_apartment_by_id() {
        Apartment expected = hotelFactory.getApartmentService().getById(1);
        Apartment actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/apartments/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Apartment.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_apartments_by_id");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void update_apartment_by_id() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("capacity", 11);
        requestBody.put("price", 5300.0);
        requestBody.put("status", "AVAILABLE");
        Apartment actual =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put("http://localhost:8080/apartments/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Apartment.class);
        Apartment expected = hotelFactory.getApartmentService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("update_apartment_by_id");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void change_apartment_price() {
        Double expected = 2500.0;
        JSONObject requestBody = new JSONObject();
        requestBody.put("price", 2500);
        Apartment apartment =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("http://localhost:8080/apartments/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Apartment.class);
        Double actual = apartment.getPrice();
        Assertions.assertEquals(expected, actual);
        System.out.println("change_apartment_price");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void change_apartment_status() {
        Apartment actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/apartments/change-status/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Apartment.class);
        Apartment expected = hotelFactory.getApartmentService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("change_apartment_status");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_apartments() {
        Integer expected = 2;
        List apartments = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/apartments")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = apartments.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_apartments");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_apartments_by_id() {
        Integer expected = 2;
        List apartments = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/apartments?type=id")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = apartments.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_apartments_by_id");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_apartments_by_price() {
        Integer expected = 2;
        List apartments = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/apartments?type=price")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = apartments.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_apartments_by_price");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_apartments_by_capacity() {
        Integer expected = 2;
        List apartments = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/apartments?type=capacity")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = apartments.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_apartments_by_capacity");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_apartments_by_status() {
        Integer expected = 2;
        List apartments = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/apartments?type=status")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = apartments.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_apartments_by_status");
        hotelFactory.getApartmentService().getAll().forEach(System.out::println);
    }
}
