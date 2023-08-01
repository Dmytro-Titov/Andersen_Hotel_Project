package com.andersenlab.service.impl;

import com.andersenlab.dao.DataSource;
import com.andersenlab.service.PerkService;

public class PerkServiceImpl implements PerkService {

    private final DataSource dataSource;

    public PerkServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
