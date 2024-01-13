package org.spoot.ryukazev2.manager;

import java.util.HashMap;
import java.util.Map;

public abstract class Manager {

    protected Map<Class<?>, Manager> services = new HashMap<>();

    public void linkServices(Map<Class<?>, Manager> services){
        this.services = services;
    }

}
