/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.ArrayList;

/**
 *
 * @author matth
 */
public class CommonClass implements Common{
    private ArrayList usersOnline;
    
    public void addUser(String user) {
        usersOnline.add(user);
    }
    
    public void removeUser(String user) {
        for(int i = 0; i < usersOnline.size(); i++) {
            if(usersOnline.get(i) == user) {
                usersOnline.remove(i);
            }
        }
    }
    
    public ArrayList getUsersOnline() {
        return usersOnline;
    }
}
