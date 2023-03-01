package com.example.demo.DataBase.UsersDB;


import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.security.models.Role;
import com.example.demo.security.models.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Модель данных пользователей.
 * @autor Шаля Андрей
 * @version 2.0
 */
@Entity
@JsonIgnoreProperties("password")
@Table(name = "blps_users")
public class User {

    /** Поле ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Логин для входа, он же почта */
    @Column(name = "login")
    private String login;

    /** Логин для входа, он же почта */
    @Column(name = "password")
    private String password;

    /** Имя */
    @Column(name = "first_name")
    private String firstName;

    /** Фамилия */
    @Column(name = "last_name")
    private String lastName;

    /** Роль пользователя в системе безопасности приложения*/
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /** Статус активности пользователя */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public User() {
        role = Role.USER;
        status = Status.ACTIVE;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}