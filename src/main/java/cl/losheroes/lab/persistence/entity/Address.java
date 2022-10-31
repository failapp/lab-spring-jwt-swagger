package cl.losheroes.lab.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private String streetName;
    private String streetNumber;
    private String state;
    private String city;
    private String country;

    public Address(String streetName, String streetNumber, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = "";
        this.country = country;
    }

    public Address(String streetName, String streetNumber, String state, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.state = state;
        this.city = city;
        this.country = country;
    }

}
