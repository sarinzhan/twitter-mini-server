package gpt.entitties;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

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
}
