/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author matth
 */
public final class ChatRoom {
    private static StringProperty chatRoomData = new SimpleStringProperty();
    
    public static StringProperty chatRoomProperty() { return chatRoomData; }
    public static void setChatRoomData(String data) { chatRoomData.set(data); }
    public static String getChatRoomData() { return chatRoomData.get(); }
}
