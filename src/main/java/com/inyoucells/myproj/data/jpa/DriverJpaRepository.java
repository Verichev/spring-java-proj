package com.inyoucells.myproj.data.jpa;

import com.inyoucells.myproj.data.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverJpaRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findAllByUserId(Long driverId);

    Optional<Long> findUserIdById(Long driverId);
}
