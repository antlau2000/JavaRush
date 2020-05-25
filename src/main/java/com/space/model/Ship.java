package com.space.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "planet")
    private String planet;

    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    @Column(name = "prodDate")
    private Date prodDate;

    @Column(name = "isUsed", nullable = false, columnDefinition = "boolean default false")
    private Boolean isUsed;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public String getName() {
        return name;
    }


    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }


    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Boolean getUsed() {
        return isUsed;
    }


    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public double getRating() {
        return rating;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Long getId() {
        return id;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(prodDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(ship.prodDate);
        return Double.compare(ship.rating, rating) == 0 &&
                Objects.equals(id, ship.id) &&
                Objects.equals(name, ship.name) &&
                Objects.equals(planet, ship.planet) &&
                shipType == ship.shipType &&
                Objects.equals(calendar1.get(Calendar.YEAR), calendar2.get(Calendar.YEAR)) &&
                Objects.equals(isUsed, ship.isUsed) &&
                Objects.equals(speed, ship.speed) &&
                Objects.equals(crewSize, ship.crewSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }

}
