package org.spoot.ryukazev2.graphic.utils;

import org.spoot.ryukazev2.graphic.manager.Manager;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<Class<?>, Manager> services = new HashMap<>();

    public static <T> void registerService(Class<T> klass, Manager service) {
        if(services.containsKey(klass)){
            services.replace(klass,service);
        }else{
            services.put(klass, service);
            for(Manager manager : services.values()){
                manager.linkServices(services);
            }
        }

    }

    public static <T> T getService(Class<T> klass) {
        return klass.cast(services.get(klass));
    }

}
