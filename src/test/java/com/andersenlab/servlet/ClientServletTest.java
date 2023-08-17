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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ClientServletTest {

    private final HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();


    @BeforeEach
    void setup() {
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
        hotelFactory.getPerkService().save("laundry", 50);
        hotelFactory.getClientService().save("Zina", 2);
        hotelFactory.getApartmentService().save(2, 3000.0);
        hotelFactory.getPerkService().save("massage", 500);
    }


    @Test
    void add_new_client() {
        int expected = 3;
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Petr");
        requestBody.put("quantityOfPeople", 2);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/clients")
                .then()
                .statusCode(201);
        Integer actual = hotelFactory.getClientService().getAll().size();
        Assertions.assertEquals(expected, actual);
        System.out.println("add apartment");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void get_client_by_id() {
        Client expected = hotelFactory.getClientService().getById(1);
        Client actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Client.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("get_client_by_id");
        System.out.println(hotelFactory.getClientService().getById(1));
    }


    @Test
    void update_client_by_id() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Test");
        requestBody.put("quantityOfPeople", 4);
        Client actual =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put("http://localhost:8080/clients/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Client.class);
        Client expected = hotelFactory.getClientService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("update_client_by_id");
        System.out.println(hotelFactory.getClientService().getById(1).getName());
    }


    @Test
    void get_stay_coast_for_client() {
        Double expected = 20000.0;
        hotelFactory.getClientService().checkInApartment(1, 5, 1);
        Double actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/stay-cost/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Double.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("get_stay_coast_for_client");
        System.out.println(hotelFactory.getClientService().getById(1));
    }


    @Test
    void add_perk_to_client() {
        hotelFactory.getClientService().checkInApartment(1, 5, 1);
        Perk expected = hotelFactory.getPerkService().getById(1);
        Perk actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .post("http://localhost:8080/clients/perks?clientId=1&perkId=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("add_perk_to_client");
        System.out.println(hotelFactory.getClientService().getById(1).getPerks().size());
    }


    @Test
    void get_perks_for_client() {
        hotelFactory.getClientService().checkInApartment(1, 5, 1);
        hotelFactory.getClientService().addPerk(1, 1);
        Integer expected = 1;
        List actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/perks?clientId=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(List.class);
        Assertions.assertEquals(expected, actual.size());
        System.out.println("get_perks_for_client");
        actual.forEach(System.out::println);
    }


    @Test
    void check_in_apartments() {
        Client actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/checkin?clientId=1&duration=5&apartmentId=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Client.class);
        Client expected = hotelFactory.getClientService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("check_in_apartments");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void check_in_any_free_apartments() {
        Client actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/checkin?clientId=1&duration=5")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Client.class);
        Client expected = hotelFactory.getClientService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("check_in_in_free_apartments");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void check_out_apartments() {
        hotelFactory.getClientService().checkInApartment(1, 5, 1);
        Double expected = 20000.0;
        Double actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/clients/checkout?clientId=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Double.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("check_out_apartments");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void get_all_clients() {
        Integer expected = 2;
        List clients = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/clients")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = clients.size();
        Assertions.assertEquals(expected, actual);
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void get_all_sorted_clients_by_name() {
        Integer expected = 2;
        List clients = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/clients?type=name")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = clients.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_clients_by_name");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }

    @Test
    void get_all_sorted_clients_by_status() {
        Integer expected = 2;
        List clients = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/clients?type=status")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = clients.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_clients_by_status");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void get_all_sorted_clients_by_id() {
        Integer expected = 2;
        List clients = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/clients?type=id")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = clients.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_clients_by_id");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }


    @Test
    void get_all_sorted_clients_by_check_out_date() {
        Integer expected = 2;
        hotelFactory.getClientService().checkInApartment(1, 5, 1);
        hotelFactory.getClientService().checkInApartment(2, 1, 2);
        List clients = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/clients?type=check_out_date")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = clients.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_clients_by_check_out_date");
        hotelFactory.getClientService().getAll().forEach(System.out::println);
    }
}
