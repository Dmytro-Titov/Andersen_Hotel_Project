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

public class PerkServletTest {

    private HotelFactory hotelFactory = ServletUtils.getHotelFactoryInstance();


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
        hotelFactory.getApartmentService().save(2, 4000.0);
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
    public void add_new_perk() {
        Integer expected = 3;
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "ironing");
        requestBody.put("price", 100.0);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/perks")
                .then()
                .statusCode(201);
        Integer actual = hotelFactory.getPerkService().getAll().size();
        Assertions.assertEquals(expected, actual);
        System.out.println("for add_new_perk");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_perk_by_id() {
        Perk expected = hotelFactory.getPerkService().getById(1);
        Perk actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/perks/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Assertions.assertEquals(expected, actual);
        System.out.println("get_perk_by_id");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void update_perk_by_id() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", 1);
        requestBody.put("name", "Test");
        requestBody.put("price", 10);
        Perk actual =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put("http://localhost:8080/perks/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Perk expected = hotelFactory.getPerkService().getById(1);
        Assertions.assertEquals(expected, actual);
        System.out.println("update_perk_by_id");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void change_perk_price() {
        Double expected = 200.0;
        JSONObject requestBody = new JSONObject();
        requestBody.put("price", 200);
        Perk perk =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("http://localhost:8080/perks/id?id=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Double actual = perk.getPrice();
        Assertions.assertEquals(expected, actual);
        System.out.println("change_perk_price");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_perks() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_perks");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_perks_by_id() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=id")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_perks_by_id");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    public void get_all_sorted_perks_by_name() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=name")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_perks_by_name");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }

    @Test
    public void get_all_sorted_perks_by_price() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=price")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(List.class);
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_sorted_perks_by_price");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }
}
