package com.catalogo.dto;

import com.catalogo.entities.User;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class UserInsertDTO extends UserDTO{

    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
