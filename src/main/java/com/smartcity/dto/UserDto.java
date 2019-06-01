package com.smartcity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserDto {

    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a password")
    private String password;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a name")
    private String name;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a surname")
    private String surname;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide an email")
    @Email(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide a valid email")
    private String email;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please provide a phone number")
    @Pattern(groups = {NewRecord.class, ExistingRecord.class},
            regexp = "/^((\\+3||8)+([0-9]){10})$/",
            message = "Please provide a valid phone number")
    private String phoneNumber;

    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return active == userDto.active &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(surname, userDto.surname) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(phoneNumber, userDto.phoneNumber) &&
                Objects.equals(createdDate, userDto.createdDate) &&
                Objects.equals(updatedDate, userDto.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, surname, email, phoneNumber, active, createdDate, updatedDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
