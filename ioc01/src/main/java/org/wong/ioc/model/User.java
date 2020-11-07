package org.wong.ioc.model;

public class User {
    private String username;
    private String address;
    private Integer id;

    public User(){
        System.out.println("----------- init User -----------");
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public User(String username, Integer id) {
        this.username = username;
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
