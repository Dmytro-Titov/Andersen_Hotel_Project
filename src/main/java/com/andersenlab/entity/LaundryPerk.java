package com.andersenlab.entity;

public class LaundryPerk extends BasicPerk {
    private static int currentId = 0;

    public LaundryPerk() {
        currentId++;
        this.id = currentId;
        this.price = 70.0;
        this.name = "Laundry";
    }
    @Override
    public void apply() {
        System.out.println("Laundry is applied");
    }
}
