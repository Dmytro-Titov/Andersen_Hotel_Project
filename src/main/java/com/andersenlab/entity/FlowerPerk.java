package com.andersenlab.entity;

public class FlowerPerk extends BasicPerk {
    private static int currentId = 0;

    public FlowerPerk() {
        currentId++;
        this.id = currentId;
        this.price = 50.0;
        this.name = "Flower arrangement";
    }
    @Override
    public void apply() {
        System.out.println("Flower arrangement is applied");
    }
}
