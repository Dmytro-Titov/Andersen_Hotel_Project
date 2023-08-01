package com.andersenlab.entity;

public class IroningPerk extends BasicPerk {

    public IroningPerk(long id) {
        this.id = id;
        this.price = 40.0;
        this.name = "Ironing";
    }
    @Override
    public void apply() {
        System.out.println("Ironing is applied");
    }
}
