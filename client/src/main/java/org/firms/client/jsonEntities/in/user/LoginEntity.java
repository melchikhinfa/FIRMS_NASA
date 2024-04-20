package org.firms.client.jsonEntities.in.user;


public class LoginEntity {

    private String username;
    private String password;

    public LoginEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
