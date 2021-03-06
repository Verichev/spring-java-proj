package com.inyoucells.myproj.service.car.data.repo;

import com.inyoucells.myproj.service.car.data.CarEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarJpaRepository extends JpaRepository<CarEntity, UUID> {
    void deleteByDriverId(Long driverId);

    List<CarEntity> findAllByDriverId(Long driverId);

    @Query(
            value = "SELECT * FROM car_entity c WHERE c.year = ?1 and c.brand = ?2",
            nativeQuery = true)
    List<CarEntity> findByYearAndBrand(String year, String brand);

    @Query(
            value = "SELECT c FROM CarEntity c WHERE c.brand LIKE CONCAT('%',:keyword,'%')")
    List<CarEntity> searchCarsByBrand(String keyword);
}
