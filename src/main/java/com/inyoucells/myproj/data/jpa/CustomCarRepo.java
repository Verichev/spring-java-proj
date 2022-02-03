package com.inyoucells.myproj.data.jpa;

import com.inyoucells.myproj.data.entity.CarEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomCarRepo {

    private final EntityManager entityManager;

    public CustomCarRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CarEntity> selectCarsWitHorsepowerMore(int horsepower) {
        String request = "SELECT * FROM car_entity e WHERE e.horsepower > :hp";
        TypedQuery<CarEntity> query = entityManager.createQuery(request, CarEntity.class);
        query.setParameter("hp", horsepower);
        return query.getResultList();
    }
}