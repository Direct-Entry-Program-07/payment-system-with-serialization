/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final List<User> userDB = new ArrayList<>();

    static {

    }

    public UserService(){

    }

    public void saveUser(User user){


        userDB.add(user);
    }

    public void updateUser(User user){

    }

    public void deleteUser(String nic){

    }

    public List<User> findAllUsers(){
        return userDB;
    }

    public User findUser(String nic){
        for (User user : userDB) {
           /* if (user.getNic().equals(nic)){
                return user;
            }*/
        }
        return null;
    }

    public List<User> findUsers(String query){
        List<User> result = new ArrayList<>();

        for (User user: userDB) {

            if (user.getFullname().contains(query) ||
                  //  user.getNic().contains(query) ||
                    user.getAddress().contains(query) ||
                    user.getContactNumber().contains(query) ||
                    user.getEmailAddress().contains(query) ||
                    user.getUsername().contains(query)){
                result.add(user);
            }
        }
        return result;
    }


}
