package com.inyoucells.myproj.data.jpa;

import com.inyoucells.myproj.data.entity.DriverEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverJpaRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findAllByUserId(Long driverId, Pageable pageable);
}
