package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipRepo extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {
    @Override
    List<Ship> findAll();
}
