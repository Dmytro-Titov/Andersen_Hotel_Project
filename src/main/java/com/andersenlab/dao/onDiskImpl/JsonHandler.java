package com.andersenlab.dao.onDiskImpl;

public interface JsonHandler {
    void save();

    StateEntity load();

    Boolean checkIfExistsJson();
}
