package com.andersenlab.entity;

public class IroningPerk extends BasicPerk {
    private static int currentId = 0;

    public IroningPerk() {
        currentId++;
        this.id = currentId;
        this.price = 40.0;
        this.name = "Ironing";
    }
    @Override
    public void apply() {
        System.out.println("Ironing is applied");
    }
}
