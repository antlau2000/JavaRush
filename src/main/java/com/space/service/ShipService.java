package com.space.service;

import com.space.exception.ShipNotFoundException;
import com.space.exception.UncorrectedInputDataException;
import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;


public interface ShipService {

    Ship getShipById(Long id) throws ShipNotFoundException;
    void createShip(Ship ship) throws UncorrectedInputDataException;
    void deleteShip(Long id) throws ShipNotFoundException;
    List<Ship> getShips(Specification<Ship> specification);
    Page<Ship> getShips(Specification<Ship> specification, Pageable sortedByName);
    Ship updateShip(Long id, Ship ship) throws ShipNotFoundException, UncorrectedInputDataException;

}