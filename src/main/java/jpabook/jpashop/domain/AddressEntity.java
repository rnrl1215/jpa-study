package jpabook.jpashop.domain;

import org.h2.store.LobStorageBackend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue
    Long id;

    private Address address;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipCode) {
        this.address = new Address(city, street, zipCode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
