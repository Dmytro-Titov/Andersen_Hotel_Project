package com.andersenlab.util;

import com.andersenlab.config.Config;
import com.andersenlab.factory.HotelFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtils {
    public static String readBody(BufferedReader reader) throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        return jsonBody.toString();
    }
}
