package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;

import java.util.List;
import java.util.Optional;

public class HibernatePerkDaoImpl implements PerkDao {
    @Override
    public Optional<Perk> getById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Perk> getAll() {
        return null;
    }

    @Override
    public Perk save(Perk perk) {
        return null;
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        return Optional.empty();
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public List<Perk> getSortedBy(PerkSortType type) {
        return null;
    }
}
