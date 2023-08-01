package com.andersenlab.entity;

public class FlowerPerk extends BasicPerk {

    public FlowerPerk(long id) {
        this.id = id;
        this.price = 50.0;
        this.name = "Flower arrangement";
    }
    @Override
    public void apply() {
        System.out.println("Flower arrangement is applied");
    }
}
