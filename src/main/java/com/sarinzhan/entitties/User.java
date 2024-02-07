package com.sarinzhan.entitties;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private LocalDate birthday;

    public User() {
    }

    public User(Long id, String login, String password, String firstName, String secondName, LocalDate birthday) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public User setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public User setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    @Override
    public String toString() {
        return String.format("[Пользователь] {\n\tID: %d\n\tПолное имя:  %s %s;\n\tДата рождения: %s;\n\tЛогин:%s\n}\n",
                this.id,this.firstName,this.secondName,this.birthday.toString(),this.login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, secondName, birthday);
    }
}
