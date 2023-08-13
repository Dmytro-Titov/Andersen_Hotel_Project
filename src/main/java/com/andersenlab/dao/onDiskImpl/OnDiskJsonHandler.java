package com.andersenlab.dao.onDiskImpl;

public interface OnDiskJsonHandler {
    void save(StateEntity stateEntity);

    StateEntity load();

    Boolean checkIfExistsJson();
}
