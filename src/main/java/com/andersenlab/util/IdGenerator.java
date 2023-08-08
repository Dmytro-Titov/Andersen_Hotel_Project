package com.andersenlab.util;

public class IdGenerator {
    private static long CLIENT_COUNTER = 0;
    private static long APARTMENT_COUNTER = 0;
    private static long PERK_COUNTER = 0;

    public static long generateClientId() {
        return ++CLIENT_COUNTER;
    }

    public static long generateApartmentId() {
        return ++APARTMENT_COUNTER;
    }

    public static long generatePerkId() {
        return ++PERK_COUNTER;
    }

    public static void cancelGenerateId(){
        CLIENT_COUNTER = 0;
        APARTMENT_COUNTER = 0;
        PERK_COUNTER = 0;
    }
}
