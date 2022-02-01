package com.inyoucells.myproj.data.jpa;

import com.inyoucells.myproj.data.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarJpaRepository extends JpaRepository<CarEntity, Long> {
    void deleteByDriverId(Long driverId);

    List<CarEntity> findAllByDriverId(Long driverId);
}
