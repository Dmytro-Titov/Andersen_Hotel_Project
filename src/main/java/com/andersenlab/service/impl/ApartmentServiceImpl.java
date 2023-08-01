package com.andersenlab.service.impl;


import com.andersenlab.dao.DataSource;
import com.andersenlab.service.ApartmentService;

public class ApartmentServiceImpl implements ApartmentService {

    private final DataSource dataSource;

    public ApartmentServiceImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }


}
