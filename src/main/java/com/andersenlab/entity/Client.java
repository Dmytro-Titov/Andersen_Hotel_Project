package com.andersenlab.entity;


public class Client {
    private final Long id;
    private String name;

    public Client(String name, Long id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
