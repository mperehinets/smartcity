package com.smartcity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Objects;

public class Role implements GrantedAuthority {
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public Role() {
    }

    public Role(Long id, String name, LocalDateTime updatedDate, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(createdDate, role.createdDate) &&
                Objects.equals(updatedDate, role.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }


    @Override
    public String getAuthority() {
        return getName();
    }
}
