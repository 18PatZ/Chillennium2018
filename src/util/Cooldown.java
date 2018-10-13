package util;

import java.util.HashMap;

public class Cooldown {

    private HashMap<String, Long> times = new HashMap<>();

    public boolean expired(String key, double seconds){
        Long expire = times.get(key);
        if(expire != null && System.currentTimeMillis() < expire)
            return false;

        times.put(key, System.currentTimeMillis() + (int)(seconds * 1000));
        return true;
    }

}
