package org.spoot.ryukaze.objects.controller;

import org.spoot.ryukaze.core.Engine;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class CharacterController extends GameObject implements Controller {

    public CharacterController() {
        super(new Transform(),null,null,null);
        Engine.getScene().subscribeController(this);
    }

    @Override
    public final void _processMouseInput(float x, float y){
        processMouseInput(x,y);
    }
    public void processMouseInput(float x, float y){

    }


}
