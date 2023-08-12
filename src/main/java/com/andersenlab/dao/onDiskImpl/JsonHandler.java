package com.andersenlab.dao.onDiskImpl;

public interface JsonHandler {
    void save(StateEntity stateEntity);

    StateEntity load();

    Boolean checkIfExistsJson();
}
