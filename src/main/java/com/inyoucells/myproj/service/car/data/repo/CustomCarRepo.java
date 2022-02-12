package com.inyoucells.myproj.service.car.data.repo;

import com.inyoucells.myproj.service.car.data.CarEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class CustomCarRepo {

    private final EntityManager entityManager;

    public CustomCarRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CarEntity> selectCarsWitHorsepowerMore(int horsepower) {
        String request = "SELECT e FROM CarEntity e WHERE e.horsepower >= :hp";
        TypedQuery<CarEntity> query = entityManager.createQuery(request, CarEntity.class);
        query.setParameter("hp", horsepower);
        return query.getResultList();
    }
}