package com.robobank.helpers;

import java.util.HashMap;
import java.util.Map;

public final class Utilities {

    public static Map<String, Object> packetObject(String key, Object object){
        Map<String, Object> packet = new HashMap<>();
        packet.put(key, object);
        return packet;
    }
}
