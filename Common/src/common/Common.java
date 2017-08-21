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
public interface Common {
    public void addUser(String user);
    public void removeUser(String user);
    public ArrayList getUsersOnline();
}
