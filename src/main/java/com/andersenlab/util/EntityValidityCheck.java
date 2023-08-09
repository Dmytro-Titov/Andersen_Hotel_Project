package com.andersenlab.util;

public class EntityValidityCheck {
    public static void perkPriceCheck(double price) {
        if (price < 0.0) {
            throw new IllegalArgumentException("Perk price cannot be less than 0.0");
        }
    }
    public static void apartmentPriceCheck(double price) {
        if (price < 0.0) {
            throw new IllegalArgumentException("Apartment price cannot be less than 0.0");
        }
    }
    public static void apartmentCapacityCheck(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Apartment capacity must be greater than 0");
        }
    }
    public static void clientQuantityOfPeopleCheck(int quantityOfPeople) {
        if (quantityOfPeople < 1) {
            throw new IllegalArgumentException("Client must include at least 1 person");
        }
    }
    public static void clientStayDurationCheck(int stayDuration) {
        if (stayDuration < 1) {
            throw new IllegalArgumentException("Client must stay for at least 1 day");
        }
    }
}
