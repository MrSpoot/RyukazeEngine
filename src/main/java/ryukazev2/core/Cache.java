package ryukazev2.core;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private static final Map<CacheKey, Object> cache = new HashMap<>();

    public static boolean isObjectCached(Map<String, Object> attrs){
        CacheKey key = new CacheKey();
        attrs.forEach(key::putAttribute);

        return cache.containsKey(key);
    }

    public static <T> T getCacheObject(Map<String, Object> attrs){
        CacheKey key = new CacheKey();
        attrs.forEach(key::putAttribute);

        return (T) cache.get(key);
    }

    public static void putCacheObject(Map<String, Object> attrs, Object value){
        CacheKey key = new CacheKey();
        attrs.forEach(key::putAttribute);
        cache.put(key,value);
    }

}
