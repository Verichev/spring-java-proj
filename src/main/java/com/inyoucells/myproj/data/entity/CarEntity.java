package com.inyoucells.myproj.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String year;
    private boolean used;
    private int horsepower;
    private Long driverId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return used == carEntity.used && horsepower == carEntity.horsepower && Objects.equals(id, carEntity.id) && Objects.equals(brand, carEntity.brand) && Objects.equals(year, carEntity.year) && Objects.equals(driverId, carEntity.driverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, year, used, horsepower, driverId);
    }
}
