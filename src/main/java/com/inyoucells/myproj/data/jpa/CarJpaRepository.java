package com.inyoucells.myproj.data.jpa;

import com.inyoucells.myproj.data.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarJpaRepository extends JpaRepository<CarEntity, Long> {
    void deleteByDriverId(Long driverId);

    List<CarEntity> findAllByDriverId(Long driverId);

    @Query(
            value = "SELECT * FROM car_entity c WHERE c.year = ?1 and c.brand = ?2",
            nativeQuery = true)
    List<CarEntity> findByYearAndBrand(String year, String brand);

    @Query(
            value = "SELECT c FROM CarEntity c WHERE c.brand LIKE '%keyword%'")
    List<CarEntity> searchCarsByBrand(String keyword);
}
