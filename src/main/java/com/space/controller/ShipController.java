package com.space.controller;

import com.space.exception.ShipNotFoundException;
import com.space.exception.UncorrectedInputDataException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.specification.ShipSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/ships")
public class ShipController {


    private final ShipService service;

    public ShipController(ShipService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) throws UncorrectedInputDataException {
        service.createShip(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam(name = "name", required = false) String name,
                                                 @RequestParam(name = "planet", required = false) String planet,
                                                 @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                 @RequestParam(name = "after", required = false) Long after,
                                                 @RequestParam(name = "before", required = false) Long before,
                                                 @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                 @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                 @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                 @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                                                 @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                                                 @RequestParam(name = "minRating", required = false) Double minRating,
                                                 @RequestParam(name = "maxRating", required = false) Double maxRating) {
        Specification<Ship> specification = ShipSpecification.getSpecification(name, planet, shipType,
                after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        return new ResponseEntity<>(service.getShips(specification).size(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ship>> getShipsList(@RequestParam(name = "name", required = false) String name,
                                                   @RequestParam(name = "planet", required = false) String planet,
                                                   @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                   @RequestParam(name = "after", required = false) Long after,
                                                   @RequestParam(name = "before", required = false) Long before,
                                                   @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                   @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                   @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                   @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                                                   @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                                                   @RequestParam(name = "minRating", required = false) Double minRating,
                                                   @RequestParam(name = "maxRating", required = false) Double maxRating,
                                                   @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
                                                   @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        Specification<Ship> specification = ShipSpecification.getSpecification(name, planet, shipType,
                after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

        Page<Ship> shipsList = service.getShips(specification, pageRequest);
        return new ResponseEntity<>(shipsList.getContent(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Ship> getShipById(@PathVariable("id") String id)
            throws UncorrectedInputDataException, ShipNotFoundException {
        Long longId = checkId(id);
        if (longId < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.getShipById(longId);

        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Ship> updateShip(@PathVariable("id") String id,
                                           @RequestBody Ship ship)
            throws UncorrectedInputDataException, ShipNotFoundException {
        Long longId = checkId(id);

        return new ResponseEntity<>(service.updateShip(longId, ship), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") String id)
            throws ShipNotFoundException, UncorrectedInputDataException {
        Long longId = checkId(id);
        service.deleteShip(longId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long checkId(String id) throws UncorrectedInputDataException {
        if (id == null || id.equals("") || id.equals("0")) {
            throw new UncorrectedInputDataException("Uncorrected ID");
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new UncorrectedInputDataException("NaN ID", e);
        }
    }
}
