package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.util.Objects;

@Embeddable
@Table(name = "ADDRESS")
public class Address {
    private String city;
    private String street;
    private String zipcode;



    // 기본생성자는 필수 이다.
    public Address() {
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipCode;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipcode;
    }

    private void setZipCode(String zipCode) {
        this.zipcode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
