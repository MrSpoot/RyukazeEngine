package ryukazev2.core;

import java.util.HashMap;
import java.util.Map;

public class Statistics {

    private static final Map<String, Object> stats = new HashMap<>();

    public static void updateStats(String name, Object data){
        stats.put(name,data);
    }

    public static String getStats(String name){
        if(stats.containsKey(name)){
            return stats.get(name).toString();
        }else {
            return "NaN";
        }
    }

}
