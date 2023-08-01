package com.andersenlab.entity;

public class LaundryPerk extends BasicPerk {

    public LaundryPerk(long id) {
        this.id = id;
        this.price = 70.0;
        this.name = "Laundry";
    }
    @Override
    public void apply() {
        System.out.println("Laundry is applied");
    }
}
