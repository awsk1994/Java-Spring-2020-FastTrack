package org.wong.ioc.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class User {
    private String username;
    private String address;
    private Integer id;
    private Cat cat;
    private Cat[] cats;
    private String[] hobbies;
    private Map<String, Object> details;
    private Properties info;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", cat=" + cat +
                ", cats=" + Arrays.toString(cats) +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", details=" + details +
                ", info=" + info +
                '}';
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    public Cat getCat() {
        return cat;
    }

    public Cat[] getCats() {
        return cats;
    }

    public void setCats(Cat[] cats) {
        this.cats = cats;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public User(){
        System.out.println("----------- init User -----------");
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

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
