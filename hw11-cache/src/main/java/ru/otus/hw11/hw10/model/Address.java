package ru.otus.hw11.hw10.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tAddress")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "address_street")
    private String street;

    public Address(String street, User user) {
        this.street = street;
        this.user = user;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Address() {
    }

    public Address(String street) {
        this.street = street;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address: id = " + id
                + ", street = " + street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return id == that.id &&
                street.equals(that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }
}