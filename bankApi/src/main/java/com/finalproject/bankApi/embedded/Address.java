package com.finalproject.bankApi.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String address;
    private Integer postalCode;
    private String city;
    private String country;

    public Address() {}

    public Address(String address, Integer postalCode, String city, String country) {
        setAddress(address);
        setPostalCode(postalCode);
        setCity(city);
        setCountry(country);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
