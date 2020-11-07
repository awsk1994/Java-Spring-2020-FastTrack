package org.wong.ioc.javaconfig;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    public String hello(){
        return "user dao saying hello";
    }
}
