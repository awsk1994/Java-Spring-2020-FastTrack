package org.wong.ioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wong.ioc.javaconfig.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<String> getAllUsers(){
        String hello = userDao.hello();
        System.out.println("hello = " + hello);

        List<String> users = new ArrayList<String>();
        for(int i=0; i<10; i++){
            users.add("wong:" + i);
        }
        return users;
    }
}
