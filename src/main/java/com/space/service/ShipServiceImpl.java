package com.space.service;

import com.space.exception.ShipNotFoundException;
import com.space.exception.UncorrectedInputDataException;
import com.space.model.Ship;
import com.space.repository.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepo repo;
    @Autowired
    public ShipServiceImpl(ShipRepo repo) {
        this.repo = repo;
    }


    @Override
    public Ship getShipById(Long id) throws ShipNotFoundException {
        Optional<Ship> optionalShip = repo.findById(id);
        if (optionalShip.isPresent()){
            return optionalShip.get();
        } else {
            throw new ShipNotFoundException("Ship not found");
        }
    }

    @Override
    public void createShip(Ship ship) throws UncorrectedInputDataException {
        if (ship.getName() == null
                || ship.getCrewSize() == null
                || ship.getPlanet() == null
                || ship.getProdDate() == null
                || ship.getShipType() == null
                || ship.getSpeed() == null){
            throw new UncorrectedInputDataException("Uncorrected input data");
        }
        checkValidShip(ship);

        if(ship.getUsed()==null){
            ship.setUsed(false);
        }
        ship.setRating(calculateRating(ship));

        repo.saveAndFlush(ship);
    }

    @Override
    public void deleteShip(Long id) throws ShipNotFoundException {
        Optional<Ship> optionalShip = repo.findById(id);
        if (optionalShip.isPresent()){
            repo.deleteById(id);
        } else {
            throw new ShipNotFoundException("Ship not found");
        }
    }

    @Override
    public List<Ship> getShips(Specification<Ship> specification) {
        return repo.findAll(specification);
    }

    @Override
    public Page<Ship> getShips(Specification<Ship> specification, Pageable sortedByName) {
        return repo.findAll(specification, sortedByName);
    }

    @Override
    public Ship updateShip(Long id, Ship ship) throws ShipNotFoundException, UncorrectedInputDataException {
        checkValidShip(ship);

        Optional<Ship> optional = repo.findById(id);
        if (!optional.isPresent())
            throw new ShipNotFoundException("The ship is not found");

        Ship editedShip = optional.get();

        if (ship.getName() != null)
            editedShip.setName(ship.getName());

        if (ship.getPlanet() != null)
            editedShip.setPlanet(ship.getPlanet());

        if (ship.getShipType() != null)
            editedShip.setShipType(ship.getShipType());

        if (ship.getProdDate() != null)
            editedShip.setProdDate(ship.getProdDate());

        if (ship.getUsed() != null)
            editedShip.setUsed(ship.getUsed());

        if (ship.getSpeed() != null)
            editedShip.setSpeed(ship.getSpeed());

        if (ship.getCrewSize() != null)
            editedShip.setCrewSize(ship.getCrewSize());


        editedShip.setRating(calculateRating(editedShip));
        return repo.saveAndFlush(editedShip);
    }

    private void checkValidShip(Ship ship) throws UncorrectedInputDataException {
        if (ship.getName() != null && (ship.getName().length() < 1 || ship.getName().length() > 50))
            throw new UncorrectedInputDataException("Incorrect ship name");

        if (ship.getPlanet() != null && (ship.getPlanet().length() < 1 || ship.getPlanet().length() > 50))
            throw new UncorrectedInputDataException("Incorrect ship planet");

        if (ship.getSpeed() != null && (ship.getSpeed() < 0.01d || ship.getSpeed() > 0.99d))
            throw new UncorrectedInputDataException("Incorrect ship speed");

        if (ship.getCrewSize() != null && (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999))
            throw new UncorrectedInputDataException("Incorrect ship crew size ");

        if (ship.getProdDate() != null) {
            if (ship.getProdDate().getTime() < 26192246400000L || ship.getProdDate().getTime() > 33134745600000L)
                throw new UncorrectedInputDataException("Incorrect ship production date");
        }
    }

    private Double calculateRating(Ship ship) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());
        int y1 = calendar.get(Calendar.YEAR);
        BigDecimal rating = BigDecimal.valueOf((80 * ship.getSpeed() * (ship.getUsed() ? 0.5 : 1)) / (3019 - y1 + 1));
        rating = rating.setScale(2, RoundingMode.HALF_UP);
        return rating.doubleValue();
    }
}
