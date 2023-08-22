package com.andersenlab.servlet;

import com.andersenlab.config.Config;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ConfigHandler;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.apache.catalina.LifecycleException;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PerkServletTest {

    private HotelFactory hotelFactory;


    @BeforeAll
    static void start() {
        try {
            StartServlet.getTomcat().start();
        } catch (LifecycleException e) {
            System.out.println(e.getMessage());
        }
    }


    @BeforeEach
    void setup() {
        Config config = new Config();
        config.setConfigData(ConfigHandler.createConfig("src/main/resources/config/config-dev.yaml"));
        hotelFactory = new HotelFactory(config);
        hotelFactory.getPerkService().cleanTable();
        hotelFactory.getClientService().save("Alex", 2);
        hotelFactory.getApartmentService().save(4, 4000.0);
        hotelFactory.getPerkService().save("laundry", 50);
        hotelFactory.getClientService().save("Zina", 2);
        hotelFactory.getApartmentService().save(2, 5000.0);
        hotelFactory.getPerkService().save("massage", 500);
    }


    @AfterAll
    static void stopServer() throws LifecycleException {
        StartServlet.getTomcat().stop();
    }


    @Test
    void add_new_perk_to_hotel_service() {
        Integer expected = 3;
        Map<String, Object> requestBody = new HashMap<>();
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
    }


    @Test
    void get_perk_by_id_in_hotel_service() {
        Perk expected = hotelFactory.getPerkService().getAll().stream().findAny().get();
        Perk actual =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("http://localhost:8080/perks/id?id=" + expected.getId())
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Assertions.assertEquals(expected.getName(), actual.getName());
    }


    @Test
    void update_perk_by_id_in_hotel_service() {
        Perk expected = hotelFactory.getPerkService().getAll().stream().findAny().get();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", expected.getId());
        requestBody.put("name", "Test");
        requestBody.put("price", 10);
        Perk actual =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put("http://localhost:8080/perks/id?id=" + expected.getId())
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        expected.setName("Test");
        expected.setPrice(10);
        Assertions.assertEquals(expected.getName(), actual.getName());
    }


    @Test
    void change_perk_price_in_hotel_service() {
        Perk expectedPerk = hotelFactory.getPerkService().getAll().stream().findAny().get();
        Double expected = 200.0;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("price", 200);
        Perk perk =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("http://localhost:8080/perks/id?id=" + expectedPerk.getId())
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Perk.class);
        Double actual = perk.getPrice();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void look_all_perks_in_hotel_service() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<Perk>>() {
                });
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
        System.out.println("get_all_perks");
        hotelFactory.getPerkService().getAll().forEach(System.out::println);
    }


    @Test
    void look_all_sorted_perks_by_id_in_hotel_service() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=id")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<Perk>>() {
                });
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void look_all_sorted_perks_by_name_in_hotel_service() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=name")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<Perk>>() {
                });
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void look_all_sorted_perks_by_price_in_hotel_service() {
        Integer expected = 2;
        List perks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/perks?type=price")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<Perk>>() {
                });
        Integer actual = perks.size();
        Assertions.assertEquals(expected, actual);
    }
}