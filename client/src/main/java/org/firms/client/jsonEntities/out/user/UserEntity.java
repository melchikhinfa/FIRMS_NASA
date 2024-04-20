package org.firms.client.jsonEntities.out.user;


public class UserEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public UserEntity() {
    }

    public UserEntity(String username, String firstName, String lastName, String middleName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }
}
