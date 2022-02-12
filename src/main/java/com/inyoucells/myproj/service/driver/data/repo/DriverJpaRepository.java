package com.inyoucells.myproj.service.driver.data.repo;

import com.inyoucells.myproj.service.driver.data.DriverEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverJpaRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findAllByUserId(Long driverId, Pageable pageable);
}
