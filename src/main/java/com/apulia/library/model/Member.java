package com.apulia.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({"id", "firstName", "lastName", "city", "phone"})
@JsonIgnoreProperties(ignoreUnknown = false)

@Entity(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Field 'firstName' is required")
    @Size(min = 1, max = 100,
            message = "First name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]+$",
            message = "First name must contain only letters")
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @NotBlank(message = "Field 'lastName' is required")
    @Size(min = 1, max = 100,
            message = "Last name must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]+$",
            message = "Last name must contain only letters")
    @Column(nullable = false, name = "last_name")
    private String lastName;

    @NotBlank(message = "Field 'city' is required")
    @Size(min = 1, max = 100,
            message = "City must be between 1 and 100 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\s-]+$",
            message = "City must contain only letters")
    @Column(nullable = false, name = "city")
    private String city;

    @NotBlank(message = "Field 'phone' is required")
    @Size(min = 1, max = 20,
            message = "Phone must be between 1 and 20 characters")
    @Pattern(
            regexp = "^(?:\\+39)?\\s?(?:0\\d{1,3}\\s?\\d{5,8}|3\\d{2}\\s?\\d{6,7})$",
            message = "Invalid phone number. Accepts Italian landline and mobile numbers, with or without +39"
    )
    @Column(nullable = false, name = "phone", unique = true)
    private String phone;

    public Member() {}

    public Member(Integer id, String firstName, String lastName, String city, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.phone = phone;
    }

    public Member(String firstName, String lastName, String city, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
